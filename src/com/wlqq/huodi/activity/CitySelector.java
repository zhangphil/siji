package com.wlqq.huodi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.data.RegionManager;
import com.wlqq.huodi.utils.AdapterDataUtils;
import com.wlqq.huodi.utils.HuoDiConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitySelector extends BaseActivity {

	public static String cityName;
	private ListView cityListView;
	private List<Map<String, Object>> adapterData;
	private long pid;

	@Override
	protected int getTitleResourceId() {
		return R.string.title_city_selector;
	}

	@Override
	protected int getContentViewLayout() {
		return R.layout.nvp_list;
	}

	@Override
	protected void onResume() {
		super.onResume();

		pid = getIntent().getLongExtra(getIntent().getStringExtra(HuoDiConstants.INTENT_KEY_PID), 11L);

		final List<Region> cities = new ArrayList<Region>();

		if (HuoDiConstants.REGION_SELECTOR_PROVINCE_OR_CITY.equals(getIntent().getAction()) && pid > 13) {
			cities.add(new Region(pid, RegionManager.getRegionName(pid) + "(所有地区)"));
		}

		List<Region> citiesByProvinceId = RegionManager.getCitiesByProvinceId(pid);
		if (citiesByProvinceId != null) {
			cities.addAll(citiesByProvinceId);
		}
		adapterData = AdapterDataUtils.getAdapterDataForRegionList(cities);

		SimpleAdapter adapter = new SimpleAdapter(this, adapterData, R.layout.nvp_list_item, new String[]{"id", "name"}, new int[]{R.id.nvp_value, R.id.nvp_name});
		cityListView.setAdapter(adapter);
	}

	protected void setupView() {
		switchCityButton.setVisibility(View.INVISIBLE);

		cityListView = (ListView) findViewById(R.id.nvp_list);
		cityListView.setCacheColorHint(0);

		View dividerView = getLayoutInflater().inflate(R.layout.divider, null);
		ImageView dividerImg = (ImageView) dividerView.findViewById(R.id.divider_img);

		cityListView.addFooterView(dividerImg, null, false);
		cityListView.setCacheColorHint(0);
	}

	protected void registerListeners() {
		switchCityButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});

		cityListView.setOnItemClickListener(new OnItemClickListener() {

												public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
													Long cid;
													if (HuoDiConstants.REGION_SELECTOR_PROVINCE_OR_CITY.equals(getIntent().getAction()) && position == 0) {
														cid = (long) -1;
													} else {
														cid = (Long) adapterData.get(position).get("id");
													}
													cityName = RegionManager.getRegion((Long) adapterData.get(position).get("id")).getName();

													if (RegionManager.getDistrictByCity(cid) != null &&
															getIntent().getBooleanExtra(HuoDiConstants.FROM_SEARCH_TAB_ACTIVITY, false)) {
														final Intent intent = new Intent(CitySelector.this, DistrictActivity.class);
														intent.putExtra(HuoDiConstants.INTENT_KEY_CID, (Long) adapterData.get(position).get("id"));

														startActivityForResult(intent, HuoDiConstants.REQUEST_CODE_REGION_FILTER);
													} else {
														Bundle bundle = getIntent().getExtras();

														Intent intent = new Intent();
														intent.putExtras(bundle);
														intent.putExtra(bundle.getString(HuoDiConstants.INTENT_KEY_PID), pid);
														intent.putExtra(bundle.getString(HuoDiConstants.INTENT_KEY_CID), cid);

														setResult(Activity.RESULT_OK, intent);
														finish();
													}
												}
											}
		);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(Activity.RESULT_CANCELED);
			finish();
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
			case Activity.RESULT_OK:
				setResult(Activity.RESULT_OK, data);
				finish();
		}
	}
}
