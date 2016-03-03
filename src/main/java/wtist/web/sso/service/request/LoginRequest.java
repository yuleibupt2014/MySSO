package wtist.web.sso.service.request;

import java.util.UUID;

public class LoginRequest {
	private String requestId = UUID.randomUUID().toString();

	private String loginIDinCookie;
	private String accountInCookie;

	private String accountParam;
	private String passwordParam;
	// encoded
	private String from;

	public String getLoginIDinCookie() {
		return loginIDinCookie;
	}

	public void setLoginIDinCookie(String loginIDinCookie) {
		this.loginIDinCookie = loginIDinCookie;
	}

	public String getAccountInCookie() {
		return accountInCookie;
	}

	public void setAccountInCookie(String accountInCookie) {
		this.accountInCookie = accountInCookie;
	}

	public String getAccountParam() {
		return accountParam;
	}

	public void setAccountParam(String accountParam) {
		this.accountParam = accountParam;
	}

	public String getPasswordParam() {
		return passwordParam;
	}

	public void setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
