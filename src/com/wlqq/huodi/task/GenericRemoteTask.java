package com.wlqq.huodi.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.JsonResponse;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.exception.ServerInternalException;
import com.wlqq.huodi.exception.WuliuQQException;
import com.wlqq.huodi.handler.DelegationHandler;
import com.wlqq.huodi.handler.ExceptionHandler;
import com.wlqq.huodi.http.HttpClientFactory;
import com.wlqq.huodi.http.ServiceInvoker;
import com.wlqq.huodi.json.Parser;
import com.wlqq.huodi.utils.ConnectionUtils;
import com.wlqq.huodi.utils.DeviceUtils;
import com.wlqq.huodi.utils.HostProvider;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LogUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tiger Tang
 *         Date: 12-1-3
 *         Time: 下午3:47
 * @since 0.1.20
 */
public abstract class GenericRemoteTask<T> extends AsyncTask<TaskParams, Integer, TaskResult<T>> {

	private static final String TAG = "RemoteServiceAction";

	// view
	protected Activity activity;
	protected ProgressDialog progressDialog;

	private DelegationHandler exceptionHandler = new DelegationHandler();

	protected int timeoutConnection = HttpClientFactory.timeoutConnection;
	protected int timeoutSocket = HttpClientFactory.timeoutSocket;
	protected boolean isShowCancelButton = true;
	// state
	private TaskParams taskParams;
	protected static boolean isGetIp = false;


	@SuppressWarnings("unchecked")
//	private TaskListener<T> listener = TaskListener.DUMMY;

	private TaskListener<T> listener;

	protected GenericRemoteTask() {
		super();
		listener = TaskListener.DUMMY;
	}

	public TaskListener<T> getListener() {
		return listener;
	}

	public GenericRemoteTask<T> setListener(TaskListener<T> listener) {
		this.listener = listener;

		return this;
	}

	protected HostProvider.HostType getHostType() {
		return HostProvider.HostType.HOST;
	}

	public GenericRemoteTask(Activity activity) {
		super();
		this.activity = activity;
		listener = new TaskListener.TaskListenerAdapter<T>(activity);
	}

	protected abstract String getRemoteServiceAPIUrl();

	protected abstract boolean isSecuredAction();

	protected abstract Parser<T> getResultParser();

	protected boolean isShowProgressDialog() {
		return true;
	}

	protected boolean isEncrypt() {
		return true;
	}

	protected String getProgressDialogTitle() {
		return HuoDiApplication.getContext().getString(R.string.please_wait);
	}

	protected String getProgressDialogMessage() {
		return HuoDiApplication.getContext().getString(R.string.msg_loading);
	}

