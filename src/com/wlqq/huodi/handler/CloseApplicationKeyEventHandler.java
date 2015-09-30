package com.wlqq.huodi.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.receiver.SendLocationReceiver;
import com.wlqq.huodi.utils.AlarmManagerUtil;


/**
 * @author Tiger Tang
 *         Date: 12-1-6
 *         Time: 上午10:39
 * @since 0.1.20
 */
public class CloseApplicationKeyEventHandler {

	private Activity activity;

	public CloseApplicationKeyEventHandler(Activity activity) {
		this.activity = activity;
	}

	public boolean onKeyDown(final KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK && (keyEvent.getRepeatCount() == 0) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
			onClose();
			return true;
		}
		return false;
	}

	public void onClose() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getString(R.string.msg_exit_confirmation));
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
//						SendLocationReceiver.cancelSendLocationBroadcast(HuoDiApplication.getContext());
//                                Intent back2HomeScreen = new Intent(Intent.ACTION_MAIN);
//                                back2HomeScreen.addCategory(Intent.CATEGORY_HOME);
//                                activity.goToHomeActivity(back2HomeScreen);
//                                activity.finish();
//                                android.os.Process.killProcess(android.os.Process.myPid());

                        Intent back2HomeScreen = new Intent(Intent.ACTION_MAIN);
                        back2HomeScreen.addCategory(Intent.CATEGORY_HOME);
                        activity.startActivity(back2HomeScreen);
                        activity.finish();
                        android.os.Process.killProcess(android.os.Process.myPid());

//						Intent startMain = new Intent(Intent.ACTION_MAIN);
//						startMain.addCategory(Intent.CATEGORY_HOME);
//						startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						activity.startActivity(startMain);
//						System.exit(0);
					}


				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
}
