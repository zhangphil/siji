package hcb.tc.sj.activitys;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import hcb.tc.sj.R;
import hcb.tc.sj.fragments.DingDanZhongXin;
import hcb.tc.sj.fragments.GongJu;
import hcb.tc.sj.fragments.QiangDan;
import hcb.tc.sj.fragments.Wo;
import hcb.tc.sj.view.SlidingTabLayout;


public class Home extends FragmentActivity {

	private Toolbar mToolbar;
	private String[] tabName;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Resources res = getResources();
		tabName = res.getStringArray(R.array.tab_name);

		setContentView(R.layout.activity_home);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mToolbar.setTitleTextColor(Color.WHITE);
		mToolbar.setTitle("抢单");

		spinner = (Spinner) findViewById(R.id.spinner);

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				res.getStringArray(R.array.city));

		// 设置下拉列表的风格
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(spinnerAdapter);
		spinner.setSelection(1);

		// 添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		final MyViewPagerAdapter adapter = new MyViewPagerAdapter(this.getSupportFragmentManager());
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adapter);

		SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);
		mSlidingTabLayout.setViewPager(viewPager);
	}

	private void addFrgmentForViewPager(ArrayList<Fragment> fragments) {
		fragments.add(new QiangDan());
		fragments.add(new DingDanZhongXin());
		fragments.add(new GongJu());
		fragments.add(new Wo());
	}

	private class MyViewPagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> fragments;

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			fragments = new ArrayList<Fragment>();
			addFrgmentForViewPager(fragments);
		}

		@Override
		public int getCount() {
			return tabName.length;
		}

		@Override
		public Fragment getItem(int pos) {
			return fragments.get(pos);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabName[position];
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			super.setPrimaryItem(container, position, object);
			mToolbar.setTitle(tabName[position]);

			if (position == 0)
				spinner.setVisibility(View.VISIBLE);
			else
				spinner.setVisibility(View.GONE);
		}
	}
}
