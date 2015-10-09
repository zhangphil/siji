package hcb.tc.sj.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import hcb.tc.sj.R;

public class QiangDanFragment extends	Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_qiangdan, container, false);  
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ListView listView=(ListView) view.findViewById(android.R.id.list);
		MyListViewAdapter adapter=new MyListViewAdapter(this.getContext(),-1); 
		listView.setAdapter(adapter);
	}
	
	private	class	MyListViewAdapter	extends	ArrayAdapter{

		private LayoutInflater layoutInflater;

		public MyListViewAdapter(Context context, int resource) {
			super(context, resource);
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			if(convertView==null)
				convertView=layoutInflater.inflate(R.layout.fragment_qiangdan_item, null);
			
			return convertView;
		}

		@Override
		public int getCount() {
			return 20;
		}
	}
}
