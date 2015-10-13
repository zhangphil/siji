package hcb.tc.sj.activitys;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;
import hcb.tc.sj.R;

/**
 * 这部分百度地图的定位代码中使用了较为复杂。
 * 若希望使用更简单的定位代码请参考这篇技术文档：
 * http://blog.csdn.net/zhangphil/article/details/48542553
 * 
 */

public class DaiJieHuo extends Activity {

	private BaiduMap mBaiduMap = null;
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();

	private MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_daijiehuo);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);

		final Activity activity = this;
		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_daijeihuo_toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});

		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.setTitle("待接货");

		try {
			trimBaiduMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

		initBaiduMap();
	}

	private void trimBaiduMap() throws Exception {
		// 隐藏百度的LOGO
		View child = mMapView.getChildAt(1);
		if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
			child.setVisibility(View.INVISIBLE);
		}

		// 不显示地图上比例尺
		mMapView.showScaleControl(false);

		// 不显示地图缩放控件（按钮控制栏）
		mMapView.showZoomControls(false);
	}

	private void initBaiduMap() {
		mBaiduMap = mMapView.getMap();

		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		initLocation();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		// 开始定位
		mLocationClient.start();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 0;// 设置0将只定位一次；设置1000将每隔一秒定位一次
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		//必须！否则将造成百度地图切换回来时候整个APP崩溃
		// 退出时销毁定位
		mLocationClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);

		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);

			LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(u);

			/*
			 * 以下代码同样有效 double lat = location.getLatitude(); double lng =
			 * location.getLongitude();
			 * 
			 * LatLng ll = new LatLng(lat, lng);
			 * 
			 * 
			 * // 添加气泡 MarkerOptions markerOptions = new
			 * MarkerOptions().position(ll)
			 * .icon(BitmapDescriptorFactory.fromResource(R.drawable.mycar));
			 * 
			 * Marker marker = (Marker) (mBaiduMap.addOverlay(markerOptions));
			 * mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			 * 
			 * @Override public boolean onMarkerClick(Marker marker) { return
			 * false; } });
			 * 
			 * MapStatusUpdate mapStatusUpdate =
			 * MapStatusUpdateFactory.newLatLngZoom(ll, 16.0f);
			 * mBaiduMap.setMapStatus(mapStatusUpdate);
			 */

			if (Config.DEBUG) {
				// Receive Location
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 单位：公里每小时
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 单位：米
					sb.append("\ndirection : ");
					sb.append(location.getDirection());// 单位度
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");

				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
					// 运营商信息
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}

				sb.append("\nlocationdescribe : ");
				sb.append(location.getLocationDescribe());// 位置语义化信息
				List<Poi> list = location.getPoiList();// POI数据
				if (list != null) {
					sb.append("\npoilist size = : ");
					sb.append(list.size());
					for (Poi p : list) {
						sb.append("\npoi= : ");
						sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
					}
				}
				Log.i("BaiduLocationApiDem", sb.toString());
			}

		}
	}
}
