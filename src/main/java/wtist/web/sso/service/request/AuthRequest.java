package wtist.web.sso.service.request;

import java.util.UUID;

public class AuthRequest {
	private String requestId = UUID.randomUUID().toString();

	private String account;
	private String loginID;
	private String serviceID;

	private String ticket;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

}
