package hcb.tc.sj.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import hcb.tc.sj.R;
import hcb.tc.sj.activitys.XiangQing;

public class DingDanZhongXinDaiJieHuo extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		setListAdapter(new MyListViewAdapter(this.getContext(), R.layout.fragment_dingdanzhongxin_item));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	}

	private class MyListViewAdapter extends ArrayAdapter {

		private LayoutInflater layoutInflater;
		private int resource;
		private	Context context;

		public MyListViewAdapter(Context context, int resource) {
			super(context, resource);
			layoutInflater = LayoutInflater.from(context);
			this.resource = resource;
			this.context=context;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = layoutInflater.inflate(resource, null);

			TextView huowuzhuangtai = (TextView) convertView.findViewById(R.id.huowuyunsongzhuangtai);
			huowuzhuangtai.setText("待接货");
			
			Button xiangqingButton=(Button) convertView.findViewById(R.id.xiangqingButton);
			xiangqingButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,XiangQing.class);
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
