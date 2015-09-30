package com.wlqq.huodi.utils;


import android.os.Environment;

import com.wlqq.huodi.app.HuoDiApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HuoDiConstants {

	//	public static final String BMAP_APPKEY = "697a2ae5a335560b87190ed0316c0a54";
	public static final String BMAP_APPKEY = "5KIINl3pnH9yHO7ja9RQLxin";
	public static final String APK_PATH = Environment.getExternalStorageDirectory().getPath() + "/wlqq/app/driver%s.apk";
	//    public static final String APK_PATH="/mnt/storage/sdcard0/wlqq/app/driver%s.apk";
	public static final String APK_SPARE_PATH = HuoDiApplication.getContext().getFilesDir() + "/wlqq/app/driver%s.apk";
	public static final String ADV_VIEW_EVENTS_FILE = Environment.getExternalStorageDirectory().getPath() + "/wlqq/adv/view-events.txt";

	public static final String VERSION = "0.2.0";

	public static final DateFormat DF_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DF_HH_mm = new SimpleDateFormat("HH:mm");
	public static final DateFormat DF_MM_dd = new SimpleDateFormat("MM-dd");
	public static final DateFormat DF_MM_dd_HH_mm_ss = new SimpleDateFormat("MM-dd HH:mm:ss");
	public static final DateFormat DF_YYYY_MM_dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final String HTTP_PARAM_SID = "sid";
	public static final String HTTP_PARAM_ST = "st";

	public static final String HTTP_PARAM_PID = "pid";
	public static final String HTTP_PARAM_CID = "cid";

	public static final String HTTP_PARAM_USERNAME = "username";
	public static final String HTTP_PARAM_PASSWORD = "password";
	public static final String HTTP_PARAM_CLIENT = "client";
	public static final String HTTP_PARAM_VERSION = "version";
	public static final String HTTP_PARAM_VERSION_CODE = "vc";

	public static final String HTTP_PARAM_MSG_ID = "msgId";

	public static final String HTTP_PARAM_BRAND_ID = "bid";
	public static final String HTTP_PARAM_FPID = "fpid";
	public static final String HTTP_PARAM_FCID = "fcid";
	public static final String HTTP_PARAM_FCNTID = "fcntid";
	public static final String HTTP_PARAM_TPID = "tpid";
	public static final String HTTP_PARAM_TCID = "tcid";
	public static final String HTTP_PARAM_MESSAGE_TYPE = "t";
	public static final String HTTP_PARAM_VEHICLE_TYPE = "tt";
	public static final String HTTP_PARAM_VEHICLE_LENGTH = "tl";
	public static final String HTTP_PARAM_WHEEL_NUMBER = "wn";
	public static final String HTTP_PARAM_KEYWORD = "kw";
	public static final String HTTP_PARAM_FETCH_SIZE = "fs";
	public static final String HTTP_PARAM_INDEX = "idx";
	public static final String HTTP_PARAM_MESSAGE_WHEEL = "wn";
	public static final String HTTP_PARAM_MESSAGE_CONTENT = "cnt";
	public static final String HTTP_PARAM_MESSAGE_RC = "rc";
	public static final String HTTP_PARAM_MESSAGE_RI = "ri";
	public static final String HTTP_PARAM_MOBILE = "mobile";
	public static final String HTTP_PARAM_MOBILE2 = "mobile2";
	public static final String HTTP_PARAM_TEL = "tel";
	public static final String HTTP_PARAM_QQ = "qq";
	public static final String HTTP_PARAM_CATEGORY_ID = "categoryId";
	public static final String HTTP_PARAM_HIGH_INDEX = "h";
	public static final String HTTP_PARAM_LOW_INDEX = "l";
	public static final String HTTP_PARAM_COMPANY_NAME = "cn";
	public static final String HTTP_PARAM_ADDRESS = "addr";
	public static final String HTTP_PARAM_CONTACTOR = "cct";
	public static final String HTTP_PARAM_DEVICE_FINGERPRINT = "dfp";
	public static final String API_URL_GET_USER_PROFILE = "/desktop4pc/profile/my-profile.do";
	public static final String API_URL_DISPATCH = "/v1.1/mobile/dispatch.do";
	public static final String API_DRIVER_URL_LOGIN = "/mobile/auth/driver/nearly/android/login";
	public static final String API_GET_SMS_CODE = "/mobile/nearly/send-verification-code-sms.do";
	public static final String API_SUBMIT_BOOKING_INFO = "/mobile/nearly/reservation.do";
	public static final String API_REGISTER = "/mobile/nearly/register.do";
	public static final String API_URL_DRIVIER_TAUTH_SIGN_IN = "/mobile/tauth/driver-sign-in";
	public static final String API_CHECK_PLN_IS_EXIST = "/mobile/nearly/check-platenumber-exist.do";
	public static final String API_CHECK_VEHICLE_STATUS = "/mobile/nearly/check-vehicle-status.do";
	public static final String API_CHECK_USER_AUDITED_STATUS = "/mobile/nearly/check-user-audited-status.do";

	public static final String API_URL_INSURANCE_HISTORY = "/mobile/insurance/freight/applied-records.do";
	public static final String API_URL_INSURANCE_MAINITEM_LIST = "/mobile/insurance/freight/mainitem-list.do";

	public static final String API_URL_LOCATION = "/mobile/vehicle/location-onTime.do";
	public static final String API_URL_LOCATION_CACHE = "/mobile/vehicle/location-delay.do";

	//实名认证申请查询状态
	public static final String API_URL_DATA_SERVICES = "/mobile/data-service/query.do";
	public static final String API_URL_MODIFY_LOGIN_PASSWORD = "/mobile/security/reset-pwd.do";

	public static final String API_URL_NEARBY_PARK = "/mobile/remote/nearby_parking_lot_list.do";
	public static final String API_URL_REGION_PARK = "/mobile/remote/parking_lot_list.do";

	public static final String API_URL_MSGBOARD_HISTORY = "/mobile/message/before-message";
	public static final String API_URL_POST_CLOSE_FREIGHT = "/mobile/message/close-message";
	public static final String API_URL_POST_DELETE_FREIGHT = "/mobile/message/delete-message";
	public static final String API_URL_POST_RESEND_FREIGHT = "/mobile/message/resend-message";

	public static final String API_URL_GET_INSURANCE_MONEY = "/mobile/insurance/freight/remaining-premium.do";
	public static final String API_URL_APPLY_INSURANCE = "/mobile/insurance/freight/apply.do";

	public static final String API_URL_BUY_MSG_PHONES = "/mobile/msg/buy-msg-phones.do";
	public static final String API_URL_GET_MSG_PHONES = "/mobile/msg/phones.do";
	//获取话费余额
	public static final String API_URL_GET_REMAIN_CHARGES = "/mobile/voip/remaining-charges.do";
	//获取话费充值记录
	public static final String API_URL_PAY_RECORD = "/mobile/service/list.do";

	public static final String API_URL_SUBMIT_LOCATION = "/mobile/vehicle/location-inst.do";
	//获取已购买信息
	public static final String API_URL_INFORMATION_ALREADY_BUY = "/mobile/message/already-buy-messages.do";
	//获取已购买信息
	public static final String API_URL_VOIP_DETAIL = "/mobile/voip/voipcall-record.do";
	//获取语音验证码
	public static final String API_URL_GET_CHECK_WORD = "/mobile/voip/get-voice-auth-code.do";
	//修改绑定电话
	public static final String API_URL_UPDATE_VOIP_MOBILE = "/mobile/voip/update-voip-mobile.do";

	public static final String API_URL_CALL_BACK = "/mobile/voip/callback.do";
	//获取广告信息
	public static final String API_URL_GET_ALL_ADVTISEMENT = "/mobile/adv/list.do";
	//点击后提交广告ID
	public static final String API_URL_ADV_CLICK_COMMIT = "/mobile/adv/record-click-event.do";
	//浏览后提交广告ID
	public static final String API_URL_ADV_VIEW_COMMIT = "/mobile/adv/record-view-event.do";
	//提交之前上传失败保存到本地的广告信息
	public static final String API_URL_ADV_CATHE_COMMIT = "/mobile/adv/record-cached-events.do";

	public static final int CATALOG_ID_FOR_CHARGES = 9;//话费产品ID   用于话费充值记录查询
	public static final int CATALOG_ID_FOR_PRODUCT = 2;//会员续费产品ID   用于会员续费记录查询
	public static final int DEFAULT_DOMAIN_ID = 1;
	public static final int HTTP_STATUS_BAD_REQUEST = 400;
	public static final int TOAST_SHOW_LENGTH = 1000;

	public static final String JSON_RESPONSE_STATUS = "status";
	public static final String JSON_RESPONSE_CONTENT = "content";
	public static final String JSON_RESPONSE_STATUS_OK = "OK";
	public static final String JSON_RESPONSE_STATUS_ERROR = "ERROR";
	public static final String ERROR = "error";

	public static final String ANDROID_VERSION_PREFIX = "Android_v";
	public static final String APK_STORE_FILENAME = "WuliuQQ-Android.apk";

	public static final int DEFAULT_MAP_ZOOM_LEVEL = 16;

	public static final String INTENT_KEY_LATITUDE = "latitude";
	public static final String INTENT_KEY_LONGITUDE = "longitude";
	public static final String INTENT_KEY_LATE6 = "latitudeE6";
	public static final String INTENT_KEY_LNGE6 = "longitudeE6";
	public static final String INTENT_KEY_ADDRESS = "address";
	public static final String INTENT_KEY_PID = "intent-key-pid";
	public static final String INTENT_KEY_CID = "intent-key-cid";
	public static final String FROM_SEARCH_TAB_ACTIVITY = "from_search_tab_activity";

	// Constants used for identifying startActivityForResult() method's param REQUEST_CODE
	public static final int REQUEST_CODE_REGION_FILTER = 0;
	public static final int REQUEST_CODE_DESTINATION = 1;
	public static final int REQUEST_CODE_MSGBOARD_SEARCH_PARAM = 2;
	public static final int REQUEST_CODE_MR_SEARCH_PARAM = 3;
	public static final int REQUEST_CODE_BROKER_SEARCH_PARAMS = 7;
	public static final int REQUEST_CODE_USEDTRUCK_SEARCH_PARAM = 9;
	public static final int REQUEST_CODE_START_REGION_FILTER = 13;
	public static final int REQUEST_CODE_DESTINATION_REGION_FILTER = 14;

	public static final int INVALID_INTEGER_VALUE = -1;
	public static final String UPLOAD_PICTURE = "/mobile/nearly/vehicleuser/upload-image.do";

	public static DateFormat MM_dd_HH_mm = new SimpleDateFormat("MM-dd HH:mm");
	public static DateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat yyyy_M_d = new SimpleDateFormat("yyyy-M-d");

	public static final String HTTP_PARAM_USEDTRUCK_PLACE = "pid";
	public static final String HTTP_PARAM_VEHICLE_BRAND_ID = "vb";
	public static final String HTTP_PARAM_ENGINE_BRAND_ID = "eb";
	public static final String HTTP_PARAM_USEDTRUCK_PRICE = "p";
	public static final String HTTP_PARAM_VEHICLE_WHEELNUMBER = "w";
	public static final String HTTP_PARAM_USEDTRUCK_VLENGTH = "vl";

	public static final String REGION_SELECTOR_CITY_ONLY = "OnlyCity";
	public static final String REGION_SELECTOR_PROVINCE_ONLY = "OnlyProvince";
	public static final String REGION_SELECTOR_PROVINCE_OR_CITY = "ProvinceOrCity";

	public static final String HTTP_PARAM_PLATE_NUMBER = "pln";
	public static final String HTTP_PARAM_VEHICLE_BRAND_NAME = "bn";
	public static final String HTTP_PARAM_ENGINE_BRAND_NAME = "ebn";
	public static final String HTTP_PARAM_ENGINE_POWER = "ep";
	public static final String HTTP_PARAM_VEHICLE_LICENSE_TIME = "vlt";
	public static final String HTTP_PARAM_IC_NAME = "icname";
	public static final String HTTP_PARAM_IC_NUM = "icnum";

	public static final String ONLINE_CONFIG_PARAM_CST = "CustomerServiceTel";
	public static final String ONLINE_CONFIG_PARAM_ABC_ACCOUNT_NUM = "ABCAccountNum";
	public static final String ONLINE_CONFIG_PARAM_CBC_ACCOUNT_NUM = "CBCAccountNum";

	public static final int GET_LATLNG_TYPE_BROKER = 1;
	public static final int GET_LATLNG_TYPE_QIPEI = 2;
	public static final int GET_LATLNG_TYPE_MR = 4;
	public static final int GET_LATLNG_TYPE_CS = 5;

	public static final String PREF_ACTIVATED = "ACTIVATED";
	public static final String SPLASH_PIC_VERSION = "SplashPicVersion";
	public static final String REPEATE_MSG = "RepeatPostVCMsg";
	public static final String SPLASH_PIC_URL = "SplashPicUrl";
	public static final String SPLASH_PIC_PATH = "/data/data/com.wlqq/files/splash_bg.png";

	public static final int PROGRESS_DIALOG = 13 << 1;

	public static final int DEFAULT_PAGE_NUMBER = 1;
	public static final String HTTP_PARAM_PN = "pn";
	public static final String HTTP_PARAM_PAGE_SIZE = "ps";
	public static final String HTTP_PARAM_PS = "ps";
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String FSTP_FILE_NAME = "full-shot-photo.jpg";

	//运单照片
	public static final String WAYBILL_FILE_NAME = "waybill.jpg";

	//figure 头像
	public static final String FIGURE_FILE_NAME = "figure.jpg";
	//证件合照
	public static final String ALL_IN_ONE_FILE_NAME = "all-in-one.jpg";

	//店面照片
	public static final String STORE_FILE_NAME = "biz-situs.jpg";

	//营业执照
	public static final String COMPANY_LICENSE_FILE_NAME = "license.jpg";

	public static final String ACTION_FROM_FREIGHT_DETAILS_ACTIVITY = "from_FreightDetailsActivity";


	public static final String PRODUCT_CONTENT = "productContent";
	public static final String HTTP_PARAM_PAGE_NUM = "pn";

	public static final String HTTP_PARAM_LAT = "lat";
	public static final String HTTP_PARAM_LON = "lon";
	public static final String HTTP_PARAM_LNG = "lng";
	public static final String HTTP_PARAM_RADIUS = "radius";

	public static final String RADIUS = "30000";

	public static final String UNIWAP_PROXY_SERVER = "10.0.0.172"; // cmwap、uniwap和3gwap所用代理地址都10.0.0.172:80

	public static final String CTWAP_PROXY_SERVER = "10.0.0.200"; // ctwap所用代理地址为10.0.0.200：80

	public static final String INSURANCE_MAINITEM = "InsuranceMainItem";

	public static final String INSURANCE_FILE_DIR = Environment.getExternalStorageDirectory() + "/太平洋保单/";

	public static final String LOCATION_CACHE_FILE = Environment.getExternalStorageDirectory() + "/location.txt";

	public static final String DEFAULT_RESPONSE_SUCCESS_CODE = "000000";
	public static final String DEFAULT_RESPONSE_CODE_TEL_USED_BEFORE = "000001";

	public static final String ADV_PATH_DOWNLOAD_AFTER = Environment.getExternalStorageDirectory() + "/wlqq/adv/adv%s.jpg";
	public static final String ADV_PATH_SPARE_DOWNLOAD_AFTER = HuoDiApplication.getContext().getFilesDir() + "/adv/adv%s.jpg";
	public static final String HOME_ADV_NAME_KEY = "adv";
	public static final String PLN = "pln";
	public static final String ADV_SHAREDPREFERENCES_KEY = "alladvertisement";
	public static final String ADV_SPLASH_LAST_PLAY_NUM = "splashAdvNum";
	public static final String DEVICE_ID = "deviceId";
	public static final String APK_TAG = "apk";

	public static final String ADV_CLICK_TYPE = "Click";
	public static final String ADV_VIEW_TYPE = "View";

	public static final int DEFAULT_DELAY = 2000;


	public static final String ACTION_SEND_LOCATION = "com.wlqq.SEND_LOCATION";
	public static final String ACTION_START_SEND_LOCATION = "com.wlqq.START_SEND_LOCATION";
	public static final String ACTION_STOP_SEND_LOCATION = "com.wlqq.STOP_SEND_LOCATION";
}
