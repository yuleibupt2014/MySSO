package wtist.web.sso.service;

import wtist.web.sso.service.request.AuthRequest;
import wtist.web.sso.service.response.AuthResponse;

public interface IAuthService {
	AuthResponse auth(AuthRequest request);
}
