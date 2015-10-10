package hcb.tc.sj.activitys;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.TextView;
import hcb.tc.sj.R;
import hcb.tc.sj.fragments.DingDanZhongXin;
import hcb.tc.sj.fragments.QiangDan;
import hcb.tc.sj.view.SlidingTabLayout;

public class Home extends FragmentActivity {

	private	TextView myToolbarTitle;
	private String[] tabName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources res = getResources();
		tabName = res.getStringArray(R.array.tab_name);
		
		setContentView(R.layout.activity_home);

		final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		//mToolbar.setLogo(R.drawable.ic_launcher);
		//mToolbar.setTitleTextColor(Color.WHITE);
		
		myToolbarTitle=(TextView) findViewById(R.id.toolbar_title);
		myToolbarTitle.setText("抢单");
		
		MyViewPagerAdapter adapter = new MyViewPagerAdapter(this.getSupportFragmentManager());
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adapter);

		SlidingTabLayout mSlidingTabLayout=(SlidingTabLayout) findViewById(R.id.slidingTabLayout);
		mSlidingTabLayout.setViewPager(viewPager);  
		//TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		//tabLayout.setSmoothScrollingEnabled(true);

		//tabLayout.setupWithViewPager(viewPager);
		
//		OnTabSelectedListener onTabSelectedListener=new OnTabSelectedListener(){
//
//			@Override
//			public void onTabReselected(Tab arg0) {
//				
//			}
//
//			@Override
//			public void onTabSelected(Tab tab) {
//				int pos=tab.getPosition();
//				myToolbarTitle.setText(tabName[pos]);
//			}
//
//			@Override
//			public void onTabUnselected(Tab arg0) {
//				
//			}};
//			
//		tabLayout.setOnTabSelectedListener(onTabSelectedListener);
	}
	
	private	void	addFrgmentForViewPager(ArrayList<Fragment> fragments){
		fragments.add(new QiangDan());
		fragments.add(new DingDanZhongXin());
		fragments.add(new Fragment());
		fragments.add(new Fragment());
	}

	private class MyViewPagerAdapter extends FragmentPagerAdapter {

		private	ArrayList<Fragment> fragments;
		
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			fragments=new ArrayList<Fragment>();
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
		public	void	setPrimaryItem(ViewGroup container, int position, Object object) {
			super.setPrimaryItem(container, position, object);
			myToolbarTitle.setText(tabName[position]);
		}
	}
}
