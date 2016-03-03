package wtist.web.sso.service.request;

import java.util.UUID;

public class LogoutRequest {
	private String requestId = UUID.randomUUID().toString();

	private String loginID;
	private String account;

	private String ticketEncryption;
	private String signature;
	private String timeStamp;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTicketEncryption() {
		return ticketEncryption;
	}

	public void setTicketEncryption(String ticketEncryption) {
		this.ticketEncryption = ticketEncryption;
	}
}
