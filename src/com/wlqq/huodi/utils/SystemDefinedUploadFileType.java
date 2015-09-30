package com.wlqq.huodi.utils;

/**
 * @author Tiger Tang
 * @since 062
 *        Date: -9-23
 */
public enum SystemDefinedUploadFileType {

	COMPANY_LICENSE("license", "公司营业执照"),
	NATIONAL_TAX_REGISTRATION("national-tax", ""),
	LOCAL_TAX_REGISTRATION("local-tax", ""),
	ORGANIZATION_IDENTIFICATION_CODE("organization", ""),
	COMPANY_LOGO("logo", ""),
	IDENTITY_CARD("identity", "identity.jpg"),
	DRIVING_LICENSE("license", "license.jpg"),
	TRANSPORTATION_CERTIFICATE("certificate", "车辆行驶证.jpg"),
	VEHICLE_LICENSE("vehicle-license", "车辆行驶证.jpg"),
	VEHICLE_INSURANCE("vehicle-insurance", "保险卡.jpg"),
	VEHICLE_OPERATION_LICENSE("vehicle-certificate", "车辆营运许可证.jpg"),
	TRAILER_LICENSE("trailer-license", "挂车行驶证扫描件.jpg"),
	FULL_SHOT_PHOTO("full-shot-photo", "full-shot-photo.jpg"),
	VEHICLE_PHOTO_FRONT("vehicle-front", "车头.jpg"),
	VEHICLE_PHOTO_SIDE("vehicle-side", "vehicle-side.jpg"),
	VEHICLE_PHOTO_BEHIND("vehicle-behind", "vehicle-behind.jpg"),
	CONTRACT_SCAN_FILE("contract", "车辆入网协议.jpg"),
	BIZ_SITUS("biz-situs", ""),
	VEHICLE_PHOTO("vehicle","车辆照片.jpg"),  //车辆照片
	ALL_PHOTO("all","人车合照.jpg"), //人车合照
    CERTIFICATES_IN_ONE("all-in-one","证件照.jpg"), // 证件照
    FIGURE("head_portrait","head_portrait.jpg");// 头像

	private String remoteFilePath;
	private String localStorePath;

	public String getRemoteFilePath() {
		return remoteFilePath;
	}

	public String getLocalStorePath() {
		return localStorePath;
	}

	private SystemDefinedUploadFileType(String localStorePath, String remoteFilePath) {
		this.localStorePath = localStorePath;
		this.remoteFilePath = remoteFilePath;
	}

	public static SystemDefinedUploadFileType fromFilePath(String fileName) {
		for (SystemDefinedUploadFileType type : SystemDefinedUploadFileType.values()) {
			if (type.getRemoteFilePath().equals(fileName)) {
				return type;
			}
		}

		return null;
	}
}
