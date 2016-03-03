package wtist.web.sso.service.response;

import java.util.ArrayList;

import javax.servlet.http.Cookie;

public class LogoutResponse {
	private String requestId;
	private ArrayList<Cookie> cookies = new ArrayList<>();
	private Boolean success;

	public ArrayList<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(ArrayList<Cookie> cookies) {
		this.cookies = cookies;
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
