package com.wlqq.huodi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wlqq.huodi.R;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.data.RegionManager;
import com.wlqq.huodi.utils.AdapterDataUtils;
import com.wlqq.huodi.utils.HuoDiConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*@author xlw
* Date: 12-6-4
*/
public class DistrictActivity extends BaseActivity {
    private ListView districtListView;
    private List<Map<String, Object>> adapterData;
    private long cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_district_selector;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.nvp_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

        cid = getIntent().getLongExtra(HuoDiConstants.INTENT_KEY_CID, 11L);

        final List<Region> districts = RegionManager.getDistrictByCity(cid);

        adapterData = new ArrayList<Map<String, Object>>();
        final Map<String, Object> m = new HashMap<String, Object>();

        m.put("id", cid);
        m.put("name", RegionManager.getRegion(cid).getName());

        adapterData.add(m);
        adapterData.addAll(AdapterDataUtils.getAdapterDataForRegionList(districts));

        SimpleAdapter adapter = new SimpleAdapter(this, adapterData, R.layout.nvp_list_item, new String[]{"id", "name"}, new int[]{R.id.nvp_value, R.id.nvp_name});
        districtListView.setAdapter(adapter);
    }

    protected void setupView() {
        switchCityButton.setVisibility(View.INVISIBLE);

        districtListView = (ListView) findViewById(R.id.nvp_list);

        View dividerView = getLayoutInflater().inflate(R.layout.divider, null);
        ImageView dividerImg = (ImageView) dividerView.findViewById(R.id.divider_img);

        districtListView.addFooterView(dividerImg, null, false);
        districtListView.setCacheColorHint(0);
    }

    protected void registerListeners() {
        switchCityButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        districtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Long destination_id = (Long) adapterData.get(position).get("id");

                Intent intent = new Intent();
                intent.putExtra(HuoDiConstants.HTTP_PARAM_CID, destination_id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return false;
    }


}
