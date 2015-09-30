package com.wlqq.huodi.handler;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.LoginResponse;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.AutoSignInTask;
import com.wlqq.huodi.task.GenericRemoteTask;
import com.wlqq.huodi.task.RemoteTaskContext;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.task.TaskResult;
import com.wlqq.huodi.utils.DeviceUtils;
import com.wlqq.huodi.utils.HuoDiConstants;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public class SessionExpiredHandler implements ExceptionHandler {

	private static final String TAG = "RemoteServiceAction";

	public static SessionExpiredHandler instance = new SessionExpiredHandler();

	public static SessionExpiredHandler getInstance() {
		return instance;
	}

	@Override
	public void handleError(ErrorCode errorCode, final RemoteTaskContext remoteTaskContext) {
		Log.i(TAG, "session expired");
		final Activity activity = remoteTaskContext.getActivity();
		final GenericRemoteTask genericRemoteTask = remoteTaskContext.getGenericRemoteTask();
		try {
			final SavedCredential savedCredential = SavedCredential.getInstance();
			final String principal = savedCredential.getPrincipal();
			final String credential = savedCredential.getCredential();

			if (StringUtils.isNotBlank(principal)) {
				Log.i(TAG, "start automatically re-sign in");

				new AutoSignInTask(activity) {
					@Override
					protected void onSucceed(LoginResponse object) {
						AuthenticationHolder.setLogin(true);
						Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);
						final UserProfile profile = object.getProfile();
						final Session session = object.getSession();
						AuthenticationHolder.setProfile(profile);
						AuthenticationHolder.setSession(session);

						Log.i(TAG, "auto re-sign in successfully, redo the RemoteServiceAction: " + this.getClass());

						// REDO
						final TaskParams taskParams = remoteTaskContext.getTaskParams();

						taskParams.getHttpParams().put(HuoDiConstants.HTTP_PARAM_SID, AuthenticationHolder.getSession().getId());
						taskParams.getHttpParams().put(HuoDiConstants.HTTP_PARAM_ST, AuthenticationHolder.getSession().getToken());
						taskParams.getHttpParams().put(HuoDiConstants.HTTP_PARAM_DEVICE_FINGERPRINT, DeviceUtils.getDeviceFingerprint());
						if (taskParams != null) {

							final TaskResult taskResult = genericRemoteTask.doInBackground(taskParams);
							genericRemoteTask.onPostExecute(taskResult);
						}
					}
				}.execute(new TaskParams(new HashMap<String, Object>()));
			} else {
				Log.i(TAG, "no saved credential found, will go to login activity");

				Toast.makeText(activity, activity.getString(R.string.err_session_expired), Toast.LENGTH_LONG).show();

				go2LoginActivity(activity);
			}
		} catch (Exception e) {
			Log.i(TAG, "exception occurred when automatically re-sign in due to: " + e);

			Log.i(TAG, "will go to login activity");

			Toast.makeText(activity, errorCode.getMessage(), Toast.LENGTH_LONG).show();

			go2LoginActivity(activity);
		}
	}

	private void go2LoginActivity(Activity activity) {
		final Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}
}
