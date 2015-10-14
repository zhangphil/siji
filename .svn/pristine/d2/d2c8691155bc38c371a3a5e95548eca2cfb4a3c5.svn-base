package hcb.tc.sj.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import hcb.tc.sj.R;
import hcb.tc.sj.activitys.DaiJieHuo;
import hcb.tc.sj.activitys.XiangQing;
import hcb.tc.sj.utils.DanZiZhuangTai;

public class QiangDan extends Fragment {

	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listview, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ListView listView = (ListView) view.findViewById(android.R.id.list);
		MyListViewAdapter adapter = new MyListViewAdapter(this.getContext(), -1);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		
		//目前先暂时不添加下拉刷新功能
		//所以先屏蔽下拉刷新功能
		swipeRefreshLayout.setEnabled(false);
		
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

		private Context context;
		private LayoutInflater layoutInflater;

		public MyListViewAdapter(Context context, int resource) {
			super(context, resource);
			layoutInflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = layoutInflater.inflate(R.layout.fragment_qiangdan_item, null);

			ImageView xiangqingImageView = (ImageView) convertView.findViewById(R.id.xiangqingImageView);
			xiangqingImageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, XiangQing.class);
					startActivity(intent);
				}
			});

			final TextView danzizhuangtai = (TextView) convertView.findViewById(R.id.danZiZhuangTaiTextView);

			final int type = getItemViewType(pos);
			String label = DanZiZhuangTai.getName(type);
			danzizhuangtai.setText(label);
			if (type == DanZiZhuangTai.YIJINGJIA.type) {
				danzizhuangtai.setBackgroundResource(R.drawable.yuan_gray);
			}
			danzizhuangtai.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (type == DanZiZhuangTai.QIANGDAN.type) {

						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setMessage("是否确认抢单？\n（抢单成功后，请立即与货主联系）").setNegativeButton("取消", null)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (which == DialogInterface.BUTTON_POSITIVE) {
									Intent intent = new Intent(getContext(), DaiJieHuo.class);
									startActivity(intent);
								}

							}
						}).setTitle("").show();
					}

					if (type == DanZiZhuangTai.JINGJIA.type) {

						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setNegativeButton("取消", null)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

							}
						}).setTitle("竞价").setView(R.layout.popup_window).show();
					}

				}

			});

			return convertView;
		}

		@Override
		public int getItemViewType(int pos) {
			int n = pos % 3;
			int type = 0;

			for (DanZiZhuangTai d : DanZiZhuangTai.values()) {
				if (d.ordinal() == n) {
					type = d.type;
				}
			}

			return type;
		}

		@Override
		public int getCount() {
			return 20;
		}
	}
}
