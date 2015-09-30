package com.wlqq.huodi.listener;

import android.app.Activity;
import android.content.Intent;

import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.PoiSearchListActivity;


/**
 * @author Cai
 *         Date 12-7-20
 */
public class GasMsgOnClickListener implements ViewItemClickListener {
    private Activity activity;

    public GasMsgOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick() {
        {
            String name = activity.getResources().getString(R.string.gas_station);
            Intent intent = new Intent(activity, PoiSearchListActivity.class);
            intent.putExtra("name", name);
            activity.startActivity(intent);
        }
    }
}
