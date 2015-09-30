package com.wlqq.huodi.exception;

/**
 * User: xlw
 * Date: 14-11-17
 * Email: xlwplm@qq.com
 */
public enum ErrorCode {

	JSON_EXCEPTION("10002", ""),
	UNKNOWN_ERROR("-1", ""),
	SERVER_INTERNAL_EXCEPTION("100003", ""),
	INTERNAL_ERROR("0", "服务器内部错误"),
	INTERNAL_ERROR2("100000", "服务器内部错误"),
	INVALID_SESSION("1", "错误的会话"),
	INVALID_PARAMETERS("3", "输入参数不正确"),
	INVALID_PARAMETERS2("100001", "参数错误"),
	ACCESS_DENIED("4", "无权访问"),
	NOT_AUTHENTICATED("5", "您还未登录"),
	MAX_MSG_PER_DAY("8", "您已达到每日货运信息发布数上限"),
	MIN_MSG_INTERVAL("9", "您的操作太过频繁，请稍后再试"),
	USERNAME_OR_PWD_WRONG("10", "用户名或密码错误"),
	USER_ALREADY_EXISTS("11", "用户名已存在，请选择其他用户名"),
	IDENTICAL_MSG_MIN_INTERVAL("18", "您的重发信息操作太过频繁，请稍后再试"),
	ILLEGAL_CONTENT("19", "您发布的信息内容包含不合法内容，请仔细检查或致电0851-8666156详询"),
	SERVICE_EXPIRED("25", "您所购买的会员服务已到期，请到就近的服务网点充值续费，或拨打我们的客服热线0851-8666156充值"),
	INVALID_USER_STATE("26", "您的账号已被禁用，请致电0851-8666156详询"),
	UNAUTHORIZED_DEVICE("28", "抱歉，您的账号还未在此设备上进行授权！每个帐号限定只能在两台终端上登录，如需变更设备，请拨打我们的客服热线%s。"),
	USER_NOT_EXIST("29", "抱歉，不存在此用户！\n请检查您的用户名、密码输入是否正确！"),
	VEHICLE_ALREADY_EXISTS("30", "该车牌号码已存在，不能重复注册！\n如果您没有注册该车牌号或者忘记了用户名/密码，请拨打我们的客服热线%s。"),
	PAY_PASSWORD_ERROR("31", "支付密码错，请重新输入正确的支付密码"),
	ORIGINAL_OR_PWD_WRONG("38", "原密码错误"),
	UNKNOWN("10000", "出现未知错误，请稍后再试"),
	ID_CARD_CHECK_PASSWORD_ERROR("31", "支付密码错误，请重新输入！"),
	ID_CARD_CHECK_NO_CHECK_TIME("32", "您的账户没有身份证验证次数了，请购买验证套餐！"),
	ID_CARD_CHECK_CAN_NOT_CONNECT_CHECK_CENTER("33", "无法连接身份证验证中心,请稍后再试！"),
	RECHARGE_CARD_EXPIRE("0000000280", "卡已过期"),
	RECHARGE_CARD_PARAMETER_ERROR_YIHUBAO("100010280", "易宝支付提交返回参数错误"),
	RECHARGE_CARD_CAN_NOT_CONNECTED("100100280", "网络连接错误，无法连接到易宝支付"),
	RECHARGE_CARD_FAIL("-10280", "#errorCode,-1，签名校验失败"),
	RECHARGE_CARD_SUCCEED("00280", "销卡成功，订单成功"),
	RECHARGE_CARD_FAIL_1("10280", "销卡成功，订单失败"),
	RECHARGE_CARD_NOT_CONFORM("70280", "充值卡序列号密码或面额不符合规则"),
	RECHARGE_CARD_SUBMIT_FREQUENTLY("10020280", "您提交过于频繁，请您稍后再试"),
	RECHARGE_CARD_NOT_SUPPORT("10030280", "系统不支持您的充值卡类型"),
	RECHARGE_CARD_PASSWORD_ERROR_OR_INVALID("10040280", "密码错误或充值卡无效"),
	RECHARGE_CARD_IS_INVALID("10060280", "充值卡无效"),
	RECHARGE_CARD_BALANCE_LESS("10070280", "卡内余额不足"),
	RECHARGE_CARD_OUT_OF_DATE("10080280", "余额卡过期（有效期1个月）"),
	RECHARGE_CARD_PROCESSING("10100280", "此卡正在处理中"),
	RECHARGE_CARD_UNKOWN_ERROR("100000280", "未知错误"),
	RECHARGE_CARD_HAS_BEEN_USED("20050280", "此卡已使用"),
	RECHARGE_CARD_PASSWORD_PROCESSING("20060280", "卡密在系统处理中"),
	RECHARGE_CARD_DUMMY("20070280", "该卡为假卡"),
	RECHARGE_CARD_MAINTENANCE("20080280", "该卡种正在维护"),
	RECHARGE_CARD_ZHEJIANG_MAINTENANCE("20090280", "浙江省移动系统维护中"),
	RECHARGE_CARD_JIANGSU_MAINTENANCE("20100280", "江苏省移动系统维护中"),
	RECHARGE_CARD_FUJIAN_MAINTENANCE("20110280", "福建省移动系统维护中"),
	RECHARGE_CARD_LIAONING_MAINTENANCE("20120280", "辽宁省移动系统维护中"),
	RECHARGE_CARD_NOT_EXIST("30010280", "此卡不存在"),
	RECHARGE_CARD_HAVE_USED("30020280", "此卡已使用过"),
	RECHARGE_CARD_INVALID("30030280", "此卡已作废"),
	RECHARGE_CARD_FREEZE("30040280", "此卡已冻结"),
	RECHARGE_CARD_NOT_ACTIVATE("30050280", "此卡未激活"),
	RECHARGE_CARD_PASSWORD_ERROR("30060280", "密码不正确"),
	RECHARGE_CARD_IS_PROCESSING("30070280", "此卡正在处理中"),
	RECHARGE_CARD_SYSTEM_ERROR("31010280", "系统错误"),
	RECHARGE_CARD_OUT_DATE("31020280", "此卡已过期"),
	RECHARGE_CARD_TIME_OUT("100020280", "支付超时"),
	RECHARGE_CARD_PARAMETER_ERROR("30280", "参数不正确"),
	RECHARGE_CARD_NO_PERMISSION("40280", "无权执行此操作"),
	API_IS_INVALID("1000", "抱歉，您安装的软件版本过低，暂时无法使用此功能，请升级本软件到最新版本重试！"),
	CONCURRENT_LOGIN_ERROR("1001", "抱歉，您的软件已在其他地方登录，您已被下线，请重新登录后再使用！"),
	NEARLY_VEHICLE_USER_REGISTER_DEVICE_LIMIT("170001", "同一设备不能多次注册"),
	AMS_REMOTE_ERROR("10000", "调用账户管理系统出错"),
	AMS_INVALID_CONSUME_AMOUNT("10001", "未指定金额"),
	AMS_DOMAIN_NOT_FOUND("10002", "域未找到"),
	AMS_INVALID_PAY_PASSWORD("10003", "支付密码错误"),

