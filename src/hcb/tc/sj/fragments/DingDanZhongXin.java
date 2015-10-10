package hcb.tc.sj.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hcb.tc.sj.R;


public class DingDanZhongXin extends	Fragment{
	
	private String[] tabName;
	
	@Override
	public	void	onAttach(Context context){
		super.onAttach(context);
		Resources res = getResources();
		tabName = res.getStringArray(R.array.dingdanzhongxin_tab_name);
	}
	
	@Override
	public	void	onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_dingdanzhongxin, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
	
		MyViewPagerAdapter adapter = new MyViewPagerAdapter(this.getFragmentManager());
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		viewPager.setAdapter(adapter);
		
		tabLayout.setupWithViewPager(viewPager);
	}
	
	private	void	addFragmentsForViewPager(ArrayList<Fragment> fragments){
		fragments.add(new DingDanZhongXinQuanBu());
		fragments.add(new DingDanZhongXinDaiJieHuo());
		fragments.add(new DingDanZhongXingYunSongZhong());
	}
	
	private class MyViewPagerAdapter extends FragmentPagerAdapter {

		private	ArrayList<Fragment> fragments;
		
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			
			fragments=new ArrayList<Fragment>();
			addFragmentsForViewPager(fragments);
		}

		@Override
		public	String	getPageTitle(int pos){
			return	tabName[pos];
		}
		
		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int pos){
			return fragments.get(pos);
		}
	}
}
