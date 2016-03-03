package wtist.web.sso.service.response;

public class AuthResponse {
	private String requestId;
	private Boolean success = false;
	private Boolean logined = false;
	private Boolean authed = false;

	public Boolean getLogined() {
		return logined;
	}

	public void setLogined(Boolean logined) {
		this.logined = logined;
	}

	public Boolean getAuthed() {
		return authed;
	}

	public void setAuthed(Boolean authed) {
		this.authed = authed;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
