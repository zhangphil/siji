package com.wlqq.huodi.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.UserInfo;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.listener.NetstatsReceiver;
import com.wlqq.huodi.utils.HuoDiConstants;

import net.tsz.afinal.FinalActivity;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Base Activity class from which should be inherent MANDATORY
 *
 * @author Tiger Tang
 *         Date: 12-1-3
 *         Time: 上午11:07
 * @since 0.1.20
 */
public abstract class BaseActivity extends FinalActivity {

	// constants
	private static final int OPTIONS_MENU_ID_FEEDBACK = 0;
	private static final int OPTIONS_MENU_ID_LOGOUT = 1;
	private static final int OPTIONS_MENU_ID_CHECK_UPDATE = 2;
	private static final int OPTIONS_MENU_ID_EXIT = 3;
	private NetstatsReceiver receiver;
	// views
	protected ImageButton backImgButton;
	protected TextView titleLabel;
	protected TextView switchCityButton;

	public BaseActivity() {
		super();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		receiver = new NetstatsReceiver(BaseActivity.this);
		MobclickAgent.setCatchUncaughtExceptions(true);
		for (int featureId : getWindowFeatures()) {
			requestWindowFeature(featureId);
		}
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//        params.topMargin=100;
		setContentView(LayoutInflater.from(this).inflate(getContentViewLayout(), null), params);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		backImgButton = (ImageButton) findViewById(R.id.backButton);
		backImgButton.setVisibility(View.INVISIBLE);
		titleLabel = (TextView) findViewById(R.id.title);
		switchCityButton = (TextView) findViewById(R.id.switch_city_button);
		backImgButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		if (getTitleResourceId() > 0) {
			titleLabel.setText(getTitleResourceId());
		}

		if (backImgButton != null) {
			backImgButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					onBack();
				}
			});
		}

		setupView();
		registerListeners();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		AuthenticationHolder.setSession((Session) savedInstanceState.getSerializable("session"));
		AuthenticationHolder.setProfile((UserProfile) savedInstanceState.getSerializable("profile"));
		LocationHolder.setLOCATION((AddressComponent) savedInstanceState.getSerializable("gps_located_address"));
		LocationHolder.setLOCATED((Boolean) savedInstanceState.get("gps_located"));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("session", AuthenticationHolder.getSession());
		outState.putSerializable("profile", AuthenticationHolder.getProfile());
		outState.putParcelable("gps_located_address", LocationHolder.getLOCATION());
		outState.putBoolean("gps_located", LocationHolder.isLOCATED());
	}

	protected abstract int getTitleResourceId();

	protected int[] getWindowFeatures() {
		return new int[]{Window.FEATURE_NO_TITLE};
	}

	abstract protected int getContentViewLayout();

	protected void setupView() {
	}

	protected void registerListeners() {
	}

	protected void onBack() {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);

		this.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);

		this.unregisterReceiver(receiver);
	}

}
