package com.wlqq.huodi.task;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.exception.ErrorCode;

/**
 * @author Tiger Tang
 *         Date: 12-1-19
 *         Time: 下午4:21
 * @since 0.1.20
 */
public interface TaskListener<T> {

	TaskListener DUMMY = new TaskListener() {
		public void onPreExecute(GenericRemoteTask genericRemoteTask) {

		}

		public void onSucceed(Object object) {

		}

		public void onProgressUpdate(GenericRemoteTask genericRemoteTask, Object param) {

		}

		public void onCancelled(GenericRemoteTask genericRemoteTask) {

		}

		public void onError(TaskResult.Status status) {
		}

		@Override
		public void onError(ErrorCode errorCode) {

		}
	};

	void onPreExecute(GenericRemoteTask<T> task);

	void onSucceed(T object);

	void onProgressUpdate(GenericRemoteTask<T> task, Object param);

	void onCancelled(GenericRemoteTask<T> task);

	void onError(TaskResult.Status status);

	void onError(ErrorCode errorCode);

	public static class TaskListenerAdapter<T> implements TaskListener<T> {

		protected Activity activity;


		public Activity getActivity() {
			return activity;
		}

		public void setActivity(Activity activity) {
			this.activity = activity;
		}

		public TaskListenerAdapter(Activity activity) {
			this.activity = activity;
		}

		public void onPreExecute(GenericRemoteTask<T> task) {

		}

		public void onSucceed(T object) {

		}

		public void onProgressUpdate(GenericRemoteTask<T> task, Object param) {

		}

		public void onCancelled(GenericRemoteTask<T> task) {

		}

		public void onError(TaskResult.Status status) {
			final Context context = HuoDiApplication.getContext();
			if (context == null) {
				return;
			}
			final Resources resources = context.getResources();
			switch (status) {
				case IO_ERROR:
					Toast.makeText(context, resources.getString(R.string.err_cannot_connect_server), Toast.LENGTH_LONG).show();
					break;
				case JSON_ERROR:
					Toast.makeText(context, resources.getString(R.string.err_internal_error), Toast.LENGTH_LONG).show();
					break;
				case INTERNAL_ERROR:
					Toast.makeText(context, resources.getString(R.string.err_server_internal_error), Toast.LENGTH_LONG).show();
					break;
				case DNS_ERROR:
					Toast.makeText(context, resources.getString(R.string.err_cannot_connect_server_dns_error), Toast.LENGTH_LONG).show();
					break;
				case UNKNOWN_ERROR:
					Toast.makeText(context, resources.getString(R.string.err_unknown_error), Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onError(ErrorCode errorCode) {

		}
	}
}
