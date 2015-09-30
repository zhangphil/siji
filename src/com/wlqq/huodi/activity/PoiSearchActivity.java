package com.wlqq.huodi.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.wlqq.huodi.R;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author xlw
 *         Date: 12-5-23
 */
public class PoiSearchActivity extends FragmentActivity {

	private View popView;
	private TextView nameTextView;
	private TextView addressTextView;
	private TextView phoneTextView;
	private MapView mMapView;
	private BaiduMap baiduMap;
	OverlayManager routeOverlay = null;

	RoutePlanSearch mRoutePlanSearch = null;
	private LatLng myGeoPoint;
	private PoiSearch mPoiSearch;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.map_route);
		findViewById(R.id.switch_city_button).setVisibility(View.GONE);
		findViewById(R.id.llSearch).setVisibility(View.GONE);
		findViewById(R.id.twoCitiesDistance).setVisibility(View.GONE);
		mMapView = (MapView) findViewById(R.id.map);
		baiduMap = mMapView.getMap();
		baiduMap.clear();
		progressDialog = ProgressDialog.show(this, getResources().getString(R.string.please_wait), getResources().getString(R.string.searching_map), true, true);
		progressDialog.setCanceledOnTouchOutside(false);
		findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		myGeoPoint = new LatLng(getIntent().getDoubleExtra("startlat", -1), getIntent().getDoubleExtra("startlon", -1));


		mRoutePlanSearch = RoutePlanSearch.newInstance();
		mRoutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
				if (drivingRouteResult == null || drivingRouteResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					return;
				}

				if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {

					DrivingRouteOverlay overlay = new MyDrivingRouteOverlay();
					if (routeOverlay != null) {
						routeOverlay.removeFromMap();
					}
					routeOverlay = overlay;
					DrivingRouteLine drivingRouteLine = drivingRouteResult.getRouteLines().get(0);
					if (overlay != null) {
						overlay.setData(drivingRouteLine);
						overlay.removeFromMap();
						overlay.addToMap();
						overlay.zoomToSpan();
					}
				}
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}
		});

		MyLocationData locData = new MyLocationData.Builder()
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(myGeoPoint.latitude)
				.longitude(myGeoPoint.longitude).build();
		baiduMap.setMyLocationData(locData);


		popView = LayoutInflater.from(this).inflate(R.layout.poi_search, null);

		nameTextView = (TextView) popView.findViewById(R.id.map_bubbleText);
		addressTextView = (TextView) popView.findViewById(R.id.addressTextView);
		phoneTextView = (TextView) popView.findViewById(R.id.phoneTextView);

		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(new MySearchListener());
		PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
		poiNearbySearchOption.location(myGeoPoint);
		poiNearbySearchOption.keyword(getIntent().getStringExtra("keyword"));
		poiNearbySearchOption.radius(10000);
		poiNearbySearchOption.pageCapacity(0);
		mPoiSearch.searchNearby(poiNearbySearchOption);


	}

	public class MySearchListener implements OnGetPoiSearchResultListener {
		@Override
		public void onGetPoiResult(PoiResult poiResult) {
			if (poiResult == null
					|| poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
				return;
			}
			if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
				baiduMap.clear();
				PoiOverlay overlay = new MyPoiOverlay(mRoutePlanSearch);
				baiduMap.setOnMarkerClickListener(overlay);
				overlay.setData(poiResult);
				overlay.addToMap();
				overlay.zoomToSpan();

				double endlat = getIntent().getDoubleExtra("endlat", 0);
				double endlon = getIntent().getDoubleExtra("endlon", 0);
				PlanNode end = PlanNode.withLocation(new LatLng(endlat, endlon));
				PlanNode start = PlanNode.withLocation(myGeoPoint);
				mRoutePlanSearch.drivingSearch((new DrivingRoutePlanOption()).from(start).to(end));
				return;
			}
		}

		@Override
		public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

		}
	}


	public class MyPoiOverlay extends PoiOverlay {
		private RoutePlanSearch mMKSearch;


		public MyPoiOverlay(RoutePlanSearch mMKSearch) {
			super(baiduMap);
			this.mMKSearch = mMKSearch;
		}

		@Override
		public boolean onPoiClick(int i) {
			DrivingRoutePlanOption option = new DrivingRoutePlanOption();
			List<PoiInfo> allPoi = getPoiResult().getAllPoi();
			option.from(PlanNode.withLocation(myGeoPoint));
			PoiInfo poiInfo = allPoi.get(i);
			option.to(PlanNode.withLocation(poiInfo.location));
			mMKSearch.drivingSearch(option);

			nameTextView.setText(poiInfo.name);
			addressTextView.setVisibility(View.VISIBLE);
			addressTextView.setText(getString(R.string.poi_address) + poiInfo.address);
			if (StringUtils.isNotEmpty(poiInfo.phoneNum)) {
				phoneTextView.setVisibility(View.VISIBLE);
				phoneTextView.setText(getString(R.string.lbl_tel) + poiInfo.phoneNum);
			}

			Point p = baiduMap.getProjection().toScreenLocation(poiInfo.location);
			p.y -= 47;
			LatLng llInfo = baiduMap.getProjection().fromScreenLocation(p);

			InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(popView), llInfo, 0, new InfoWindow.OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick() {
					baiduMap.hideInfoWindow();
				}
			});
			baiduMap.showInfoWindow(mInfoWindow);
			return true;
		}
	}

	public static Bitmap convertViewToBitmap(View view) {
		if (view != null) {
			view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
			view.buildDrawingCache();
			return view.getDrawingCache();
		}
		return null;
	}

	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay() {
			super(baiduMap);
		}

		@Override
		public boolean onRouteNodeClick(int i) {
			return true;
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
		}

		@Override
		public void setData(DrivingRouteLine drivingRouteLine) {
			super.setData(drivingRouteLine);
		}
	}


	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mRoutePlanSearch.destroy();
		mPoiSearch.destroy();
		super.onDestroy();
	}

}


