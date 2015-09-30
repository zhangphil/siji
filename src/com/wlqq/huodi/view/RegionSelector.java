package com.wlqq.huodi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.wlqq.huodi.R;
import com.wlqq.huodi.adapter.RegionGridViewAdapter;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.data.RegionManager;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xlw
 *         Date: 13-6-8
 */
public class RegionSelector extends LinearLayout {

	private Context context;

	private Button provinceButton;
	private Button cityButton;
	private Button countyButton;

	public GridView regionsGridView;
	private RegionGridViewAdapter adapter;

	public boolean isShowCounty;  //default false
	public boolean isShowCity;    //default true
	private boolean isAutoPop;     //default false

	private boolean provinceMode;
	private boolean cityMode;
	private boolean countyMode;

	private long pid = -1;
	private long cid = -1;
	private long cntid = -1;

	public boolean isSearch = false;

	public RegionSelector(Context context) {
		super(context);
	}

	public RegionSelector(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		LayoutInflater.from(context).inflate(R.layout.region_selector, this, true);

		provinceButton = (Button) findViewById(R.id.provinceButton);
		cityButton = (Button) findViewById(R.id.cityButton);
		countyButton = (Button) findViewById(R.id.countyButton);
		regionsGridView = (GridView) findViewById(R.id.region_gridview);

		final List<Region> provinces = RegionManager.getProvinces();
		adapter = new RegionGridViewAdapter(context, provinces);
		regionsGridView.setAdapter(adapter);

		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RegionSelector);

		isShowCity = typedArray.getBoolean(R.styleable.RegionSelector_isShowCity, true);
		isShowCounty = typedArray.getBoolean(R.styleable.RegionSelector_isShowCounty, false);
		isAutoPop = typedArray.getBoolean(R.styleable.RegionSelector_isAutoPop, false);

		if (isShowCounty) {
			isShowCity = true;//if use set show county, city is must show
			countyButton.setVisibility(View.VISIBLE);
		} else {
			countyButton.setVisibility(View.GONE);
			cityButton.setBackgroundResource(R.drawable.select_right);
		}

		if (isShowCity) {
			cityButton.setVisibility(View.VISIBLE);
		} else {
			cityButton.setVisibility(View.GONE);
		}

