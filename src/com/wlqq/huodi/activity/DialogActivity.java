package com.wlqq.huodi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.LoginResponse;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.handler.LoginFromOtherDeviceHandler;
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
 * Created by caitiancai on 14-8-14.
 */
public class DialogActivity extends Activity {
	private static final String TAG = DialogActivity.class.getSimpleName();
	private Activity activity;
	public static ErrorCode errorCode;
	public static RemoteTaskContext remoteTaskContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.login_from_other_device_dialog);

		Button reLoginButton = (Button) findViewById(R.id.btnReLogin);
		reLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					final SavedCredential savedCredential = SavedCredential.getInstance();
					final String principal = savedCredential.getPrincipal();
					final String credential = savedCredential.getCredential();

					if (StringUtils.isNotBlank(principal)) {

						new AutoSignInTask(activity) {

							@Override
							protected boolean isShowProgressDialog() {
								return true;
							}

							@Override
							protected String getProgressDialogMessage() {
								return activity.getString(R.string.msg_logining);
							}

							@Override
							protected void onSucceed(LoginResponse object) {
								AuthenticationHolder.setLogin(true);
								Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);
								final UserProfile profile = object.getProfile();
								final Session session = object.getSession();
								AuthenticationHolder.setProfile(profile);
								AuthenticationHolder.setSession(session);
								final GenericRemoteTask genericRemoteTask = remoteTaskContext.getGenericRemoteTask();

								final TaskParams taskParams = remoteTaskContext.getTaskParams();
								taskParams.getHttpParams().put(HuoDiConstants.HTTP_PARAM_SID, AuthenticationHolder.getSession().getId());
								taskParams.getHttpParams().put(HuoDiConstants.HTTP_PARAM_ST, AuthenticationHolder.getSession().getToken());
								taskParams.getHttpParams().put(HuoDiConstants.HTTP_PARAM_DEVICE_FINGERPRINT, DeviceUtils.getDeviceFingerprint());

								final TaskResult taskResult = genericRemoteTask.doInBackground(taskParams);
								genericRemoteTask.onPostExecute(taskResult);
								finish();
							}
						}.execute(new TaskParams(new HashMap<String, Object>()));
					} else {

						Toast.makeText(activity, activity.getString(R.string.err_session_expired), Toast.LENGTH_LONG).show();

						go2LoginActivity(activity);
					}
				} catch (Exception e) {

					Toast.makeText(activity, errorCode.getMessage(), Toast.LENGTH_LONG).show();

					go2LoginActivity(activity);
				}

			}
		});

		Button cancelButton = (Button) findViewById(R.id.btnCancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activity.startActivity(new Intent(activity, LoginActivity.class));
				try {
					activity.finish();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
				finish();
			}
		});
	}


	private void go2LoginActivity(Activity activity) {
		final Intent intent = new Intent(activity, LoginActivity.class);
		startActivity(intent);
		finish();

	}

	@Override
	public void onBackPressed() {
		go2LoginActivity(activity);
	}

	@Override
	protected void onDestroy() {
		LoginFromOtherDeviceHandler.isShow = false;
		super.onDestroy();
	}
}