	AMS_NO_REMAINING_USER_SERVICES("10004", "no remaining user services!"),
	AMS_NOT_ENOUGH_BALANCE("10005", "账户余额不足"),
	AMS_PRODUCT_CATALOG_IS_NOT_BY_COUNT("10006", "产品不以次数计算"),
	AMS_PRODUCT_CATALOG_NOT_EXIST("10007", "产品类型不存在"),
	AMS_PRODUCT_NOT_EXIST("10008", "产品不存在"),
	AMS_TRANSACTION_NOT_FOUND("10009", "交易未找到"),
	AMS_USER_ACCOUNT_FROZEN("10010", "账户已冻结"),
	AMS_USERNAME_ALREADY_EXISTS("10011", "用户名已存在"),
	AMS_USER_NOT_EXISTS("10012", "用户信息未找到"),
	AMS_USER_SERVICES_NOT_EXISTS("10013", "user services not found!"),

	AMS_VEHICLE_NOT_EXISTS("10014", "车辆未找到"),
	AMS_USER_ALREADY_DISABLED("10015", "用户已禁用！"),
	AMS_INVALID_TRANSACTION_TYPE("10016", "错误的交易类型"),
	AMS_TRANSACTION_CANNOT_BE_CANCELED("10017", "交易无法取消"),
	AMS_INVALID_PURCHASE_CHANNEL("10018", "错误的支付渠道"),
	AMS_INVALID_PAY_PWD("10019", "支付密码错误"),
	AMS_CANCEL_REASON_CANNOT_BE_EMPTY("10020", "取消交易原因不能为空"),
	AMS_CANCEL_TRANSACTION_TIME_LIMIT("10021", "交易已过"),
	AMS_PRODUCT_ALREADY_BE_CONSUMED("10022", "购买产品已使用！无法取消！"),

