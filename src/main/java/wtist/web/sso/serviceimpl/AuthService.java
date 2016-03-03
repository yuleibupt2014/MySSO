package wtist.web.sso.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wtist.web.sso.cache.RedisCache;
import wtist.web.sso.dao.UserService;
import wtist.web.sso.mapper.UserServiceMapper;
import wtist.web.sso.service.IAuthService;
import wtist.web.sso.service.request.AuthRequest;
import wtist.web.sso.service.response.AuthResponse;

@Service
public class AuthService implements IAuthService {
	@Autowired
	RedisCache cache;
	@Autowired
	UserServiceMapper userServiceMapper;

	@Override
	public AuthResponse auth(AuthRequest request) {
		String account = request.getAccount();
		String loginID = request.getLoginID();
		String serviceID = request.getServiceID();
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
		return result;
	}

}
