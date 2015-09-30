package com.wlqq.huodi.json;


import com.wlqq.huodi.bean.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Tiger Tang
 * @since 110612 Date: 11-12-29
 */
public final class UserProfileParser implements Parser<UserProfile> {

	public static final UserProfileParser instance = new UserProfileParser();

	private UserProfileParser() {
	}

	public static UserProfileParser getInstance() {
		return instance;
	}

	public UserProfile parse(final String content) throws JSONException {
		final JSONObject jsonObject = new JSONObject(content);

		final UserProfile userProfile = new UserProfile();

		userProfile.setId(jsonObject.optLong("id"));
		userProfile.setUsername(jsonObject.optString("un"));
		userProfile.setType(jsonObject.optString("ut"));
		userProfile.setProvinceId(jsonObject.optLong("pid"));
		userProfile.setCityId(jsonObject.optLong("cid"));
		userProfile.setCountyId(jsonObject.optLong("cntid"));
		userProfile.setAddress(jsonObject.optString("addr"));
		userProfile.setContactor(jsonObject.optString("cp"));
		userProfile.setMobile(jsonObject.optString("m"));
		userProfile.setMobile1(jsonObject.optString("m1"));
		userProfile.setMobile2(jsonObject.optString("m2"));
		userProfile.setTrailerAxleType(jsonObject.optString("trailerAxleType"));
		userProfile.setBoxStructure(jsonObject.optString("boxStructure"));
		userProfile.setMobile3(jsonObject.optString("m3"));
		userProfile.setIcName(jsonObject.optString("icname"));
		userProfile.setIcNum(jsonObject.optString("icnum"));
		userProfile.setTelephone(jsonObject.optString("p"));
		userProfile.setQq(jsonObject.optString("qq"));
		userProfile.setMember(jsonObject.optBoolean("member"));
		userProfile.setVoip(jsonObject.optString("voip"));
		userProfile.setSystemTime(new Date(jsonObject.optLong("sti")));

		userProfile.setMembershipName(jsonObject.optString("sn"));
		Date sst = new Date(jsonObject.optLong("sst"));
		userProfile.setMembershipStartTime(sst);

		userProfile.setMembershipExpireTime(new Date(jsonObject.optLong("set")));

		if (userProfile.isOfDriverType()) {
			userProfile.setVehicleBrandName(jsonObject.optString("vb_n"));
			userProfile.setPlateNumber(jsonObject.optString("vn"));
			userProfile.setEngineBrandName(jsonObject.optString("eb_n"));
			userProfile.setEnginePower(jsonObject.optString("ep"));
			userProfile.setVehicleTypeName(jsonObject.optString("ct_n"));
			userProfile.setVehicleLengthName(jsonObject.optString("vl_n"));
			userProfile.setWheelNumber(jsonObject.optInt("wn"));
			userProfile.setVehicleLicenseTime(jsonObject.optString("vlt"));
		} else {
			userProfile.setCompanyName(jsonObject.optString("cn"));
			userProfile.setCompanyType(jsonObject.optString("ct"));
			userProfile.setCompanyScale(jsonObject.optString("cs"));
			userProfile.setCompanyLicenseNo(jsonObject.optString("cln"));
			userProfile.setCompanyWebsite(jsonObject.optString("cw"));
			userProfile.setRepresentative(jsonObject.optString("crep"));
			userProfile.setCompanyDescription(jsonObject.optString("cintro"));
			userProfile.setImageFolderDir(jsonObject.optString("uaf"));
			userProfile.setReasonNum(jsonObject.optString("rsn"));
			userProfile.setAtta(jsonObject.optInt("atta"));
			final String ias = jsonObject.optString("ias");
			if (StringUtils.isNotBlank(ias))
				userProfile.setAuditState(UserProfile.AuditState.valueOf(ias));
		}

		return userProfile;
	}
}