		registerListener();
		typedArray.recycle();
	}

	private void registerListener() {
		provinceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (provinceMode && regionsGridView.isShown()) {
					regionsGridView.setVisibility(View.GONE);
					isSearch = true;
					provinceMode = false;
					setInitialBackground();
				} else {
					final List<Region> provinces = RegionManager.getProvinces();
					adapter.resetDate(provinces, pid);
					regionsGridView.setVisibility(View.VISIBLE);
					isSearch = false;
					provinceMode = true;
					cityMode = false;
					countyMode = false;

					provinceButton.setBackgroundResource(R.drawable.select_left_pressed);
					if (isShowCounty) {
						cityButton.setBackgroundResource(R.drawable.select_middle_normal);
					} else {
						cityButton.setBackgroundResource(R.drawable.select_right_normal);
					}
					countyButton.setBackgroundResource(R.drawable.select_right_normal);
				}
				onRegionChangeListener.regionChanged();
			}
		});

		cityButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (pid == -1) {
					Toast.makeText(context, "请先选择省", Toast.LENGTH_SHORT).show();
					return;
				}

				if (cityMode && regionsGridView.isShown()) {
					regionsGridView.setVisibility(View.GONE);
					isSearch = true;
					setInitialBackground();
					cityMode = false;
				} else {
					final List<Region> cities = new ArrayList<Region>();
					cities.addAll(RegionManager.getCitiesByProvinceId(pid));
					adapter.resetDate(cities, cid);
					regionsGridView.setVisibility(View.VISIBLE);
					isSearch = false;
					cityMode = true;
					provinceMode = false;
					countyMode = false;
					if (isShowCounty) {
						cityButton.setBackgroundResource(R.drawable.select_middle_pressed);
					} else {
						cityButton.setBackgroundResource(R.drawable.select_right_pressed);
					}
					provinceButton.setBackgroundResource(R.drawable.select_left_normal);
					countyButton.setBackgroundResource(R.drawable.select_right_normal);
				}
				onRegionChangeListener.regionChanged();
			}
		});

		countyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (pid == -1) {
					Toast.makeText(context, "请先选择省和市", Toast.LENGTH_SHORT).show();
					return;
				}
				if (cid == -1) {
					Toast.makeText(context, "请先选择市", Toast.LENGTH_SHORT).show();
					return;
				}

				if (countyMode && regionsGridView.isShown()) {
					regionsGridView.setVisibility(View.GONE);
					isSearch = true;
					setInitialBackground();
					countyMode = false;
				} else {
					final List<Region> districts = RegionManager.getDistrictByCity(cid);
					if (districts == null) {
						adapter.resetDate(new ArrayList<Region>(), -1);
						regionsGridView.setVisibility(View.GONE);
						setInitialBackground();
						isSearch = true;
						return;
					}
					adapter.resetDate(districts, cntid);
					regionsGridView.setVisibility(View.VISIBLE);
					isSearch = false;
					countyMode = true;
					provinceMode = false;
					cityMode = false;

					countyButton.setBackgroundResource(R.drawable.select_right_pressed);
					provinceButton.setBackgroundResource(R.drawable.select_left_normal);
					cityButton.setBackgroundResource(R.drawable.select_middle_normal);
				}
				onRegionChangeListener.regionChanged();
			}
		});

		regionsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				final Region region = ((RegionGridViewAdapter.ViewHolder) view.getTag()).region;
				final long id = region.getId();
				if (id < 0) {
					return;
				}
				final String name = region.getName();

				Log.v("RegionSelector", "select region is id : " + id + "name : " + name);

				provinceMode = false;
				cityMode = false;
				countyMode = false;

				if (id < 1101) {
					if (pid != id || cid == -1) {
						pid = id;

						provinceButton.setText(name);
						provinceButton.setTextColor(Color.parseColor("#3f3f3f"));

						if (isShowCity) {
							cityButton.setText("选择市");
							cityButton.setTextColor(Color.parseColor("#9a9a9a"));
							cid = -1;
							cntid = -1;
							countyButton.setText("区/县");
							countyButton.setTextColor(Color.parseColor("#9a9a9a"));
							if (isAutoPop) {
								cityButton.performClick();
							} else {
								regionsGridView.setVisibility(View.GONE);
								isSearch = true;
							}
						} else {
							regionsGridView.setVisibility(View.GONE);
							isSearch = true;
						}
						onRegionChangeListener.regionChanged();
					} else {
						regionsGridView.setVisibility(View.GONE);
						isSearch = true;
					}

				} else if (id < 110101) {
					if (cid != id || cntid == -1) {
						cid = id;

						cityButton.setText(name);
						cityButton.setTextColor(Color.parseColor("#3f3f3f"));
						if (isShowCounty) {
							cntid = -1;
							countyButton.setText("区/县");
							countyButton.setTextColor(Color.parseColor("#9a9a9a"));
							if (isAutoPop) {
								countyButton.performClick();
							} else {
								regionsGridView.setVisibility(View.GONE);
								isSearch = true;
								setInitialBackground();
							}
						} else {
							regionsGridView.setVisibility(View.GONE);
							isSearch = true;
							provinceButton.setBackgroundResource(R.drawable.select_left);
							cityButton.setBackgroundResource(R.drawable.select_right);
						}
						onRegionChangeListener.regionChanged();
					} else {
						isSearch = true;
						regionsGridView.setVisibility(View.GONE);
					}
				} else {
					cntid = id;
					countyButton.setText(name);
					countyButton.setTextColor(Color.parseColor("#3f3f3f"));
					regionsGridView.setVisibility(View.GONE);
					isSearch = true;
					onRegionChangeListener.regionChanged();
					setInitialBackground();
				}
			}
		});
	}

	public long getPid() {
		return pid;
	}


	public long getCid() {
		return cid;
	}

	public void setPid(long pid) {
		final long provinceId = RegionManager.getProvinceId(pid);
		if (RegionManager.isRegion(provinceId)) {
			this.pid = provinceId;
			provinceButton.setText(RegionManager.getRegionName(provinceId));
			provinceButton.setTextColor(Color.parseColor("#3f3f3f"));
		} else {
			this.pid = provinceId;
			provinceButton.setText("选择省");
			provinceButton.setTextColor(Color.parseColor("#9a9a9a"));
		}
	}

	public void setCid(long cid) {
		final long cityId = RegionManager.getCityId(cid);
		if (RegionManager.isRegion(cityId) && cityId >= 1101) {
			this.cid = cityId;
			cityButton.setText(RegionManager.getRegionName(cityId));
			cityButton.setTextColor(Color.parseColor("#3f3f3f"));
		} else {
			this.cid = cityId;
			cityButton.setText("选择市");
			cityButton.setTextColor(Color.parseColor("#9a9a9a"));
		}
	}

	public void setCntid(long cntid) {
		if (cntid >= 110101 && RegionManager.isRegion(cntid)) {
			this.cntid = cntid;
			countyButton.setText(RegionManager.getRegionName(cntid));
			countyButton.setTextColor(Color.parseColor("#3f3f3f"));
		}
	}

	public long getCntid() {
		return cntid;
	}


	public void setProvinceText(String text) {
		pid = -1;
		cid = -1;
		cntid = -1;
		provinceButton.setText(text);
		cityButton.setText(text);
		countyButton.setText(text);
	}

	private void setInitialBackground() {
		provinceButton.setBackgroundResource(R.drawable.select_left);
		cityButton.setBackgroundResource(R.drawable.select_middle);
		countyButton.setBackgroundResource(R.drawable.select_right);
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		provinceButton.setEnabled(isEnabled);
		cityButton.setEnabled(isEnabled);
		countyButton.setEnabled(isEnabled);
	}

	public void setOnRegionChangeListener(OnRegionChangeListener onRegionChangeListener) {
		this.onRegionChangeListener = onRegionChangeListener;
	}

	private OnRegionChangeListener onRegionChangeListener = new OnRegionChangeListener() {
		@Override
		public void regionChanged() {
		}
	};

	public String getCityButtonTextString() {
		String s = cityButton.getText().toString();
		return s == null ? "" : s;
	}

	public String getProvinceButtonTextString() {
		String s = provinceButton.getText().toString();
		return s == null ? "" : s;
	}

	public String getCountyButtonTextString() {
		String s = countyButton.getText().toString();
		return s == null ? "" : s;
	}

	public interface OnRegionChangeListener {
		void regionChanged();
	}
}
