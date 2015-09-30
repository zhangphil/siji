package com.wlqq.huodi.locate;

import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wlqq.huodi.app.HuoDiApplication;

/**
 * @author xlw
 *         Date: 13-7-1
 */
public class WRequestLocationTask {

	private static final String TAG = "WRequestLocationTask";

	private static final int LOCATE_MAX_TIME = 3;

	private LocationClient mLocClient;

	private int locateTime;

	private boolean showPrompt;

	public WRequestLocationTask(boolean showPrompt) {

		this.showPrompt = showPrompt;

		if (mLocClient == null) {
			Log.v(TAG, "mLocClient is null");
			mLocClient = new LocationClient(HuoDiApplication.getContext());

			setLocationOption();

		}

		BDLocationListener myListener = new WLocationListener() {
			@Override
			protected void locationSucceed() {
				super.locationSucceed();
				stopLocation();
				succeed();
			}

			@Override
			protected void locationFailed() {
				super.locationFailed();

				locateTime++;

				if (locateTime > LOCATE_MAX_TIME) {
					stopLocation();
				}

				failed();
			}

			@Override
			protected void getAddressFailed() {
				super.getAddressFailed();
				stopLocation();
				failedToAddress();
			}

			@Override
			protected void showPrompt(String prompt) {
				super.showPrompt(prompt);
				showToast(prompt);
			}
		};

		mLocClient.registerLocationListener(myListener);

	}

	private void stopLocation() {
		locateTime = 0;

		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
			Log.v(TAG, "stop  mLocClient");
		}
	}

	public void execute() {

		if (mLocClient.isStarted()) {
			mLocClient.requestLocation();
			Log.v(TAG, "mLocClient is Started , execute");
		} else {
			mLocClient.start();
			Log.v(TAG, "mLocClient is not Started , start");
		}
	}

	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.disableCache(true);//禁止启用缓存定位
		option.setPoiNumber(5);    //最多返回POI个数
		option.setPoiDistance(1000); //poi查询距离
		option.setScanSpan(100000);
		option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
		mLocClient.setLocOption(option);
	}

	public void succeed() {
	}

	public void failed() {
	}

	public void failedToAddress() {
	}

	public void showToast(String prompt) {
		if (showPrompt)
			Toast.makeText(HuoDiApplication.getContext(), prompt, Toast.LENGTH_SHORT).show();
	}
}