	AMS_INVALID_VEHICLE_USER_TYPE("10023", "非法的司机类型"),
	AMS_USE_PRODUCT_RECORD_NOT_EXIST("10024", "产品使用记录未找到"),
	AMS_BUY_PRODUCT_RECORD_NOT_EXIST("10025", "产品购买记录未找到"),
	AMS_USER_ACCOUNT_NOT_EXIST("10026", "账户未找到"),
	AMS_YEEPAY_WRONG_AMOUNT("10034", "支付金额有误"),
	AMS_YEEPAY_WRONG_CARD_NUMBER("10035", "业务状态不可用，未开通此类卡业务"),
	AMS_YEEPAY_CALL_ERROR("10036", "支付失败"),
	LOGIN_FROM_OTHER_DEVICE("120002", "您的账号在另一设备登录，您被迫下线"),
	SESSION_EXPIRED("120001", "登录已经过期"),
	FIS_CPIC_REMOTE_EXCEPTION("20000", "远程调用太平洋出错"),
	FIS_AMOUNT_EXCEEDS_MAXIMUM("20001", "保额不能超过协议规定的最大值（人民币1100万元）"),

	FIS_PREMIUM_BELOW_MINIMUM("20002", "保费不能低于协议规定的最小值（人民币5元）"),
	FIS_INVALID_SAIL_DATE("20003", "无效的起运日期！（起运日期不能早于当前时间）"),
	FIS_INVALID_ITEM("20004", "货物名称超出了协议规定的内容！（请仔细阅读投保条件）"),
	FIS_INVALID_MAIN_ITEM("20005", "无效的保险条款！"),
	FIS_INVALID_ROUTE("20006", "无效的起运/目的地（目前不支持西藏及港澳台地区的投保）"),
	FIS_POLICY_NOT_EXISTS("20007", "保险单号不存在！"),
	FIS_INVALID_PACK_CODE("20008", "包装方式无效"),
	FIS_POLICY_UNAUDITED("20009", "保单进入人工审核，1小时以后到投保记录查看审核结果"),
	FIS_APPLY_SUCCEED_BUT_EPOLICY_NOT_CREATED("20010", "投保成功但电子保单未生成，10分钟到投保记录查看电子保单生成结果"),
	ICS_REMOTE_ERROR("30000", ""),
	DXS_REMOTE_ERROR("40000", "远程调用数据交换服务器出错！"),
	DXS_INVALID_SYNC_TIMESTAMP("40001", "同步时间未指定！"),
	DXS_ACCESS_DATA_PERMISSION_DENIED("40002", "无权操作指定数据！"),
	AP_PERMISSION_SESSION_EXPIRED("50002", "访问时间已过期"),
	VOIP_REMOTE_ERROR("70001", "远程调用网络电话服务出错"),
	VOIP_MSG_NOT_FOUND("70002", "信息未找到"),
	VOIP_VOICE_VERIFIED_ERROR("70003", "语音验证出错"),
	VOIP_MOBILE_ALREADY_VERIFIED("70004", "该号码与您当前绑定号码相同,请更换号码"),
	VOIP_MOBILE_ALREADY_VERIFIED_BY_OTHER_USER("70005", "号码已被其他用户占用，请更换号码重试"),
	VOIP_INVALID_VERIFY_MOBILE("70006", "电话号码有误"),
	VOIP_VERIFY_TOO_OFTEN("70007", "40秒以内不能再次获取验证码"),
	VOIP_VERITY_OVER_FIVE_TIMES_PER_DAY("70008", "当天验证码获取超过5次"),
	VOIP_INVALID_VOICE_VERIFY_CODE("70009", "语音验证码无效"),
	VOIP_NOT_ENOUGH_VOIP_CHARGES("70010", "网络电话费余额不足，请您充值网络话费"),
	VOIP_CALL_ERROR("70011", "拨打网络电话出错,请稍后再试"),
	VOIP_NOT_BIND_MOBILE("70012", "使用的电话不是您当前绑定电话"),
	VOIP_MSG_PHONE_NOT_FOUND("70013", "信息电话未找到"),
	VOIP_VOIP_FUNCTION_FREEZE("70014", "您的网络电话功能已被锁定，详情请拨打客户热线0851-8666156咨询"),
	VOIP_CALL_TOO_FREQUENTLY("70015", "呼叫过于频繁，请稍候再试"),

