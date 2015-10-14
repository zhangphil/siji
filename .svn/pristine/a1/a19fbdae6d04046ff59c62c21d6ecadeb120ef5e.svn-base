package hcb.tc.sj.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import hcb.tc.sj.R;
import hcb.tc.sj.activitys.XiangQing;

public class DingDanZhongXinQuanBu extends	Fragment{

	private	SwipeRefreshLayout swipeRefreshLayout;
	
	@Override
	public	void	onAttach(Context context){
		super.onAttach(context);
	}
	
	@Override
	public	void	onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listview, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		ListView listView = (ListView) view.findViewById(android.R.id.list);
		MyListViewAdapter adapter = new MyListViewAdapter(this.getContext(), R.layout.fragment_dingdanzhongxin_item);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

		// 设置下拉多少距离之后开始刷新数据
		swipeRefreshLayout.setDistanceToTriggerSync(100);

		// 设置进度条背景颜色
		swipeRefreshLayout
				.setProgressBackgroundColorSchemeColor(this.getResources().getColor(android.R.color.holo_blue_light));

		// 设置刷新动画的颜色，可以设置1或者更多.
		swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
				getResources().getColor(android.R.color.holo_orange_light),
				getResources().getColor(android.R.color.holo_green_light));

		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(true);
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}
	
	private class MyListViewAdapter extends ArrayAdapter {

		private LayoutInflater layoutInflater;
		private	int	resource;
		private	Context context;

		public MyListViewAdapter(Context context, int resource) {
			super(context, resource);
			layoutInflater = LayoutInflater.from(context);
			this.resource=resource;
			this.context=context;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = layoutInflater.inflate(resource, null);

			Button xiangqingButton=(Button) convertView.findViewById(R.id.xiangqingButton);
			xiangqingButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,XiangQing.class);
					startActivity(intent);
				}
			});
			
			Button callFaHuoRen=(Button) convertView.findViewById(R.id.callFaHuoRen);
			callFaHuoRen.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String number="15918742145";
					
					//用intent启动拨打电话  
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
					startActivity(intent);  
				}
			});
			
			return convertView;
		}

		@Override
		public int getCount() {
			return 20;
		}
	}
}