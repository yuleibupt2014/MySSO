package wtist.web.sso.service;

import wtist.web.sso.service.request.LoginRequest;
import wtist.web.sso.service.response.LoginResponse;

public interface ILoginService {
	LoginResponse login(LoginRequest request);
}
