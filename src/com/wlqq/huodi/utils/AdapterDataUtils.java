package com.wlqq.huodi.utils;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.Brand;
import com.wlqq.huodi.bean.Message;
import com.wlqq.huodi.bean.Msgboard;
import com.wlqq.huodi.bean.NameValuePair;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.data.RegionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tiger Tang
 *         Date: 12-1-8
 *         Time: 下午4:23
 * @since 0.1.20
 */
public class AdapterDataUtils {
    private static String NAME = "name";

    public static List<Map<String, Object>> getAdapterDataForRegionList(final List<Region> regions) {
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(regions.size());

        for (Region r : regions) {
            final Map<String, Object> m = new HashMap<String, Object>();

            m.put("id", r.getId());
            m.put(NAME, r.getName());
            data.add(m);
        }

        return data;
    }

    public static List<Map<String, Object>> getAdapterDataForMsgboard(Msgboard msgboard) {
        final List<Message> msgs = msgboard.getMsgs();
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(msgs.size());

        for (Message m : msgs) {
            final Map<String, Object> e = new HashMap<String, Object>();

            e.put("id", m.getId());
            e.put("c", m.getContent());
            e.put("ct", MsgboardUtils.getDateDisplayString(m.getCreateTime()));
            e.put("dep", RegionManager.getFullPlaceName(m.getDeparturePlaceId()));
            e.put("dest", RegionManager.getFullPlaceName(m.getDestinationPlaceId()));
            e.put("m", m.getMobile());
            e.put("tel", m.getTel());
            e.put("mnn", m.getMicroNetNo());
            e.put("qq", m.getQq());
            e.put("udn", m.getUserDisplayName());
            e.put("n", HuoDiApplication.getContext().getString(R.string.freight_note));

            data.add(e);
        }

        return data;
    }

    public static List<Map<String, Object>> getAdapterDataForBrandList(List<Brand> brands) {
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(brands.size());

        for (Brand brand : brands) {
            final Map<String, Object> m = new HashMap<String, Object>();

            m.put("id", brand.getId());
            m.put(NAME, brand.getName());

            data.add(m);
        }

        return data;
    }


    public static List<Map<String, Object>> getAdapterDataForNameValuePairList(List<NameValuePair> items) {
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(items.size());

        for (NameValuePair pair : items) {
            final Map<String, Object> m = new HashMap<String, Object>();

            m.put("value", pair.getValue());
            m.put(NAME, pair.getName());

            data.add(m);
        }

        return data;
    }
}
