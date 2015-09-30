package com.wlqq.huodi.locate;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.data.RegionManager;
import com.wlqq.huodi.utils.LogUtils;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * @author xlw
 *         Date: 13-6-14
 */
public class WLocationListener implements BDLocationListener {

	@Override
	public void onReceiveLocation(BDLocation location) {

		if (location == null)
			return;
		try {

			Log.v("WLocationListener", "location succeed");

			final double lat = location.getLatitude();
			final double lng = location.getLongitude();
			LocationHolder.setLOCATION(new AddressComponent(lat, lng, location.getCity(), location.getAddrStr()));
			final Context context = HuoDiApplication.getContext();
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				Geocoder geocoder;
				List<Address> addresses;
				geocoder = new Geocoder(context, Locale.getDefault());
				try {
					addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
					if (!addresses.isEmpty()) {
						if (addresses.size() > 0) {
							Address locationAddress = addresses.get(0);
							String address = locationAddress.getAddressLine(1) + locationAddress.getAddressLine(2);
							String city = locationAddress.getLocality();
							storeUserAddress(lat, lng, city, address);
							showPrompt(String.format(context.getString(R.string.gps_located), address));
							locationSucceed();
						}
					}
				} catch (Exception e) {
					storeUserAddress(lat, lng, "", "");
					getAddressFailed();
					showPrompt(context.getString(R.string.gps_get_address_failed));
					Log.e("WLocationListener", "failed due to :　" + e);
				}

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				storeUserAddress(lat, lng, location.getCity(), location.getAddrStr());
				showPrompt(String.format(context.getString(R.string.msg_located), location.getAddrStr()));
				locationSucceed();
			} else {
				locationFailed();
				showPrompt(context.getString(R.string.location_failed));
			}
		} catch (Exception e) {
			LogUtils.e(WLocationListener.class.getSimpleName(), e.toString());
		}
	}

	protected void getAddressFailed() {
	}

	protected void locationSucceed() {
	}

	protected void locationFailed() {
	}

	protected void showPrompt(String prompt) {
	}

	@Override
	public void onReceivePoi(BDLocation poiLocation) {
	}

	private void storeUserAddress(double lat, double lng, String city, String address) {
		if (StringUtils.isNotBlank(city)) {
			final Region region = RegionManager.extractCity(city);
			if (region != null) {
				LocationHolder.setUSER_SELECTED_REGION(region);
			}
		}

		LocationHolder.setLOCATION(new AddressComponent(lat, lng, city, address));

	}

}
