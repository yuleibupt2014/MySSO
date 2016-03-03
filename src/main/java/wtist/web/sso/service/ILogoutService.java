package wtist.web.sso.service;

import wtist.web.sso.service.request.LogoutRequest;
import wtist.web.sso.service.response.LogoutResponse;

public interface ILogoutService {
	LogoutResponse logout(LogoutRequest request);
}
