package com.wlqq.huodi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.wlqq.huodi.R;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.data.Constants;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.task.SubmitBookingInfoTask;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.utils.ConnectionUtils;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LogUtils;
import com.wlqq.huodi.view.DateTimeButton;
import com.wlqq.huodi.view.RegionSelector;

import net.tsz.afinal.annotation.view.ViewInject;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;

public class BookingActivity extends BaseActivity {

	// views
	private RegionSelector departurePlace;
	private RegionSelector destination;

	private String plateNumber;
	private Activity activity;
	private GeoCoder mSearch;
	private boolean isStartPlace = false;

	private String fromLng = "";             // 起始地经度
	private String fromLat = "";             // 起始地纬度
	private String fromAddress = "";         // 起始地址
	private String toLng = "";               // 到达地经度
	private String toLat = "";               // 到达地纬度
	private String toAddress = "";           // 到达地址

	@ViewInject(id = R.id.btnDateTime)
	DateTimeButton mDateTimeButton;

	@ViewInject(id = R.id.submit_button)
	Button mSubmitButton;

	@ViewInject(id = R.id.timeSpinner)
	TextView timeTextView;

	@ViewInject(id = R.id.currentTime)
	TextView currentTimeTextView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = this;
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(listener);

		Region user_selected_region = LocationHolder.getUSER_SELECTED_REGION();
		if (user_selected_region != null) {

			final long id = user_selected_region.getId();

			departurePlace.setPid(id);
			departurePlace.setCid(id);
			isStartPlace = true;
			mSearch.geocode(new GeoCodeOption()
					.city(departurePlace.getCityButtonTextString())
					.address(departurePlace.getCityButtonTextString()));
		}

		AddressComponent location = LocationHolder.getLOCATION();

		if (location != null) {
			fromLat = String.valueOf(location.getLatitude());
			fromLng = String.valueOf(location.getLongitude());
			fromAddress = location.getFormattedAddress();
		}

