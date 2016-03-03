package wtist.web.sso.dao;

import java.util.Date;

public class Service {
	private Integer id;

	private String servicename;

	private String serviceid;

	private Date gmtModified;

	private Date gmtCreated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename == null ? null : servicename.trim();
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid == null ? null : serviceid.trim();
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
}