	VOIP_MESSAGE_DONE("70016", "信息已成交"),

	AA_ACCESS_DENIED("1000001", "拒绝访问！您没有权限访问此资源！"),
	AA_INVALID_PARAM("1000002", "拒绝访问！参数列表不符合类型！"),
	AA_DISABLED_USER("1000003", "拒绝访问！用户已禁用！"),
	AA_BAD_CREDENTIAL("1000004", "拒绝访问！非法域！"),
	AA_BAD_SIGNATURE("1000005", "拒绝访问！非法数据！"),
	VERSION_LOW("1000006", "版本过底，请升级致更新版本"),

	PUSH_SYSTEM_REMOTE_ERROR("80001", "推送服务器调用出错"),
	PUSH_SYSTEM_INTERNAL_ERROR("80002", "推送服务内部错误"),
	PUSH_SERVER_CONNECTION_FAILED("80003", "JPush服务器连接失败"),
	PUSH_NOTIFY_PUSH_TYPE_INTERNAL("80004", "推送通知的类型无效"),
	PUSH_NOTIFY_TYPE_VALUE_INTERNAL("80005", "推送通知的具体类型值无效"),
	PUSH_NOTIFY_MSG_INTERNAL("80006", "推送通知的内容无效"),
	PUSH_APP_TYPE_NOT_FOUND("80007", "推送通知的客户端类型无效"),
	PUSH_CLIENT_NOT_FOUND("80008", "推送终端未找打"),
	PUSH_NOTIFY_FORMAT_INVALID("80009", "推送通知格式无效"),
	PUSH_NOTIFICATION_NOT_FOUND("80010", "通知未找到"),
	PUSH_APP_UPLOAD_FAILED("80011", "升级包上传失败"),
	PUSH_APP_ASSOC_NOTIFY_TYPE_NOT_FOUND("80012", "该应用版本占不支持发送此类型通知"),
	PUSH_SUB_SUBJECT_ALREADY_EXIST("80013", "主题已经存在了"),
	PUSH_SUB_SUBJECT_NOT_FOUND("80014", "主题未找到"),
	PUSH_SUB_SUBSCRIPTION_ALREADY_EXIST("80015", "订阅记录已经存在了"),
	DEVICE_NOT_AUTH("120009", "您的设备未被授权使用该应用"),
	NEARLY_INVALID_RESERVATION_TIME("170000", "无效的预约时间"),
	PUSH_SUB_SUBSCRIPTION_NOT_FOUND("80016", "订阅记录未找到");

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static ErrorCode fromCode(String code) {
		final ErrorCode[] values = ErrorCode.values();
		for (int i = 0, len = values.length; i < len; i++) {
			final ErrorCode errorCode = values[i];

			if (errorCode.getCode().equals(code)) {
				return errorCode;
			}
		}
		return ErrorCode.UNKNOWN;
	}
}
