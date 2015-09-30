package com.wlqq.huodi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.wlqq.huodi.R;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.data.RegionManager;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LocationUtils;
import com.wlqq.huodi.utils.LogUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author xlw
 *         Date: 12-5-18
 */
public class MapRouteActivity extends Activity {

	private TextView twoCitiesDistanceTextView;
	private TextView startCityTextView;
	private TextView endCityTextView;

	private MapView mapView;
	private BaiduMap baiduMap;

	private Region startRegion;
	private Region destinationRegion;

	private int driving_strategy;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.map_route);

		try {

			startCityTextView = (TextView) findViewById(R.id.start_city);
			endCityTextView = (TextView) findViewById(R.id.end_city);
			findViewById(R.id.switch_city_button).setVisibility(View.GONE);
			Button searchButton = (Button) findViewById(R.id.search_button);
			twoCitiesDistanceTextView = ((TextView) findViewById(R.id.twoCitiesDistance));
			twoCitiesDistanceTextView.setVisibility(View.GONE);


			mapView = (MapView) findViewById(R.id.map);
			baiduMap = mapView.getMap();

			LatLng myGeoPoint = null;
			final AddressComponent gps_located_address = LocationHolder.getLOCATION();
			if (gps_located_address != null) {
				myGeoPoint = new LatLng(gps_located_address.getILatitude(), gps_located_address.getILongitude());
			} else {
				//定位不成功时，定位默认为用户注册的城市名
				final UserProfile profile = AuthenticationHolder.getProfile();
				if (profile != null) {
					final String cityName = RegionManager.getRegionName(profile.getCityId());
					if (StringUtils.isNotBlank(cityName)) {
						int[] location = LocationUtils.getLocationByCityName(cityName);
						myGeoPoint = new LatLng(location[1], location[0]);
					}
				}
			}
			if (myGeoPoint == null) {
				myGeoPoint = new LatLng(0, 0);
			}

			//跳转到某坐标位置
			MapStatus.Builder mapBuilder = new MapStatus.Builder();
			mapBuilder.target(myGeoPoint);//地图中心点；
			mapBuilder.zoom(10);//缩放级别；
			MapStatus mapStatus = mapBuilder.build();
			MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
			baiduMap.setMapStatus(mapStatusUpdate);

			registerListener();

			String action = getIntent().getAction();
			if (action != null && action.equals(HuoDiConstants.ACTION_FROM_FREIGHT_DETAILS_ACTIVITY)) {
				final Intent intent = getIntent();
				final long departurePlaceId = Long.parseLong(intent.getStringExtra("departurePlaceId"));
				startRegion = RegionManager.getRegion(departurePlaceId);
				startCityTextView.setText(RegionManager.getFullPlaceName(startRegion.getId()));
				final String[] destinationPlaceId1 = {intent.getStringExtra("destinationPlaceId")};
				findViewById(R.id.llSearch).setVisibility(View.GONE);
				if (destinationPlaceId1[0].contains(",")) {
					final String[] desId = destinationPlaceId1[0].split(",");
					String[] desName = new String[desId.length];
					for (int i = 0; i < desId.length; i++) {
						desName[i] = RegionManager.getFullPlaceName(Long.parseLong(desId[i]));
					}
					AlertDialog.Builder drivingBuilder = new AlertDialog.Builder(this);
					drivingBuilder.setTitle(getResources().getString(R.string.please_choice_des));
					drivingBuilder.setItems(desName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							destinationPlaceId1[0] = desId[i];
							final long destinationPlaceId = Long.parseLong(destinationPlaceId1[0]);
							destinationRegion = RegionManager.getRegion(destinationPlaceId);
							endCityTextView.setText(RegionManager.getFullPlaceName(destinationRegion.getId()));
							showChooseDriveStrategyDialog();
						}
					});
					drivingBuilder.show();
				} else {
					final long destinationPlaceId = Long.parseLong(destinationPlaceId1[0]);
					destinationRegion = RegionManager.getRegion(destinationPlaceId);
					endCityTextView.setText(RegionManager.getFullPlaceName(destinationRegion.getId()));
					showChooseDriveStrategyDialog();
				}
			}

			findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					finish();
				}
			});

			searchButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					twoCitiesDistanceTextView.setVisibility(View.GONE);
					final String strStart = startCityTextView.getText().toString();
					final String strEnd = endCityTextView.getText().toString();
					if (StringUtils.isEmpty(strStart) && StringUtils.isEmpty(strEnd)) {
						Toast.makeText(MapRouteActivity.this, getString(R.string.please_choice_city), Toast.LENGTH_LONG).show();
					} else if (StringUtils.isEmpty(strStart) || StringUtils.isEmpty(strEnd)) {
						Toast.makeText(MapRouteActivity.this, getResources().getString(R.string.choice_city_all), Toast.LENGTH_SHORT).show();
					} else if (strStart.equals(strEnd)) {
						Toast.makeText(MapRouteActivity.this, getResources().getString(R.string.choice_city_repeat), Toast.LENGTH_SHORT).show();
					} else {
						showChooseDriveStrategyDialog();
					}
				}
			});

		} catch (Exception e) {
			LogUtils.e(MapRouteActivity.class.getSimpleName(), e.toString());
		}
	}

	private void showChooseDriveStrategyDialog() {
		AlertDialog.Builder drivingBuilder = new AlertDialog.Builder(this);
		drivingBuilder.setTitle(getResources().getString(R.string.please_choice));
		drivingBuilder.setItems(R.array.driving, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				if (i == 0) {
					driving_strategy = DrivingRoutePlanOption.DrivingPolicy.ECAR_FEE_FIRST.ordinal();
				} else if (i == 1) {
					driving_strategy = DrivingRoutePlanOption.DrivingPolicy.ECAR_TIME_FIRST.ordinal();
				} else if (i == 2) {
					driving_strategy = DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST.ordinal();
				}
				baiduMap.clear();
				search();
			}
		});
		drivingBuilder.show();
	}

	private void registerListener() {

		startCityTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MapRouteActivity.this, ProvinceSelector.class);

				intent.putExtra(HuoDiConstants.INTENT_KEY_PID, HuoDiConstants.HTTP_PARAM_PID);
				intent.putExtra(HuoDiConstants.INTENT_KEY_CID, HuoDiConstants.HTTP_PARAM_CID);

				intent.putExtra(HuoDiConstants.FROM_SEARCH_TAB_ACTIVITY, true);

				startActivityForResult(intent, HuoDiConstants.REQUEST_CODE_START_REGION_FILTER);

			}
		});

		endCityTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MapRouteActivity.this, ProvinceSelector.class);
				intent.putExtra(HuoDiConstants.INTENT_KEY_PID, HuoDiConstants.HTTP_PARAM_PID);
				intent.putExtra(HuoDiConstants.INTENT_KEY_CID, HuoDiConstants.HTTP_PARAM_CID);

				intent.putExtra(HuoDiConstants.FROM_SEARCH_TAB_ACTIVITY, true);

				startActivityForResult(intent, HuoDiConstants.REQUEST_CODE_DESTINATION_REGION_FILTER);

			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case HuoDiConstants.REQUEST_CODE_START_REGION_FILTER:
					final long start_cid = data.getLongExtra(HuoDiConstants.HTTP_PARAM_CID, -1);

					startCityTextView.setText(getPlaceName(start_cid));
					startRegion = RegionManager.getRegion(start_cid);
					break;
				case HuoDiConstants.REQUEST_CODE_DESTINATION_REGION_FILTER:
					final long destination_cid = data.getLongExtra(HuoDiConstants.HTTP_PARAM_CID, -1);

					endCityTextView.setText(getPlaceName(destination_cid));
					destinationRegion = RegionManager.getRegion(destination_cid);
					break;
			}
		}
	}

	private String getPlaceName(long id) {
		Region region = RegionManager.getRegion(id);
		if (region != null) {
			if (CitySelector.cityName.equals(region.getName())) {
				return region.getName();
			} else {
				return CitySelector.cityName + region.getName();
			}
		}
		return "";
	}

	private void search() {
		try {

			final ProgressDialog progressDialog = ProgressDialog.show(this, getResources().getString(R.string.please_wait), getResources().getString(R.string.searching), true, true);
			progressDialog.setCanceledOnTouchOutside(false);
			PlanNode start = PlanNode.withLocation(new LatLng(startRegion.getLat() / 1E6, startRegion.getLng() / 1E6));

			PlanNode end = PlanNode.withLocation(new LatLng(destinationRegion.getLat() / 1E6, destinationRegion.getLng() / 1E6));

			RoutePlanSearch mKSearch = RoutePlanSearch.newInstance();
			// 注意，MKSearchListener只支持一个，以最后一次设置为准
			//设置驾车路线规划策略.
			DrivingRoutePlanOption option = new DrivingRoutePlanOption();
			option.from(start).to(end);
			option.policy(DrivingRoutePlanOption.DrivingPolicy.values()[driving_strategy]);
			//驾乘路线搜索.
			mKSearch.drivingSearch(option);
			mKSearch.setOnGetRoutePlanResultListener(new MySearchListener(progressDialog));

		} catch (WindowManager.BadTokenException e) {
			LogUtils.e("MapRouteActivity", e.toString());
		}
	}

	public class MySearchListener implements OnGetRoutePlanResultListener {
		private ProgressDialog progressDialog;

		public MySearchListener(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}


		@Override
		public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

		}

		@Override
		public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

		}

		@Override
		public void onGetDrivingRouteResult(DrivingRouteResult result) {
			try {

				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(MapRouteActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(MapRouteActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
					baiduMap.setOnMarkerClickListener(overlay);
					DrivingRouteLine drivingRouteLine = result.getRouteLines().get(0);
					if (drivingRouteLine != null) {

						overlay.setData(drivingRouteLine);
						overlay.addToMap();
						overlay.zoomToSpan();

						twoCitiesDistanceTextView.setVisibility(View.VISIBLE);
						twoCitiesDistanceTextView.setText(String.format(getResources().getString(R.string.road_mileage_remind), drivingRouteLine.getDistance() / 1000));
					}
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
				}
			} catch (Exception e) {
				LogUtils.e(MapRouteActivity.class.getSimpleName(), e.toString());
			}

		}
	}


	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		long start_id = savedInstanceState.getLong("start_id");
		long end_id = savedInstanceState.getLong("end_id");

		if (start_id > 0) {
			startCityTextView.setText(getPlaceName(start_id));
			startRegion = RegionManager.getRegion(start_id);
		}

		if (end_id > 0) {
			endCityTextView.setText(getPlaceName(end_id));
			destinationRegion = RegionManager.getRegion(end_id);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (startRegion != null) {
			outState.putLong("start_id", startRegion.getId());
		}
		if (destinationRegion != null) {
			outState.putLong("end_id", destinationRegion.getId());
		}
	}

	//定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
		}
	}
}
