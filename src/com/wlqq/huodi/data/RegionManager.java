package com.wlqq.huodi.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.Region;
import com.wlqq.huodi.dao.SeedDataDBHelper;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LogUtils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tiger Tang
 *         Date: 12-1-6
 *         Time: 下午4:19
 * @since 0.1.20
 */
public class RegionManager {

    public static final char CHN_OR = '或';
    public static final String GET_LATITUDE_AND_LONGITUDE = "SELECT * FROM region WHERE id = ?";
    private static final Map<Long, Region> cache = new HashMap<Long, Region>();
    private static final List<Region> provinces = new ArrayList<Region>();
    private static final List<Region> cities = new ArrayList<Region>();
    public static final Map<Long, List<Region>> provinceCityMap = new HashMap<Long, List<Region>>();
    private static final Map<Long, List<Region>> cityCountyMap = new HashMap<Long, List<Region>>();

    static {
        final SQLiteDatabase sqLiteDatabase = HuoDiApplication.getDb();
        try {

            final Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, name, lat, lng FROM region", new String[]{});

            while (cursor.moveToNext()) {
                final long id = cursor.getLong(cursor.getColumnIndex("id"));
                final String name = cursor.getString(cursor.getColumnIndex("name")).trim();
                final int lat = cursor.getInt(cursor.getColumnIndex("lat"));
                final int lng = cursor.getInt(cursor.getColumnIndex("lng"));

                final Region region = new Region(id, name, lat, lng);

                cache.put(id, region);

                if (id < 1101) {
                    provinces.add(region);
                } else if (id < 110101) {
                    cities.add(region);
                    final long parent = region.getParent();
                    if (!provinceCityMap.containsKey(parent)) {
                        provinceCityMap.put(parent, new ArrayList<Region>(20));
                    }
                    provinceCityMap.get(parent).add(region);

                } else {
                    final long parent = region.getParent();
                    if (!cityCountyMap.containsKey(parent)) {
                        cityCountyMap.put(parent, new ArrayList<Region>());
                    }
                    cityCountyMap.get(parent).add(region);
                }
            }
            cursor.close();
        } catch (Exception e) {
            LogUtils.e(RegionManager.class.getSimpleName(), e.toString());
        }
    }


    public static Region extractCity(String s) {
        SQLiteDatabase readableDatabase;
        SQLiteStatement statement = null;
        try {
            readableDatabase = SeedDataDBHelper.getInstance().getReadableDatabase();
            statement = readableDatabase.compileStatement("SELECT id FROM region WHERE level = 1 AND name like ?");
            statement.bindString(1, s.replace("市", ""));
            final long id = statement.simpleQueryForLong();
            return new Region(id, s);
        } catch (Exception t) {
            return null;
        } finally {
            if (statement != null)
                statement.close();

        }
    }

    public static boolean isMunicipality(long depId) {
        final long provinceId = getProvinceId(depId);
        final long cityId = getCityId(depId);

        return provinceId <= 15 && (depId < 100 || cityId == (provinceId) * 100 + 1);
    }

    public static String getFullPlaceName(final long depId) {
        final long provinceId = getProvinceId(depId);
        final long cityId = getCityId(depId);

        if (isMunicipality(depId)) {
            if (depId > 110000) {
                return getRegionName(provinceId) + getRegionName(depId);
            } else {
                return getRegionName(provinceId);
            }
        } else if (cityId != HuoDiConstants.INVALID_INTEGER_VALUE) {
            if (depId > 110000) {
                return getRegionName(provinceId) + getRegionName(cityId) + getRegionName(depId);
            } else {
                return getRegionName(provinceId) + getRegionName(cityId);
            }
        } else
            return getRegionName(provinceId);
    }

    public static String getFullPlaceName(final String destIds) {
        if (StringUtils.isBlank(destIds))
            return StringUtils.EMPTY;

        final StringBuilder buffer = new StringBuilder();
        final String[] split = destIds.split(",");

        List<String> list = new ArrayList<String>(split.length);
        Collections.addAll(list, split);
        Collections.sort(list);

        long pid = HuoDiConstants.INVALID_INTEGER_VALUE;
        for (String s : split) {
            if (StringUtils.isNumeric(s)) {
                final Long rid = Long.valueOf(s);
                if (pid == HuoDiConstants.INVALID_INTEGER_VALUE) {
                    pid = getProvinceId(rid);
                    buffer.append(getFullPlaceName(rid));
                } else if (getProvinceId(rid) == pid) {
                    final long cityId = getCityId(rid);
                    if (cityId != HuoDiConstants.INVALID_INTEGER_VALUE)
                        buffer.append(getRegionName(cityId));
                } else {
                    pid = getProvinceId(rid);
                    buffer.append(getFullPlaceName(rid));
                }
            }
        }

        return buffer.toString();
    }

    public static Region getRegion(long id) {
        return cache.get(id);
    }

    public static Region getRegion(String name) {
        final long id = getRegionId(name);
        if (id > 0) {
            return new Region(id, name);
        }

        return null;
    }

    public static List<Region> getProvinces() {
        return provinces;
    }

    public static List<Region> getCitiesByProvinceId(long pid) {
        return provinceCityMap.get(pid);
    }

    public static String getRegionName(long id) {
        if (cache.containsKey(id)) {
            return cache.get(id).getName();
        }

        return StringUtils.EMPTY;
    }

    public static String getRegionName(String ids) {
        final StringBuilder buffer = new StringBuilder();
        final String[] array = ids.split(",");
        for (String s : array) {
            if (StringUtils.isNumeric(s)) {
                buffer.append(getRegionName(Long.valueOf(s)));
                buffer.append(CHN_OR);
            }
        }

        final int length = buffer.length();
        if (length > 0 && buffer.charAt(length - 1) == CHN_OR) {
            buffer.deleteCharAt(length - 1);
        }
        return buffer.toString();
    }

    public static long getRegionId(String name) {
        final SQLiteDatabase readableDatabase = HuoDiApplication.getDb();
        final SQLiteStatement statement = readableDatabase.compileStatement("SELECT id FROM region WHERE name = ?");
        statement.bindString(1, name);
        final long id = statement.simpleQueryForLong();
        statement.close();
        return id;
    }

    public static long getProvinceId(final long rid) {
        if (rid > 110000) {
            return rid / 10000;
        } else if (rid > 1100) {
            return rid / 100;
        } else {
            return rid;
        }
    }

    public static long getCityId(final long rid) {
        if (rid > 100000) {
            return rid / 100;
        } else if (rid > 1000) {
            return rid;
        } else {
            return HuoDiConstants.INVALID_INTEGER_VALUE;
        }
    }

    public static List<Region> getProvinceAndCities() {
        final ArrayList<Region> regions = new ArrayList<Region>(provinces);
        regions.addAll(cities);
        return regions;
    }

    public static boolean isRegion(long pid) {
        return cache.containsKey(pid);
    }

    public static List<Region> getDistrictByCity(long cid) {
        return cityCountyMap.get(cid);
    }
}
