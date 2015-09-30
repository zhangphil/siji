package com.wlqq.huodi.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.HomeActivity;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.JsonResponse;
import com.wlqq.huodi.bean.LoginResponse;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.exception.ServerInternalException;
import com.wlqq.huodi.exception.WuliuQQException;
import com.wlqq.huodi.handler.ServiceExpiredHandler;
import com.wlqq.huodi.handler.UnauthorizedDeviceHandler;
import com.wlqq.huodi.handler.UserNameOrPwdWrongHandler;
import com.wlqq.huodi.http.ServiceInvoker;
import com.wlqq.huodi.json.LoginResponseParser;
import com.wlqq.huodi.json.Parser;
import com.wlqq.huodi.utils.AlarmManagerUtil;
import com.wlqq.huodi.utils.DeviceUtils;
import com.wlqq.huodi.utils.HostProvider;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.VersionUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tiger Tang
 *         Date: 12-1-3
 *         Time: 下午5:40
 * @since 0.1.20
 */
public class SignInTask extends GenericRemoteTask<LoginResponse> {

	public SignInTask(Activity activity) {
		super(activity);

		registerExceptionHandler(ErrorCode.SERVICE_EXPIRED, new ServiceExpiredHandler.ServiceExpiredChild());
		registerExceptionHandler(ErrorCode.UNAUTHORIZED_DEVICE, UnauthorizedDeviceHandler.getInstance());
		registerExceptionHandler(ErrorCode.USER_NOT_EXIST, UserNameOrPwdWrongHandler.getInstance());
	}

	@Override
	protected HostProvider.HostType getHostType() {
		return HostProvider.HostType.SSO;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected TaskResult<LoginResponse> callRemoteService(String serviceUrl, Map<String, Object> params) throws IOException, ServerInternalException, JSONException {
		SavedCredential.getInstance().setAuthType(SavedCredential.AuthType.WULIUQQ);
		SavedCredential.getInstance().setPrincipal((String) params.get(HuoDiConstants.HTTP_PARAM_USERNAME));
		SavedCredential.getInstance().setCredential((String) params.get(HuoDiConstants.HTTP_PARAM_PASSWORD));

		params.put(HuoDiConstants.HTTP_PARAM_CLIENT, VersionUtils.getClientOSVersion());
		params.put(HuoDiConstants.HTTP_PARAM_VERSION, VersionUtils.getCurrentVersion());
		params.put(HuoDiConstants.HTTP_PARAM_VERSION_CODE, VersionUtils.getCurrentVersionCode());
		params.put(HuoDiConstants.HTTP_PARAM_DEVICE_FINGERPRINT, DeviceUtils.getDeviceFingerprint());

		final LoginResponseParser parser = LoginResponseParser.getInstance();
		try {
			String serverHost = HostProvider.getHostDomain(getHostType(), isGetIp);
			JsonResponse<LoginResponse> invoke = null;
			invoke = ServiceInvoker.invoke(serverHost, getRemoteServiceAPIUrl(), params, parser, null, true);
			TaskResult<LoginResponse> loginResponseTaskResult = new TaskResult<LoginResponse>(TaskResult.Status.OK, invoke.getContent());
			return loginResponseTaskResult;
		} catch (WuliuQQException e) {
			return new TaskResult(TaskResult.Status.ERROR, e.getErrorCode());
		} catch (ServerInternalException e) {
			e.printStackTrace();
		}
		return new TaskResult(TaskResult.Status.ERROR);
	}

	@Override
	protected boolean isShowProgressDialog() {
		return true;
	}

	@Override
	protected String getProgressDialogMessage() {
		return activity.getString(R.string.msg_logining);
	}

	@Override
	protected String getRemoteServiceAPIUrl() {
		return HuoDiConstants.API_DRIVER_URL_LOGIN;
	}

	@Override
	protected void onSucceed(LoginResponse loginResponse) {
        AuthenticationHolder.setLogin(true);
		Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);
		final Session session = loginResponse.getSession();
		final UserProfile profile = loginResponse.getProfile();

		AuthenticationHolder.setSession(session);
		AuthenticationHolder.setProfile(profile);

		AlarmManagerUtil.sendLocationBroadcast(activity);

		final Context context = HuoDiApplication.getContext();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {


			}
		}, 1000);


		final Intent intent = new Intent(activity, HomeActivity.class);

		activity.startActivity(intent);
		activity.finish();
//		final Date expireTime = profile.getMembershipExpireTime();
//		final Date now = DateUtils.truncate(session.getStartTime(), Calendar.DATE);
//		if (expireTime != null && DateUtils.addDays(now, 2).after(expireTime)) {
//			final long expireDays = (expireTime.getTime() - now.getTime()) / (24 * 60 * 60 * 1000);
//
//			final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//			builder.setTitle(activity.getString(R.string.tips));
//			if (expireDays > 0) {
//				builder.setMessage(String.format(activity.getString(R.string.msg_membership_expiration_alert),
//						expireDays, activity.getString(R.string.customer_service_tel)));
//			} else {
//				builder.setMessage(String.format(activity.getString(R.string.msg_membership_expiration),
//						activity.getString(R.string.customer_service_tel)));
//			}
//			builder.setPositiveButton(R.string.continueBuy, new DialogInterface.OnClickListener() {
//
//				public void onClick(DialogInterface dialogInterface, int i) {
//					Intent intent1 = new Intent(activity, HuoDiApplication.getBuyMembershipActivityInstance());
//					intent1.setAction("LoginActivity");
//					activity.startActivity(intent1);
//					activity.finish();
//				}
//			});
//			builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialogInterface, int i) {
//					activity.startActivity(intent);
//					activity.finish();
//				}
//			});
//			builder.show();
//		} else {
//
//		}

	}

	@Override
	protected void onError(ErrorCode errorCode) {
		super.onError(errorCode);
	}

	@Override
	protected Parser<LoginResponse> getResultParser() {
		return LoginResponseParser.getInstance();
	}

	@Override
	protected boolean isSecuredAction() {
		return false;
	}
}
