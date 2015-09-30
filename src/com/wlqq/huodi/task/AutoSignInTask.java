package com.wlqq.huodi.task;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.wlqq.huodi.activity.HomeActivity;
import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.JsonResponse;
import com.wlqq.huodi.bean.LoginResponse;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.exception.ServerInternalException;
import com.wlqq.huodi.exception.WuliuQQException;
import com.wlqq.huodi.http.ServiceInvoker;
import com.wlqq.huodi.json.LoginResponseParser;
import com.wlqq.huodi.utils.DeviceUtils;
import com.wlqq.huodi.utils.HostProvider;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.VersionUtils;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tiger Tang
 *         Date: 12-1-3
 *         Time: 下午10:05
 * @since 0.1.20
 */
public class AutoSignInTask extends SignInTask {

	private static final String TAG = "AutoSignInAction";

	public AutoSignInTask(Activity activity) {
		super(activity);
	}

	@Override
	protected boolean isShowProgressDialog() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected TaskResult<LoginResponse> callRemoteService(String serviceUrl, Map<String, Object> params) throws IOException, ServerInternalException, JSONException {
		final SavedCredential savedCredential = SavedCredential.getInstance();

		final String principal = savedCredential.getPrincipal();
		final String credential = savedCredential.getCredential();

		if (StringUtils.isNotBlank(principal)) {
			params.put(HuoDiConstants.HTTP_PARAM_USERNAME, principal);
			params.put(HuoDiConstants.HTTP_PARAM_PASSWORD, credential);
			params.put(HuoDiConstants.HTTP_PARAM_CLIENT, VersionUtils.getClientOSVersion());
			params.put(HuoDiConstants.HTTP_PARAM_VERSION, VersionUtils.getCurrentVersion());
			params.put(HuoDiConstants.HTTP_PARAM_VERSION_CODE, VersionUtils.getCurrentVersionCode());
			params.put(HuoDiConstants.HTTP_PARAM_DEVICE_FINGERPRINT, DeviceUtils.getDeviceFingerprint());

			final LoginResponseParser parser = LoginResponseParser.getInstance();
			final JsonResponse<LoginResponse> jsonResponse;
			try {
				String serverHost = HostProvider.getHostDomain(getHostType(), isGetIp);
				jsonResponse = ServiceInvoker.invoke(serverHost, serviceUrl, params, parser, null, true);
				Log.d(TAG, "auto sign-in succeed");

				TaskResult<LoginResponse> loginResponseTaskResult = new TaskResult<LoginResponse>(TaskResult.Status.OK, jsonResponse.getContent());
				return loginResponseTaskResult;
			} catch (WuliuQQException e) {
				return new TaskResult(TaskResult.Status.ERROR, e.getErrorCode());
			}

		} else {
			Log.d(TAG, "no saved credential found");

			final Intent intent = new Intent(activity, HomeActivity.class);
			activity.startActivity(intent);
			activity.finish();
			return null;
		}
	}

	@Override
	protected void onError(ErrorCode errorCode) {
		super.onError(errorCode);
		final Intent intent = new Intent(activity, HomeActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}
}
