package com.wlqq.huodi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * @author Cai
 *         Date 12-7-20
 */
@SuppressWarnings("unchecked")
public class AboutActivity extends BaseActivity {


	private Activity activity;
	private static final String TAG = AboutActivity.class.getSimpleName();

	@ViewInject(id = R.id.tvVersion)
	TextView mVersionTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
	}

	@Override
	protected int getTitleResourceId() {
		return R.string.about_us;
	}

	@Override
	protected int getContentViewLayout() {
		return R.layout.more;
	}

	@Override
	protected void setupView() {
		super.setupView();
		switchCityButton.setVisibility(View.INVISIBLE);
		findViewById(R.id.backButton).setVisibility(View.VISIBLE);
		final TextView companyAddressTextView = (TextView) findViewById(R.id.company_address);
		final String companyAddress = MobclickAgent.getConfigParams(this, "companyAddress");
		companyAddressTextView.setText(companyAddress);

		final TextView copyrightTextView = (TextView) findViewById(R.id.copyright2);
		final String copyright = MobclickAgent.getConfigParams(this, "copyright");
		copyrightTextView.setText(copyright);

		try {
			final PackageInfo packageInfo = this.getPackageManager().getPackageInfo("com.wlqq.huodi", 0);
			final String versionName = packageInfo.versionName;
			mVersionTextView.setText("当前版本:V" + versionName);
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	protected void registerListeners() {
		super.registerListeners();

		findViewById(R.id.company_phone).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "08518666156"));
				startActivity(intent);
			}
		});
		findViewById(R.id.tvUpgrade).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				UmengUpdateAgent.setUpdateCheckConfig(false);
// 如果想程序启动时自动检查是否需要更新， 把下面两行代码加在Activity 的onCreate()函数里。
				UmengUpdateAgent.setUpdateOnlyWifi(false); // 目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在其他网络环境下进行更新自动提醒，则请添加该行代码
				UmengUpdateAgent.setUpdateAutoPopup(false);
				UmengUpdateAgent.setUpdateListener(updateListener);
				UmengUpdateAgent.update(activity);

			}
		});

		findViewById(R.id.company_email).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"service@56qq.cn"});
				intent.putExtra(Intent.EXTRA_TEXT, "");
				intent.setType("plain/text");
				startActivity(Intent.createChooser(intent, "联系我们"));
			}
		});


	}


	UmengUpdateListener updateListener = new UmengUpdateListener() {
		@Override
		public void onUpdateReturned(int updateStatus,
									 UpdateResponse updateInfo) {
			switch (updateStatus) {
				case 0: // has update
					UmengUpdateAgent.showUpdateDialog(activity, updateInfo);
					break;
				case 1: // has no update
					Toast.makeText(HuoDiApplication.getContext(), "没有更新", Toast.LENGTH_SHORT)
							.show();
					break;
				case 2: // none wifi
					Toast.makeText(HuoDiApplication.getContext(), "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
							.show();
					break;
				case 3: // time out
					Toast.makeText(HuoDiApplication.getContext(), "超时", Toast.LENGTH_SHORT)
							.show();
					break;
			}

		}
	};

}
