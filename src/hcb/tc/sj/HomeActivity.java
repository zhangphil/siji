package hcb.tc.sj;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
		mToolbar.setTitle("待初始化");
		mToolbar.setTitleTextColor(Color.WHITE);


		MyViewPagerAdapter adapter = new MyViewPagerAdapter(this.getSupportFragmentManager());
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setSmoothScrollingEnabled(true);

		tabLayout.setupWithViewPager(viewPager);
		//mSlidingTabLayout.setViewPager(viewPager);  
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
