package wtist.web.sso.dao;

import java.util.Date;

public class UserService {
	private Integer id;

	private String userid;

	private String serviceid;

	private Date gmtCreated;

	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid == null ? null : serviceid.trim();
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}