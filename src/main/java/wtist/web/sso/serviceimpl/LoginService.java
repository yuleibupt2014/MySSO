package wtist.web.sso.serviceimpl;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wtist.web.sso.cache.RedisCache;
import wtist.web.sso.dao.User;
import wtist.web.sso.mapper.UserMapper;
import wtist.web.sso.service.ILoginService;
import wtist.web.sso.service.request.LoginRequest;
import wtist.web.sso.service.response.LoginResponse;

@Service
public class LoginService implements ILoginService {
	@Autowired
	RedisCache cache;
	@Autowired
	UserMapper userMapper;

	@Override
	public LoginResponse login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		// 先查cookie
		String account1 = request.getAccountInCookie();
		String loginID1 = request.getLoginIDinCookie();
		if (account1 != null && loginID1 != null
				&& cache.isExist(account1 + "." + loginID1)) {
			String lastLoginTime = cache.getLastLoginTime(account1);
			String username = cache.getUsername(account1);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("username", username);
			map.put("lastlogintime", lastLoginTime);
			map.put("account", account1);
			response.setUser(map);
			response.setSuccess(true);
			return response;
		}
		String account = request.getAccountParam();
		String password = request.getPasswordParam();
		if (account != null && password != null) {
			User user = userMapper.selectByAccountId(account);
			if (user != null && user.getPassword().equals(password)) {
				// 成功登录
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
				response.getCookies().add(cookie);
				cookie = new Cookie("account", account);
				cookie.setMaxAge(-1);
				cookie.setDomain("cmbchina.info");
				cookie.setPath("/");
				// cookie.setSecure(true);
				response.getCookies().add(cookie);

				// 登录成功后，由from决定去向
				String from = request.getFrom();
				if (from != null && !from.isEmpty()) {
					try {
						String uri = URLDecoder.decode(from, "utf-8");
						response.setRedirect(true);
						response.setTo(uri);
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("username", user.getUsername());
						map.put("lastlogintime", lastLoginTime);
						map.put("account", account);
						response.setUser(map);
						response.setSuccess(true);
					} catch (Exception e) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("username", user.getUsername());
						map.put("lastlogintime", lastLoginTime);
						map.put("account", account);
						response.setUser(map);
						response.setRedirect(false);
						response.setSuccess(true);
						return response;
					}
				} else {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("username", user.getUsername());
					map.put("lastlogintime", lastLoginTime);
					map.put("account", account);
					response.setUser(map);
					response.setSuccess(true);
					return response;
				}
			} else {
				response.setSuccess(false);
			}
		} else if (account != null || password != null)
			response.setSuccess(false);
		return response;
	}

}