	@Override
	@SuppressWarnings("unchecked")
	public TaskResult<T> doInBackground(TaskParams... params) {
		try {
			Log.i(TAG, "start to execute a RemoteServiceAction");

			taskParams = params[0];

			TaskResult<T> tTaskResult = callRemoteService(getRemoteServiceAPIUrl(), taskParams.getHttpParams());
			return tTaskResult;
		} catch (IOException e) {
			final String s = e.toString();
			Log.e(TAG, "failed due to: " + s);
			if (s.contains("java.net.UnknownHostException")) {
				isGetIp = true;
				go2LoginActivity();
				return new TaskResult(TaskResult.Status.DNS_ERROR);
			}

			return new TaskResult(TaskResult.Status.IO_ERROR);
		} catch (ServerInternalException e) {
			Log.e(TAG, "execution failed due to: " + e);

			return new TaskResult(TaskResult.Status.INTERNAL_ERROR);
		} catch (JSONException e) {
			Log.e(TAG, "execution failed due to: " + e);

			return new TaskResult(TaskResult.Status.JSON_ERROR);
		} catch (Throwable t) {
			Log.e(TAG, "execution failed due to: " + t);
			return new TaskResult(TaskResult.Status.INTERNAL_ERROR);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (ConnectionUtils.isConnected()) {
			if (isShowProgressDialog()) {
				progressDialog = new ProgressDialog(activity);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setTitle(getProgressDialogTitle());
				progressDialog.setMessage(getProgressDialogMessage());
				if (isShowCancelButton) {
					progressDialog.setButton(HuoDiApplication.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							progressDialog.dismiss();

							GenericRemoteTask.this.cancel(true);
							onCancel();
						}
					});
				}
				try {
					progressDialog.show();
				} catch (IllegalArgumentException e) {
					Log.e(TAG, e.toString());
				}
			}
		} else {
			Toast.makeText(HuoDiApplication.getContext(), R.string.err_no_available_networks, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onPostExecute(TaskResult<T> result) {
		if (isShowProgressDialog() && progressDialog != null && progressDialog.isShowing() && progressDialog.isShowing()) {
			try {
				progressDialog.dismiss();
			} catch (IllegalArgumentException e) {
				Log.e(TAG, e.toString());
			}
		}

		try {
			super.onPostExecute(result);

			final TaskResult.Status status = result.getStatus();
			switch (result.getStatus()) {
				case OK:
					onSucceed(result.getContent());
					break;
				case ERROR:
					onError(result.getErrorCode());
					onError();
					break;
				case IO_ERROR:
				case INTERNAL_ERROR:
				case JSON_ERROR:
				default:
					onError(status);
					onError();
					break;
			}
		} catch (Throwable t) {
			Log.e(TAG, "onPostExecute invocation failed due to: " + t);
			onError(TaskResult.Status.UNKNOWN_ERROR);
		}
	}

	protected void onError(TaskResult.Status status) {
		listener.onError(status);
	}

	protected void onError() {

	}

	protected void onSucceed(T t) {
		listener.onSucceed(t);
	}

	protected void onCancel() {

	}

	public GenericRemoteTask<T> registerExceptionHandler(ErrorCode errorCode, ExceptionHandler handler) {
		this.exceptionHandler.registerErrorHandler(errorCode, handler);
		return this;
	}

	@SuppressWarnings("unchecked")
	protected void onError(ErrorCode errorCode) {

		Log.i(TAG, "execution failed due to: " + errorCode);
		RemoteTaskContext remoteTaskContext = new RemoteTaskContext(activity, this, taskParams);
		exceptionHandler.handleError(errorCode, remoteTaskContext);
	}

	@SuppressWarnings("unchecked")
	protected TaskResult<T> callRemoteService(String serviceUrl, Map<String, Object> params) throws IOException, ServerInternalException, JSONException {
		HttpClientFactory.setTimeoutConnection(timeoutConnection);
		HttpClientFactory.setTimeoutSocket(timeoutConnection);

		if (isSecuredAction()) {
			params.put(HuoDiConstants.HTTP_PARAM_SID, AuthenticationHolder.getSession().getId());
			params.put(HuoDiConstants.HTTP_PARAM_ST, AuthenticationHolder.getSession().getToken());
			params.put(HuoDiConstants.HTTP_PARAM_DEVICE_FINGERPRINT, DeviceUtils.getDeviceFingerprint());
		}

		try {
			String serverHost = HostProvider.getHostDomain(getHostType(), isGetIp);
			final JsonResponse<T> response = ServiceInvoker.invoke(serverHost, serviceUrl, params, getResultParser(), null, isEncrypt());
			return new TaskResult<T>(TaskResult.Status.OK, response.getContent());
		} catch (WuliuQQException e) {
			return new TaskResult(TaskResult.Status.ERROR, e.getErrorCode());
		}
	}

	private void go2LoginActivity() {
		try {

////		todo	final Intent intent = new Intent(activity, L);
//			activity.startActivity(intent);
//			activity.finish();
		} catch (NullPointerException e) {
			LogUtils.e(TAG, e.toString());
		}
	}
}
