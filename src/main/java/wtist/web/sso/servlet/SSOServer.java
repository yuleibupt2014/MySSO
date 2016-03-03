package wtist.web.sso.servlet;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import wtist.web.sso.Encryption.Encryption;
import wtist.web.sso.cache.RedisCache;
import wtist.web.sso.dao.User;
import wtist.web.sso.dao.UserService;
import wtist.web.sso.mapper.ServiceMapper;
import wtist.web.sso.mapper.UserMapper;
import wtist.web.sso.mapper.UserServiceMapper;
import wtist.web.sso.service.response.AuthResponse;
import wtist.web.sso.util.SignatureUtil;

import com.alibaba.fastjson.JSON;

@Controller
public class SSOServer {
	@Autowired
	private ServiceMapper serviceMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserServiceMapper userServiceMapper;
	@Autowired
	private RedisCache cache;
	/*
	 * @Value("#{yewu1.url}") private String yewu1;
	 * 
	 * @Value("#{yewu2.url}") private String yewu2;
	 * 
	 * @Value("#{sso.url}") private String sso;
	 */

	private static Logger logger = Logger.getLogger(SSOServer.class.toString());

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView loginModel = new ModelAndView("login");
		ModelAndView loginsuccessModel = new ModelAndView("loginsuccess");
		// 先查cookie
		String loginID1 = parseCookie(request.getCookies(), "loginID");
		String account1 = parseCookie(request.getCookies(), "account");
		if (account1 != null && loginID1 != null
				&& cache.isExist(account1 + "." + loginID1)) {
			String lastLoginTime = cache.getLastLoginTime(account1);
			String username = cache.getUsername(account1);
			loginsuccessModel.addObject("username", username);
			loginsuccessModel.addObject("lastlogintime", lastLoginTime);
			loginsuccessModel.addObject("fail", false);
			loginsuccessModel.addObject("account", account1);
			return loginsuccessModel;
		}
		// 再查参数
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		// 来自哪个业务服务器
		String from = request.getParameter("from");
		if (from != null && !from.isEmpty()) {
			loginModel.addObject("from", from);
		} else
			loginModel.addObject("from", "");
		if (account != null && password != null) {
			User user = userMapper.selectByAccountId(account);
			if (user != null
					&& user.getPassword().equals(Encryption.MD5(password))) {
				// 成功登录
				logger.info(user.getUsername());
				// TODO
				// String ticket=new
				// Secret().init().getPublicKey().toString();
				String loginID = UUID.randomUUID().toString();
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				String time = df.format(date);
				String lastLoginTime = cache.saveLoginTime(account, time);
				cache.put(account + "." + loginID, time, 3600);
				cache.saveUsername(account, user.getUsername(), 3700);
				Cookie cookie = new Cookie("loginID", loginID);
				cookie.setMaxAge(-1);
				cookie.setDomain("cmbchina.info");
				cookie.setPath("/");
				// cookie.setSecure(true);
				response.addCookie(cookie);

				cookie = new Cookie("account", account);
				cookie.setMaxAge(-1);
				cookie.setDomain("cmbchina.info");
				cookie.setPath("/");
				// cookie.setSecure(true);
				response.addCookie(cookie);
				// 登录成功后，由from决定去向
				if (from != null && !from.isEmpty()) {
					try {
						String uri = URLDecoder.decode(from, "utf-8");
						response.sendRedirect(uri);
					} catch (Exception e) {
						loginsuccessModel.addObject("username",
								user.getUsername());
						loginsuccessModel.addObject("lastlogintime",
								lastLoginTime);
						loginsuccessModel.addObject("fail", false);
						loginsuccessModel.addObject("account", account);
						return loginsuccessModel;
					}
				} else {
					loginsuccessModel.addObject("username", user.getUsername());
					loginsuccessModel.addObject("lastlogintime", lastLoginTime);
					loginsuccessModel.addObject("fail", false);
					loginsuccessModel.addObject("account", account);
					return loginsuccessModel;
				}
			} else {
				loginModel.addObject("fail", true);
			}
		} else if (account != null || password != null)
			loginModel.addObject("fail", true);
		return loginModel;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView m = new ModelAndView("logout");
		Cookie[] cookies = request.getCookies();
		String loginID = parseCookie(cookies, "loginID");

		String signature = request.getParameter("signature");
		String timeStamp = request.getParameter("timeStamp");
		String account = request.getParameter("account");
		if (loginID != null && account != null
				&& SignatureUtil.checkSignature(timeStamp, account, signature)
				&& cache.isExist(account + "." + loginID)) {
			Cookie cookie = new Cookie("loginID", loginID);
			cookie.setDomain("cmbchina.info");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("account", loginID);
			cookie.setDomain("cmbchina.info");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cache.del(account + "." + loginID);
			m.addObject("fail", false);
			return m;
		}
		m.addObject("fail", true);
		return m;
	}

	@RequestMapping("/auth")
	public void auth(HttpServletRequest request, HttpServletResponse response) {
		String account = request.getParameter("account");
		String loginID = request.getParameter("loginID");
		String serviceID = request.getParameter("serviceID");
		AuthResponse result = new AuthResponse();
		if (account != null && loginID != null && serviceID != null) {
			result.setSuccess(true);
			if (cache.isExist(account + "." + loginID)) {
				result.setLogined(true);
				UserService req = new UserService();
				req.setServiceid(serviceID);
				req.setUserid(account);
				UserService userService = userServiceMapper
						.selectByUserIdAndServiceId(req);
				if (userService == null) {
					result.setAuthed(false);
				} else {
					result.setAuthed(true);
				}
			}
		}
		try {
			response.setContentType("application/json");
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSON(result));
		} catch (Exception e) {
		}
	}

	private String parseCookie(Cookie[] cookies, String name) {
		String result = null;
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				result = cookies[i].getValue();
				break;
			}
		}
		return result;
	}
}
