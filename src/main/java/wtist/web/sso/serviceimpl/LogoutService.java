package wtist.web.sso.serviceimpl;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wtist.web.sso.cache.RedisCache;
import wtist.web.sso.service.ILogoutService;
import wtist.web.sso.service.request.LogoutRequest;
import wtist.web.sso.service.response.LogoutResponse;
import wtist.web.sso.util.SignatureUtil;

@Service
public class LogoutService implements ILogoutService {
	@Autowired
	RedisCache cache;

	@Override
	public LogoutResponse logout(LogoutRequest request) {
		LogoutResponse response = new LogoutResponse();
		String loginID = request.getLoginID();
		String signature = request.getSignature();
		String timeStamp = request.getTimeStamp();
		String account = request.getAccount();
		if (loginID != null && account != null
				&& SignatureUtil.checkSignature(timeStamp, account, signature)
				&& cache.isExist(account + "." + loginID)) {
			Cookie cookie = new Cookie("loginID", loginID);
			cookie.setDomain("cmbchina.info");
			cookie.setPath("/");
			cookie.setMaxAge(0);

			response.getCookies().add(cookie);
			cookie = new Cookie("account", loginID);
			cookie.setDomain("cmbchina.info");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.getCookies().add(cookie);

			cache.del(account + "." + loginID);
			response.setSuccess(true);
			return response;
		}
		response.setSuccess(false);
		return response;
	}

}
