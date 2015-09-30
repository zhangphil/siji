package com.wlqq.huodi.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.HomeActivity;
import com.wlqq.huodi.bean.JsonResponse;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.Constants;
import com.wlqq.huodi.exception.ServerInternalException;
import com.wlqq.huodi.exception.WuliuQQException;
import com.wlqq.huodi.http.CustomMultiPartEntity;
import com.wlqq.huodi.http.ServiceInvoker;
import com.wlqq.huodi.json.NULLParser;
import com.wlqq.huodi.task.TaskResult;
import com.wlqq.huodi.utils.DeviceUtils;
import com.wlqq.huodi.utils.HostProvider;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LogUtils;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cai
 *         Date 12-6-12
 */
public class UploadAllPictureTask extends AsyncTask<Map<String, Object>, Integer, TaskResult> {

	private static final String TAG = UploadAllPictureTask.class.getSimpleName();
	private List<Map<String, Object>> pictureParams = new ArrayList<Map<String, Object>>();
	private Activity activity;
	private ProgressDialog dialog;
	public static boolean isJump;
	private int uploaded;
	private int paramsLength;

	static {
		isJump = true;
	}

	protected boolean isEncrypt() {
		return false;
	}

	protected String getRemoteServiceAPIUrl() {
		return HuoDiConstants.UPLOAD_PICTURE;
	}

	public UploadAllPictureTask(Activity activity, ProgressDialog dialog) {
		this.activity = activity;
		this.dialog = dialog;
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.show();
	}


	protected void onSucceed(TaskResult voidTaskResult) {

		dialog.dismiss();
		final int transferred = uploaded;
		if (transferred == paramsLength) {
			Toast.makeText(activity, activity.getString(R.string.allPictureUploadSuccess), Toast.LENGTH_SHORT).show();
//			try {
//				File directory = new File(Constants.CAMERA_DIR);
//				if (directory.exists()) {
//					FileUtils.deleteDirectory(directory);
//					directory.deleteOnExit();
//				}
//			} catch (Exception e) {
//				LogUtils.e(TAG, e.toString());
//			}

		} else {
			Toast.makeText(activity, transferred + "张图片上传成功，有" + (paramsLength - transferred) + "张图片上传失败", Toast.LENGTH_SHORT).show();
			Map<String, Object>[] map = new HashMap[0];
			Map<String, Object>[] maps = pictureParams.toArray(map);
			for (Map map1 : maps) {
				doInBackground(map1);
			}
		}
//		activity.startActivity(new Intent(activity, HomeActivity.class));
//		activity.finish();
		isJump = true;
	}


	@Override
	public com.wlqq.huodi.task.TaskResult<Void> doInBackground(Map<String, Object>... params) {
		if (params != null) {
			paramsLength = params.length;
			final String remoteUrl = getRemoteServiceAPIUrl();
			JsonResponse<Void> result = null;
			for (int i = 0; i < paramsLength; i++) {

				Map<String, Object> param = params[i];
				param.put(HuoDiConstants.HTTP_PARAM_SID, AuthenticationHolder.getSession().getId());
				param.put(HuoDiConstants.HTTP_PARAM_ST, AuthenticationHolder.getSession().getToken());
				param.put(HuoDiConstants.HTTP_PARAM_DEVICE_FINGERPRINT, DeviceUtils.getDeviceFingerprint());
				LogUtils.d("UploadPictureTask", String.format("%s", param));
				pictureParams.add(param);
				final int finalI = i;
				String serverHost = HostProvider.getHostDomain(HostProvider.HostType.HOST, false);

				try {
					result = ServiceInvoker.invoke(serverHost, remoteUrl, param, NULLParser.getInstance(), new CustomMultiPartEntity.ProgressListener() {
						@Override
						public void transferred(long transferred, long total) {
							LogUtils.d("UploadPictureTask", String.format("%s/%s", transferred, total));
							final Message message = new Message();
							message.arg1 = paramsLength;
							message.arg2 = finalI + 1;
							mHandler.sendMessage(message);
							dialog.setMax((int) total);
							publishProgress((int) transferred);
						}
					}, isEncrypt());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ServerInternalException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (WuliuQQException e) {
					e.printStackTrace();
				}
				if (result != null) {
					if (result.getStatus().equals(TaskResult.Status.OK.name())) {
						uploaded++;
					}
				}
			}
			if (result != null) {
				return new TaskResult(TaskResult.Status.OK, result.getContent());
			}
		}
		return new TaskResult(TaskResult.Status.UNKNOWN_ERROR, "FAILURE");
	}

	@Override
	protected void onPostExecute(TaskResult taskResult) {
		super.onPostExecute(taskResult);

		onSucceed(taskResult);
	}

	private boolean isSpeed() {
		return false;
	}

	private void updateProgressBar(int total, int current) {
		dialog.setMessage("共" + total + "张照片，正在上传第" + current + "张！");
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			updateProgressBar(msg.arg1, msg.arg2);
		}
	};

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		dialog.setProgress(values[0]);
	}

	protected boolean isSecuredAction() {
		return true;
	}
}
