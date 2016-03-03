package wtist.web.sso.service.response;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;

public class LoginResponse {
	private String requestId;
	// 第一步先处理cookie
	private ArrayList<Cookie> cookies = new ArrayList<>();
	// 第二步如果redirect，就转向，否则显示view
	// redirect view
	// 如果是redirect，尝试转到to，失败就显示view
	private Boolean redirect = false;
	private String to;
	// 登录失败显示错误信息
	private Boolean success = false;
	private Map<String, String> user;

	private String ticket;
	private String publicKey;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public ArrayList<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(ArrayList<Cookie> cookies) {
		this.cookies = cookies;
	}

	public Boolean getRedirect() {
		return redirect;
	}

	public void setRedirect(Boolean redirect) {
		this.redirect = redirect;
	}

	public Map<String, String> getUser() {
		return user;
	}

	public void setUser(Map<String, String> user) {
		this.user = user;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
