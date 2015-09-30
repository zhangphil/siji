package com.wlqq.huodi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wlqq.huodi.R;
import com.wlqq.huodi.bean.Region;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author xlw
 *         Date: 13-5-29
 */
public class RegionGridViewAdapter extends BaseAdapter {

    private Context context;

    private List<Region> regions;

    private long selectedIndex;

    public RegionGridViewAdapter(Context context, List<Region> regions) {
        this.context = context;
        this.regions = regions;
        selectedIndex = -1;
    }


    @Override
    public int getCount() {
        return regions == null ? 0 : regions.size();
    }

    @Override
    public Object getItem(int i) {
        return regions == null ? new Region(-1, "") : regions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.region_text, null);
            holder.textView = (TextView) view.findViewById(R.id.textView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Region region = regions.get(i);
        final String regionName = region.getName();
        holder.textView.setText(regionName);
        if (StringUtils.isBlank(regionName)) {
            view.setBackgroundResource(R.drawable.item_border);
        } else {
            view.setBackgroundResource(R.drawable.list_selector);
        }
        if (selectedIndex != -1 && region.getId() == selectedIndex) {
            view.setBackgroundResource(R.drawable.item_border_selected);
        } else {
            view.setBackgroundResource(R.drawable.list_selector);
        }
        holder.region = region;
        return view;
    }

    public static class ViewHolder {
        private TextView textView;
        public Region region;
    }

    public void resetDate(List<Region> newDate, long selectedIndex) {
        this.selectedIndex = selectedIndex;
        final int remainder = newDate.size() % 3;
        if (remainder == 2) {
            newDate.add(new Region(-1, ""));
        } else if (remainder == 1) {
            newDate.add(new Region(-1, ""));
            newDate.add(new Region(-1, ""));
        }

        this.regions = newDate;
        notifyDataSetChanged();
    }
}
