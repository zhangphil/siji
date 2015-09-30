package com.wlqq.huodi.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class UserProfile implements Serializable {

	private long id;   // User ID
	private String username;   // Username
	private String type;   // User Type
	private long provinceId;   // Province ID
	private long cityId;   // City ID
	private long countyId;   // County ID
	private String address;   // Address
	private String contactor;   // Contact Person:
	private String mobile;   // Mobile number
	private String mobile1;   // Mobile number
	private String mobile2;   // Mobile number
	private String mobile3;   // Mobile number
	private String telephone; // Telephone
	private String qq; // QQ Number
	private String score; // Score
	private boolean isMember;

	private String membershipName;  // current bought Service Name
	private Date membershipStartTime;   // Service Start Time
	private Date membershipExpireTime;   // Service End Time

	private long vehicleBrandId;     // System defined vehicle brand's ID
	private String vehicleBrandName;     // System defined vehicle brand's ID
	private String vehicleBrand;  // Vehicle Brand
	private String plateNumber;  // Vehicle No
	private int engineBrandId;      // System defined engine brand's ID
	private String engineBrand;  // Engine Brand
	private String engineBrandName;  // Engine Brand
	private String enginePower;  // Engine Power
	private int vehicleTypeId;  // Vehicle Type Id
	private String vehicleTypeName;  // Vehicle Type Id
	private int vehicleLengthId;  // Vehicle Length Id
	private String vehicleLengthName;  // Vehicle Length Id
	private int wheelNumber;     // Wheel numbers
	private String vehicleLicenseTime;   // Vehicle License Time
	private String icName;
	private String icNum;
	private String boxStructure;
	private String trailerAxleType;

	private String companyName; // Company Name
	private String companyType;// Company Type
	private String companyScale;  // Company Scale
	private String companyLicenseNo;  // Company License No
	private String companyWebsite;  // Company Website
	private String representative;  // Company Representative
	private String companyDescription;  // Company Introduction
	private String imageFolderDir;  //photo path
	private String reasonNum;  //reason num
	private int atta; //atta
	private String voip;//网络电话绑定号码

	private Date systemTime;

	private File avatarFile;
	private File operatingLicenseFile;
	private File StoreFile;

	private AuditState auditState;

	public static enum AuditState {
		Unaudited,
		Valid,
		Invalid,
		Applying
	}

	public String getVehicleBrandName() {
		return vehicleBrandName;
	}

	public void setVehicleBrandName(String vehicleBrandName) {
		this.vehicleBrandName = vehicleBrandName;
	}

	public String getEngineBrandName() {
		return engineBrandName;
	}

	public void setEngineBrandName(String engineBrandName) {
		this.engineBrandName = engineBrandName;
	}

	public String getVehicleTypeName() {
		return vehicleTypeName;
	}

	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}

	public String getBoxStructure() {
		return boxStructure;
	}

	public void setBoxStructure(String boxStructure) {
		this.boxStructure = boxStructure;
	}

	public String getTrailerAxleType() {
		return trailerAxleType;
	}

	public void setTrailerAxleType(String trailerAxleType) {
		this.trailerAxleType = trailerAxleType;
	}

	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean member) {
		isMember = member;
	}

	public String getVoip() {
		return voip;
	}

	public void setVoip(String voip) {
		this.voip = voip;
	}

	public int getAtta() {
		return atta;
	}

	public void setAtta(int atta) {
		this.atta = atta;
	}

	public String getReasonNum() {
		return reasonNum;
	}

	public void setReasonNum(String reasonNum) {
		this.reasonNum = reasonNum;
	}

	public AuditState getAuditState() {
		return auditState;
	}

	public void setAuditState(AuditState auditState) {
		this.auditState = auditState;
	}

	public File getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(File avatarFile) {
		this.avatarFile = avatarFile;
	}

	public File getOperatingLicenseFile() {
		return operatingLicenseFile;
	}

	public void setOperatingLicenseFile(File operatingLicenseFile) {
		this.operatingLicenseFile = operatingLicenseFile;
	}

	public File getStoreFile() {
		return StoreFile;
	}

	public void setStoreFile(File storeFile) {
		this.StoreFile = storeFile;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getCountyId() {
		return countyId;
	}

	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getMembershipName() {
		return membershipName;
	}

	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}

	public Date getMembershipStartTime() {
		return membershipStartTime;
	}

	public void setMembershipStartTime(Date membershipStartTime) {
		this.membershipStartTime = membershipStartTime;
	}

	public Date getMembershipExpireTime() {
		return membershipExpireTime;
	}

	public void setMembershipExpireTime(Date membershipExpireTime) {
		this.membershipExpireTime = membershipExpireTime;
	}

	public long getVehicleBrandId() {
		return vehicleBrandId;
	}

	public void setVehicleBrandId(long vehicleBrandId) {
		this.vehicleBrandId = vehicleBrandId;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public int getEngineBrandId() {
		return engineBrandId;
	}

	public void setEngineBrandId(int engineBrandId) {
		this.engineBrandId = engineBrandId;
	}

	public String getEngineBrand() {
		return engineBrand;
	}

	public void setEngineBrand(String engineBrand) {
		this.engineBrand = engineBrand;
	}

	public String getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(String enginePower) {
		this.enginePower = enginePower;
	}

	public int getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(int vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public int getVehicleLengthId() {
		return vehicleLengthId;
	}

	public void setVehicleLengthId(int vehicleLengthId) {
		this.vehicleLengthId = vehicleLengthId;
	}

	public int getWheelNumber() {
		return wheelNumber;
	}

	public void setWheelNumber(int wheelNumber) {
		this.wheelNumber = wheelNumber;
	}

	public String getVehicleLicenseTime() {
		return vehicleLicenseTime;
	}

	public void setVehicleLicenseTime(String vehicleLicenseTime) {
		this.vehicleLicenseTime = vehicleLicenseTime;
	}

	public String getIcName() {
		return icName;
	}

	public void setIcName(String icName) {
		this.icName = icName;
	}

	public String getIcNum() {
		return icNum;
	}

	public void setIcNum(String icNum) {
		this.icNum = icNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	public String getCompanyLicenseNo() {
		return companyLicenseNo;
	}

	public void setCompanyLicenseNo(String companyLicenseNo) {
		this.companyLicenseNo = companyLicenseNo;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getImageFolderDir() {
		return imageFolderDir;
	}

	public void setImageFolderDir(String imageFolderDir) {
		this.imageFolderDir = imageFolderDir;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserProfile)) return false;

		UserProfile that = (UserProfile) o;

		return id == that.id;
	}

	public Date getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile3() {
		return mobile3;
	}

	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		if (isOfDriverType()) {
			final StringBuilder sb = new StringBuilder();
			sb.append("UserProfile");
			sb.append("{id=").append(id);
			sb.append(", username='").append(username).append('\'');
			sb.append(", type='").append(type).append('\'');
			sb.append(", provinceId=").append(provinceId);
			sb.append(", cityId=").append(cityId);
			sb.append(", countyId=").append(countyId);
			sb.append(", address='").append(address).append('\'');
			sb.append(", contactor='").append(contactor).append('\'');
			sb.append(", mobile='").append(mobile).append('\'');
			sb.append(", telephone='").append(telephone).append('\'');
			sb.append(", qq='").append(qq).append('\'');
			sb.append(", score='").append(score).append('\'');
			sb.append(", membershipName='").append(membershipName).append('\'');
			sb.append(", membershipStartTime=").append(membershipStartTime);
			sb.append(", membershipExpireTime=").append(membershipExpireTime);
			sb.append(", vehicleBrandId=").append(vehicleBrandId);
			sb.append(", vehicleBrand='").append(vehicleBrand).append('\'');
			sb.append(", plateNumber='").append(plateNumber).append('\'');
			sb.append(", engineBrandId=").append(engineBrandId);
			sb.append(", engineBrand='").append(engineBrand).append('\'');
			sb.append(", enginePower='").append(enginePower).append('\'');
			sb.append(", vehicleTypeId=").append(vehicleTypeId);
			sb.append(", vehicleLengthId=").append(vehicleLengthId);
			sb.append(", wheelNumber=").append(wheelNumber);
			sb.append(", vehicleLicenseTime='").append(vehicleLicenseTime).append('\'');
			sb.append(", icName='").append(icName).append('\'');
			sb.append(", icNum='").append(icNum).append('\'');
			sb.append('}');
			return sb.toString();
		} else {
			final StringBuilder sb = new StringBuilder();
			sb.append("UserProfile");
			sb.append("{id=").append(id);
			sb.append(", username='").append(username).append('\'');
			sb.append(", type='").append(type).append('\'');
			sb.append(", provinceId=").append(provinceId);
			sb.append(", cityId=").append(cityId);
			sb.append(", countyId=").append(countyId);
			sb.append(", address='").append(address).append('\'');
			sb.append(", contactor='").append(contactor).append('\'');
			sb.append(", mobile='").append(mobile).append('\'');
			sb.append(", telephone='").append(telephone).append('\'');
			sb.append(", qq='").append(qq).append('\'');
			sb.append(", score='").append(score).append('\'');
			sb.append(", membershipName='").append(membershipName).append('\'');
			sb.append(", membershipStartTime=").append(membershipStartTime);
			sb.append(", membershipExpireTime=").append(membershipExpireTime);
			sb.append(", companyName='").append(companyName).append('\'');
			sb.append(", companyType='").append(companyType).append('\'');
			sb.append(", companyScale='").append(companyScale).append('\'');
			sb.append(", companyLicenseNo='").append(companyLicenseNo).append('\'');
			sb.append(", companyWebsite='").append(companyWebsite).append('\'');
			sb.append(", representative='").append(representative).append('\'');
			sb.append(", companyDescription='").append(companyDescription).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}

	public boolean isOfDriverType() {
		return "GTSJ".equalsIgnoreCase(this.type) || "QYSJ".equalsIgnoreCase(this.type);
	}
}

