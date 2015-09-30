package com.wlqq.huodi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlqq.huodi.R;
import com.wlqq.huodi.bean.SearchPoi;

import java.util.List;

/**
 * @author xlw
 *         Date: 12-6-13
 */
public class PoiSearchListAdapter extends BaseAdapter {
    private List<SearchPoi> poiList;
    private Context context;

    public PoiSearchListAdapter(List<SearchPoi> poiList, Context context) {
        this.poiList = poiList;
        this.context = context;
    }

    public void clearList() {
        poiList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return poiList == null ? 0 : poiList.size();
    }

    @Override
    public Object getItem(int i) {
        return poiList == null ? null : poiList.get(i);
    }

    public SearchPoi getPoi(int i) {
        return poiList == null ? null : poiList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder mHolder = null;
        if (view == null) {
            mHolder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.poi_search_list_item, null);
            mHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            mHolder.name = (TextView) view.findViewById(R.id.nameTextView);
            mHolder.address = (TextView) view.findViewById(R.id.addressTextView);
            mHolder.distance = (TextView) view.findViewById(R.id.distanceTextView);
            view.setTag(mHolder);
        } else {
            mHolder = (Holder) view.getTag();
        }
        SearchPoi searchPoi = poiList.get(i);
        mHolder.imageView.setImageBitmap(searchPoi.getBitmap());
        mHolder.name.setText(searchPoi.getName());
        mHolder.address.setText(searchPoi.getAddress());
        mHolder.distance.setText(searchPoi.getDistance());
        return view;
    }

    static class Holder {
        private ImageView imageView;
        private TextView name;
        private TextView address;
        private TextView distance;
    }
}
