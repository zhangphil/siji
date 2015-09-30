package com.wlqq.huodi.adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.wlqq.huodi.R;

/**
 * author cai
 * Date 13-6-8
 * Time  上午10:48
 */
public class KeyBoardWidgetAdapter extends BaseAdapter {

    private Activity context;

    private String[] list;
    private DisplayMetrics metrics;

    public KeyBoardWidgetAdapter(Activity context, String[] list) {
	this.context = context;
	this.list = list;
	metrics = new DisplayMetrics();
	context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }


    @Override
    public int getCount() {
	return list == null ? 0 : list.length;
    }

    @Override
    public Object getItem(int i) {
	return list == null ? "" : list[i];
    }

    @Override
    public long getItemId(int i) {
	return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
	ViewHolder holder = null;
	if (view == null) {
	    holder = new ViewHolder();
	    view = LayoutInflater.from(context).inflate(R.layout.text, null);
	    holder.textView = (TextView) view.findViewById(R.id.textView);
	    view.setTag(holder);
	} else {
	    holder = (ViewHolder) view.getTag();
	}

	final int heightPixels = metrics.heightPixels;
	if (heightPixels > 799) {

	    final AbsListView.LayoutParams params = new GridView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 105);
	    view.setLayoutParams(params);
	} else {
	    view.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 65));
	}

	if (list[i].equals("deleteUp")) {

	    holder.textView.setBackgroundResource(R.drawable.delete_up_btn_bg);
	    holder.textView.setText("");
	    holder.textView.setWidth(40);
	} else if (list[i].equals("deleteBottom")) {
	    holder.textView.setBackgroundResource(R.drawable.delete_bottom_bg);
	    holder.textView.setText("");
	    holder.textView.setWidth(40);
	} else {
	    holder.textView.setBackgroundResource(R.drawable.keyboard_btn_bg);
	    final String text = list[i];
	    holder.textView.setText(text);
	}
	return view;
    }

    public static class ViewHolder {
	public TextView textView;
    }

    public void resetDate(String[] strings) {
	//判断数字是否相等，这里只判断length即可
	list = new String[0];
	list = strings;
	notifyDataSetChanged();
    }
}
