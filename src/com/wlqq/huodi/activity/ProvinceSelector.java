package com.wlqq.huodi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wlqq.huodi.R;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.data.RegionManager;
import com.wlqq.huodi.utils.AdapterDataUtils;
import com.wlqq.huodi.utils.HuoDiConstants;

import java.util.List;
import java.util.Map;

public class ProvinceSelector extends BaseActivity {

    private String ACTION = HuoDiConstants.REGION_SELECTOR_CITY_ONLY;

    private ListView listView;
    private List<Map<String, Object>> adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<Region> provinces = RegionManager.getProvinces();
        adapterData = AdapterDataUtils.getAdapterDataForRegionList(provinces);
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_province_selector;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.nvp_list;
    }

    protected void setupView() {
        switchCityButton.setVisibility(View.INVISIBLE);

        listView = (ListView) findViewById(R.id.nvp_list);
        listView.setCacheColorHint(0);
        View view = getLayoutInflater().inflate(R.layout.divider, null);
        ImageView divider_img = (ImageView) view.findViewById(R.id.divider_img);
        listView.addFooterView(divider_img, null, false);

        adapterData = AdapterDataUtils.getAdapterDataForRegionList(RegionManager.getProvinces());
        SimpleAdapter adapter = new SimpleAdapter(this, adapterData, R.layout.nvp_list_item, new String[]{"id", "name"}, new int[]{R.id.nvp_value, R.id.nvp_name});
        listView.setAdapter(adapter);
    }

    protected void registerListeners() {
        switchCityButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long pid = (Long) adapterData.get(position).get("id");

                Bundle bundle = ProvinceSelector.this.getIntent().getExtras();

                if (HuoDiConstants.REGION_SELECTOR_PROVINCE_ONLY.equals(ProvinceSelector.this.getIntent().getAction())) {
                    final Intent data = new Intent();
                    data.putExtra(bundle.getString(HuoDiConstants.INTENT_KEY_PID), pid);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                    return;
                }

                final Intent intent = new Intent(ProvinceSelector.this, CitySelector.class);
                intent.putExtras(bundle);
                intent.setAction(getIntent().getAction());
                intent.putExtra(bundle.getString(HuoDiConstants.INTENT_KEY_PID), pid);
                intent.putExtra(HuoDiConstants.FROM_SEARCH_TAB_ACTIVITY, getIntent().getBooleanExtra(HuoDiConstants.FROM_SEARCH_TAB_ACTIVITY, false));

                startActivityForResult(intent, HuoDiConstants.REQUEST_CODE_REGION_FILTER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                setResult(Activity.RESULT_OK, data);
                finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(Activity.RESULT_CANCELED);
            finish();
            return true;
        }
        return false;
    }

}
