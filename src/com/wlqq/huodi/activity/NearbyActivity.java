package com.wlqq.huodi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wlqq.huodi.R;
import com.wlqq.huodi.listener.BankMsgOnClickListener;
import com.wlqq.huodi.listener.GasMsgOnClickListener;
import com.wlqq.huodi.listener.StayMsgOnClickListener;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by caitiancai on 14/11/20.
 */
public class NearbyActivity extends BaseActivity {

	private Activity activity;

	@ViewInject(id = R.id.btnBank)
	Button mBankButton;

	@ViewInject(id = R.id.btnOil)
	Button mOilButton;

	@ViewInject(id = R.id.btnParking)
	Button mParkingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
	}

	@Override
	protected void setupView() {
		super.setupView();
		backImgButton.setVisibility(View.VISIBLE);
	}

	@Override
	protected void registerListeners() {
		super.registerListeners();
		mBankButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new BankMsgOnClickListener(activity).onClick();
			}
		});


		mOilButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new GasMsgOnClickListener(activity).onClick();
			}
		});
	}

	@Override
	protected int getTitleResourceId() {
		return R.string.nearby;
	}

	@Override
	protected int getContentViewLayout() {
		return R.layout.nearby;
	}
}
