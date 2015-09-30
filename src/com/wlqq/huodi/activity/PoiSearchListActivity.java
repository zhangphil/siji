package com.wlqq.huodi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.umeng.analytics.MobclickAgent;
import com.wlqq.huodi.R;
import com.wlqq.huodi.adapter.PoiSearchListAdapter;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.bean.SearchPoi;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.locate.WRequestLocationTask;
import com.wlqq.huodi.utils.LogUtils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xlw
 *         Date: 12-6-13
 */
public class PoiSearchListActivity extends Activity {
	private LatLng myGeoPoint;
	public static List<PoiInfo> list;
	private List<SearchPoi> poiList = new ArrayList<SearchPoi>();
	private ListView listView;
	private int[] bitmapId = {R.drawable.icon_marka, R.drawable.icon_markb, R.drawable.icon_markc, R.drawable.icon_markd,
			R.drawable.icon_marke, R.drawable.icon_markf, R.drawable.icon_markg, R.drawable.icon_markh, R.drawable.icon_marki, R.drawable.icon_markj};
	private PoiSearch mPoiSearch;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.poi_search_list);
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				LatLng geoPoint = poiList.get(i).getGeoPoint();
				Intent intent = new Intent(PoiSearchListActivity.this, PoiSearchActivity.class);
				intent.putExtra("endlat", geoPoint.latitude);
				intent.putExtra("endlon", geoPoint.longitude);
				intent.putExtra("startlat", myGeoPoint.latitude);
				intent.putExtra("startlon", myGeoPoint.longitude);
				String name = getIntent().getStringExtra("name");
				if (StringUtils.isNotBlank(name)) {
					intent.putExtra("keyword", name);
					intent.putExtra("myLat", myGeoPoint.latitude);
					intent.putExtra("myLng", myGeoPoint.longitude);
				}
				startActivity(intent);
			}
		});
		final ProgressDialog progressDialog = ProgressDialog.show(this, getResources().getString(R.string.please_wait), getResources().getString(R.string.searching_map), true, true);
		final AddressComponent gps_located_address = LocationHolder.getLOCATION();
		if (gps_located_address != null) {
			myGeoPoint = new LatLng(gps_located_address.getILatitude(), gps_located_address.getILongitude());
		} else {
			//定位不成功时
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			Toast.makeText(PoiSearchListActivity.this, "查询失败，正在重新定位，请稍后重试", Toast.LENGTH_SHORT).show();
			new WRequestLocationTask(true).execute();
			finish();
		}
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(new MySearchListener(progressDialog));
		PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
		poiNearbySearchOption.location(myGeoPoint);
		poiNearbySearchOption.keyword(getIntent().getStringExtra("name"));
		poiNearbySearchOption.radius(10000);
		poiNearbySearchOption.pageCapacity(0);
		try {
			mPoiSearch.searchNearby(poiNearbySearchOption);
		} catch (Exception e) {
			LogUtils.e(PoiSearchListActivity.class.getSimpleName(), e.toString());
		}

		View viewById = findViewById(R.id.backButton);
		viewById.setVisibility(View.VISIBLE);
		viewById.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}

	public class MySearchListener implements OnGetPoiSearchResultListener {
		private ProgressDialog progressDialog;

		public MySearchListener(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		public void onGetPoiResult(PoiResult result) {
			if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
				return;
			}
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {

				list = result.getAllPoi();
				PoiSearchListAdapter poiSearchListAdapter = new PoiSearchListAdapter(poiList, PoiSearchListActivity.this);
				poiSearchListAdapter.clearList();
				for (int i = 0; i < result.getAllPoi().size(); i++) {
					SearchPoi searchPoi = new SearchPoi();
					searchPoi.setName(result.getAllPoi().get(i).name);
					searchPoi.setAddress(result.getAllPoi().get(i).address);
					searchPoi.setGeoPoint(result.getAllPoi().get(i).location);
					searchPoi.setBitmap(((BitmapDrawable) getResources().getDrawable(bitmapId[i])).getBitmap());
					poiList.add(searchPoi);
				}
				listView.setAdapter(poiSearchListAdapter);
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				return;
			}
		}

		@Override
		public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

		}
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPoiSearch.destroy();
	}
}


