package com.wlqq.huodi.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.Brand;
import com.wlqq.huodi.bean.NameValuePair;
import com.wlqq.huodi.utils.LogUtils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tiger Tang
 *         Date: 12-1-14
 *         Time: 上午11:07
 * @since 0.1.20
 */
public class SeedData {

    private static final String TAG = "SeedData";

    public static String[] freightTypeArray = {"普货", "重货", "泡货", "整车", "零担", "设备", "配件", "电瓷", "显像管", "电器", "烟叶", "服装", "棉纱", "棉被", "平板纸", "医药", "煤炭", "矿产", "钢铁", "铁粉", "建材", "胶版", "食品", "粮食", "饮料", "危险品", "烟花", "化工", "化肥农药", "石油制品", "轻工产品", "牧产品", "牲畜", "渔产品", "农产品", "水果", "蔬菜", "木材", "木方", "竹片", "轿车", "驾驶室", "特种货物", "军用品", "超宽设备", "散装设备", "灌装货物", "其他"};
    public static String[] unitTypArray = {"吨", "方", "件", "车", "个", "台", "箱"};
    public static String[] commonPhraseArray = {"急装", "随装", "包来回", "高价急装", "装车付款", "门面装货", "本地装货", "求本地货源", "车型不限", "运价好商量", "马上可以装货", "今天定车,明天装货", "本地装货", "不装水果"};

    public static String[] vehicleTypeArray = {"不限", "半封闭", "半挂", "保温", "单车", "低板", "二拖三", "二拖四", "高栏", "高栏单桥", "高栏双桥", "工程车", "后八轮或前", "后八轮或半", "集装箱", "冷藏", "零担", "笼子", "平板", "平板拖", "普通", "起重", "前四后八", "全封闭", "斯太尔", "特种", "危险", "小车", "邮政", "油罐", "自卸", "自由厢板"};
    public static String[] vehicleLengthArray = {"不限", "4.5米", "6.2米", "6.8米", "7.2米", "8.2米", "8.6米", "9.6米", "11.7米", "12.5米", "13米", "13.5米", "14米", "17米", "17.5米", "18米"};
    public static String[] vehicleWheelNumberArray = {"不限", "3轴", "4轴", "5轴", "6轴"};
    public static String[] jobTypeArray = {"不限", "司机", "企业管理类", "车队管理类", "仓库管理类", "销售、采购类", "财务、统计类", "人力资源类", "行政后勤类", "客户服务类", "网络技术类", "媒体公关类", "媒体公关类", "教育培训类", "其它"};
    public static String[] jobDegreeArray = {"不限学历", "高中", "中技", "中专", "大专", "本科", "研究生以上"};
    public static String[] jobSalaryArray = {"面议", "1000以下", "1000-2000", "2000-3000", "3000-5000", "5000-7000", "7000以上"};
    public static String[] jobExperienceArray = {"不限工作经验", "0-2年", "3-5年", "6-10年", "10年以上"};

    public static String[] engineBrandArray = {"其他发动机品牌", "淮柴", "玉柴", "康明斯", "锡柴"};
    public static String[] vehicleBrandArray;

    public static String[] comTypeArray = {"", "个体经营", "私营有限公司", "私营独资企业", "私营合伙企业", "私营股份有限公司", "国有企业", "集体企业", "股份合作企业", "联营企业", "有限责任公司(国有独资)", "其他有限责任公司", "", "", "", ""};
    public static String[] comScaleArray = {};

    static {
        List<Brand> vehicleBrands = SeedData.getVehicleBrands();
        final String[] vehicleBrandNames = new String[vehicleBrands.size() + 1];
        vehicleBrandNames[0] = "其他整车品牌";
        for (int i = 0; i < vehicleBrands.size(); i++) {
            vehicleBrandNames[i + 1] = vehicleBrands.get(i).getName();
        }

        vehicleBrandArray = vehicleBrandNames;
    }

    public static List<Brand> getVehicleBrands() {
        final List<Brand> brands = new ArrayList<Brand>();
        SQLiteDatabase readableDatabase = null;
        Cursor cursor = null;
        try {
            readableDatabase = HuoDiApplication.getDb();
            cursor = readableDatabase.rawQuery("SELECT * FROM brand WHERE type = ? AND system_defined = ?", new String[]{Brand.Type.VEHICLE.ordinal() + StringUtils.EMPTY, "1"});
            while (cursor.moveToNext()) {
                final Brand brand = new Brand();

                brand.setId(cursor.getInt(cursor.getColumnIndex("id")));
                brand.setName(cursor.getString(cursor.getColumnIndex("name")));

                brands.add(brand);
            }
            return brands;
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return brands;
    }

    public static ArrayList<NameValuePair> getQipeiTopLevelCategoryAsNVPs() {
        final ArrayList<NameValuePair> items = new ArrayList<NameValuePair>();
        SQLiteDatabase readableDatabase;
        Cursor cursor = null;
        try {
            readableDatabase = HuoDiApplication.getDb();
            cursor = readableDatabase.rawQuery("SELECT * FROM qipei_category WHERE parent = 0", new String[]{});
            while (cursor.moveToNext()) {

                final long value = cursor.getLong(cursor.getColumnIndex("id"));
                final String name = cursor.getString(cursor.getColumnIndex("name"));

                final NameValuePair item = new NameValuePair(value, name);

                items.add(item);
            }
            return items;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public static ArrayList<NameValuePair> getQipeiSubCategoriesAsNVPs(final long parent) {
        final ArrayList<NameValuePair> items = new ArrayList<NameValuePair>();
        SQLiteDatabase readableDatabase;
        Cursor cursor = null;
        try {
            readableDatabase = HuoDiApplication.getDb();
            cursor = readableDatabase.rawQuery("SELECT * FROM qipei_category WHERE parent = ?", new String[]{Long.toString(parent)});
            while (cursor.moveToNext()) {
                final long value = cursor.getLong(cursor.getColumnIndex("id"));
                final String name = cursor.getString(cursor.getColumnIndex("name"));

                final NameValuePair item = new NameValuePair(value, name);
                items.add(item);
            }
            return items;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

}
