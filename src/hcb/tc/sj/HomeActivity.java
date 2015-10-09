package hcb.tc.sj;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import hcb.tc.sj.fragments.QiangDanFragment;

public class HomeActivity extends FragmentActivity {

	private String[] tabName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources res = getResources();
		tabName = res.getStringArray(R.array.tab_name);
		
		setContentView(R.layout.activity_home);

		final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		// mToolbar.setLogo(R.drawable.ic_launcher);
		mToolbar.setTitle(tabName[0]);
		mToolbar.setTitleTextColor(Color.WHITE);
		
		MyViewPagerAdapter adapter = new MyViewPagerAdapter(this.getSupportFragmentManager());
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setSmoothScrollingEnabled(true);

		tabLayout.setupWithViewPager(viewPager);
		
		OnTabSelectedListener onTabSelectedListener=new OnTabSelectedListener(){

			@Override
			public void onTabReselected(Tab arg0) {
				
			}

			@Override
			public void onTabSelected(Tab tab) {
				int pos=tab.getPosition();
				mToolbar.setTitle(tabName[pos]);
			}

			@Override
			public void onTabUnselected(Tab arg0) {
				
			}};
		tabLayout.setOnTabSelectedListener(onTabSelectedListener);
	}

	private class MyViewPagerAdapter extends FragmentPagerAdapter {

		private	ArrayList<Fragment> fragments;
		
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			
			fragments=new ArrayList<Fragment>();
			for(int i=0;i<tabName.length;i++){
				Fragment f=new QiangDanFragment();
				fragments.add(f);
			}
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

	}
}
