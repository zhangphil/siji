package com.wlqq.huodi.bean;

import java.io.Serializable;
import java.util.Date;

public class Message extends BeanObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1017079292323508428L;

	public static enum Type {
		Freight, Vehicle
	}

	private long uid;
	private String userDisplayName;
	private long departurePlaceId;
	private String destinationPlaceId;
	private String content;
	private String infoStatus;
	private Date createTime;
	private String mobile;
	private String tel;
	private String microNetNo;
	private String qq;
	private String gnd;
	private String hwd;
	private String un;
	private AuditState auditState;
	private boolean ffCertificated;
	private boolean icCertificated;
	private boolean bsCertificated;
	private boolean blCertificated;
	private String contactPerson;
	private boolean viewedDetails;

	public String getInfoStatus() {
		return infoStatus;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}

	public long getUid() {
		return uid;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public String getGnd() {
		return gnd;
	}

	public void setGnd(String gnd) {
		this.gnd = gnd;
	}

	public String getHwd() {
		return hwd;
	}

	public void setHwd(String hwd) {
		this.hwd = hwd;
	}

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	public long getDeparturePlaceId() {
		return departurePlaceId;
	}

	public void setDeparturePlaceId(long departurePlaceId) {
		this.departurePlaceId = departurePlaceId;
	}

	public String getDestinationPlaceId() {
		return destinationPlaceId;
	}

	public void setDestinationPlaceId(String destinationPlaceId) {
		this.destinationPlaceId = destinationPlaceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMicroNetNo() {
		return microNetNo;
	}

	public void setMicroNetNo(String microNetNo) {
		this.microNetNo = microNetNo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public AuditState getAuditState() {
		return auditState;
	}

	public void setAuditState(AuditState auditState) {
		this.auditState = auditState;
	}

	public boolean isFfCertificated() {
		return ffCertificated;
	}

	public void setFfCertificated(boolean ffCertificated) {
		this.ffCertificated = ffCertificated;
	}

	public boolean isIcCertificated() {
		return icCertificated;
	}

	public void setIcCertificated(boolean icCertificated) {
		this.icCertificated = icCertificated;
	}

	public boolean isBsCertificated() {
		return bsCertificated;
	}

	public void setBsCertificated(boolean bsCertificated) {
		this.bsCertificated = bsCertificated;
	}

	public boolean isBlCertificated() {
		return blCertificated;
	}

	public void setBlCertificated(boolean blCertificated) {
		this.blCertificated = blCertificated;
	}

	public boolean isViewedDetails() {
		return viewedDetails;
	}

	public void setViewedDetails(boolean viewedDetails) {
		this.viewedDetails = viewedDetails;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Message)) return false;

		Message message = (Message) o;

		return id == message.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Message");
		sb.append("{id=").append(id);
		sb.append(", uid=").append(uid);
		sb.append(", userDisplayName='").append(userDisplayName).append('\'');
		sb.append(", departurePlaceId=").append(departurePlaceId);
		sb.append(", destinationPlaceId='").append(destinationPlaceId).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append(", createTime=").append(createTime);
		sb.append(", mobile='").append(mobile).append('\'');
		sb.append(", tel='").append(tel).append('\'');
		sb.append(", microNetNo='").append(microNetNo).append('\'');
		sb.append(", qq='").append(qq).append('\'');
		sb.append(", auditState=").append(auditState);
		sb.append(", ffCertificated=").append(ffCertificated);
		sb.append(", icCertificated=").append(icCertificated);
		sb.append(", bsCertificated=").append(bsCertificated);
		sb.append(", blCertificated=").append(blCertificated);
		sb.append('}');
		return sb.toString();
	}
}