		currentTimeTextView.setText("当前时间是: " + HuoDiConstants.DF_YYYY_MM_dd_HH_mm.format(new Date()));
	}

	@Override
	protected int getTitleResourceId() {
		return R.string.booking;
	}

	@Override
	protected int getContentViewLayout() {
		return R.layout.booking;
	}


	protected void setupView() {

		findViewById(R.id.backButton).setVisibility(View.VISIBLE);
		departurePlace = (RegionSelector) findViewById(R.id.departure_place);

		destination = (RegionSelector) findViewById(R.id.destination);

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	protected void registerListeners() {

		timeTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String[] items = {"2", "3", "4", "5"};
				new AlertDialog.Builder(BookingActivity.this)
						.setTitle("预约时间")
						.setItems(items, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								timeTextView.setText(items[i]);
							}
						})
						.show();
			}
		});

		mSubmitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!ConnectionUtils.isConnected()) {
					Toast.makeText(activity, R.string.err_no_available_networks, Toast.LENGTH_LONG).show();
				} else {
					if (checkValue()) {
						HashMap<String, Object> httpParams = new HashMap<String, Object>();
						httpParams.put("reservationtime", timeTextView.getText().toString());
						httpParams.put("fromLng", fromLng);
						httpParams.put("fromAddress", fromAddress);
						httpParams.put("fromLat", fromLat);
						httpParams.put("toLng", toLng);
						httpParams.put("toAddress", toAddress);
						httpParams.put("toLat", toLat);

						new SubmitBookingInfoTask(activity) {
							@Override
							protected void onSucceed(Void aVoid) {
								super.onSucceed(aVoid);

								Toast.makeText(activity, "预约成功", Toast.LENGTH_LONG).show();
								Intent intent = new Intent(activity, HomeActivity.class);
								startActivity(intent);
								activity.finish();
							}
						}.execute(new TaskParams(httpParams));
					}
				}
			}
		});
		destination.setOnRegionChangeListener(new RegionSelector.OnRegionChangeListener() {
			@Override
			public void regionChanged() {
				if (destination.isSearch) {
					String cityButtonTextString = destination.getCityButtonTextString();
					String countyButtonTextString = destination.getCountyButtonTextString();
					if (destination.getCityButtonTextString().equals("选择市")) {
						cityButtonTextString = destination.getProvinceButtonTextString();
					}
					if (countyButtonTextString.equals("区/县")) {
						countyButtonTextString = cityButtonTextString;
					}
					isStartPlace = false;
					mSearch.geocode(new GeoCodeOption()
							.city(cityButtonTextString)
							.address(countyButtonTextString));

					toAddress = destination.getProvinceButtonTextString() + destination.getCityButtonTextString() + destination.getCountyButtonTextString();
				}

			}
		});

		departurePlace.setOnRegionChangeListener(new RegionSelector.OnRegionChangeListener() {
			@Override
			public void regionChanged() {
				if (departurePlace.isSearch) {
					String cityButtonTextString = departurePlace.getCityButtonTextString();
					String countyButtonTextString = departurePlace.getCountyButtonTextString();
					if (departurePlace.getCityButtonTextString().equals("选择市")) {
						cityButtonTextString = departurePlace.getProvinceButtonTextString();
					}
					if (countyButtonTextString.equals("区/县")) {
						countyButtonTextString = cityButtonTextString;
					}
					isStartPlace = true;
					mSearch.geocode(new GeoCodeOption()
							.city(cityButtonTextString)
							.address(countyButtonTextString));
					fromAddress = departurePlace.getProvinceButtonTextString() + departurePlace.getCityButtonTextString() + departurePlace.getCountyButtonTextString();

				}
			}
		});

	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			onBack();
			return true;
		} else return false;
	}

	OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
		public void onGetGeoCodeResult(GeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(activity, "未找到您选择的区县的经纬度", Toast.LENGTH_LONG).show();
				if (isStartPlace) {
					fromLat = "";
					fromLng = "";
					fromAddress = "";
				} else {
					toLng = "";
					toLng = "";
					toAddress = "";
				}
			}
			LatLng location = result.getLocation();
			if (location != null) {
				if (isStartPlace) {
					String cityButtonTextString = departurePlace.getCityButtonTextString();
					if (departurePlace.getCityButtonTextString().equals("选择市")) {
						cityButtonTextString = "";
					}
					if (!result.getAddress().equals(departurePlace.getProvinceButtonTextString())) {
						fromAddress = departurePlace.getProvinceButtonTextString() + cityButtonTextString + result.getAddress();
					} else {
						fromAddress = departurePlace.getProvinceButtonTextString() + cityButtonTextString;
					}
					fromLat = String.valueOf(location.latitude);
					fromLng = String.valueOf(location.longitude);
				} else {
					String cityButtonTextString = destination.getCityButtonTextString();
					if (destination.getCityButtonTextString().equals("选择市")) {
						cityButtonTextString = "";
					}
					if (!result.getAddress().equals(destination.getProvinceButtonTextString())) {
						toAddress = destination.getProvinceButtonTextString() + cityButtonTextString + result.getAddress();
					} else {
						toAddress = destination.getProvinceButtonTextString() + cityButtonTextString;
					}
					toLat = String.valueOf(location.latitude);
					toLng = String.valueOf(location.longitude);
				}
			}
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			}
		}
	};

	private boolean checkValue() {

		fromAddress = departurePlace.getProvinceButtonTextString() + departurePlace.getCityButtonTextString() + departurePlace.getCountyButtonTextString();

		if (StringUtils.isBlank(fromAddress)) {
			Toast.makeText(activity, "请选择起运地", Toast.LENGTH_LONG).show();
			return false;
		}

		if (StringUtils.isBlank(fromLat)) {
			Toast.makeText(activity, "暂时还未获取到经纬度，请稍后再试", Toast.LENGTH_LONG).show();
			return false;
		}

		if (StringUtils.isBlank(toAddress)) {
			Toast.makeText(activity, "请选择到达地", Toast.LENGTH_LONG).show();
			return false;
		}

		if (StringUtils.isBlank(toLat)) {
			Toast.makeText(activity, "暂时还未获取到经纬度，请稍后再试", Toast.LENGTH_LONG).show();
			return false;
		}

		if (StringUtils.isBlank(timeTextView.getText().toString())) {
			Toast.makeText(activity, "请选择预约时间", Toast.LENGTH_LONG).show();
			return false;
		}

		return true;
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		mSearch.destroy();
	}
}
