package com.wlqq.huodi.utils;

import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Tiger Tang
 *         Date: 12-1-11
 *         Time: 上午9:17
 * @since 0.1.20
 */
public class MsgboardUtils {

    public static String getDateDisplayString(final Date date) {
        final StringBuilder buffer = new StringBuilder();
        final Date currentTime = new Date();

        Date yesterday = DateUtils.truncate(currentTime, Calendar.DAY_OF_MONTH);
        Date dayBeforeYesterday = DateUtils.addDays(yesterday, -1);

        final long timeMinus = currentTime.getTime() - date.getTime();
        if (timeMinus < 2000) {
            buffer.append("1秒之前");
        } else if (timeMinus < (60 * 1000)) {
            buffer.append(timeMinus / 1000).append("秒之前");
        } else if (timeMinus < (30 * 60 * 1000)) {
            buffer.append(timeMinus / (60 * 1000)).append("分钟之前");
        } else if (date.before(dayBeforeYesterday)) {
            buffer.append(HuoDiConstants.DF_yyyy_MM_dd.format(date));
        } else if (date.before(yesterday)) {
            buffer.append("昨天").append(HuoDiConstants.DF_HH_mm.format(date));
        } else {
            int hours = date.getHours();
            if (hours < 7) {
                buffer.append("凌晨").append(HuoDiConstants.DF_HH_mm.format(date));
            } else if (hours < 12) {
                buffer.append("上午").append(HuoDiConstants.DF_HH_mm.format(date));
            } else if (hours == 12) {
                buffer.append("中午").append(HuoDiConstants.DF_HH_mm.format(date));
            } else if (hours < 18) {
                buffer.append("下午").append(HuoDiConstants.DF_HH_mm.format(DateUtils.addHours(date, -12)));
            } else {
                buffer.append("晚上").append(HuoDiConstants.DF_HH_mm.format(DateUtils.addHours(date, -12)));
            }
        }

        return buffer.toString();
    }

    public static String getDateDisplayString2(Date date) {
        Date currentTime = new Date();
        return DateUtils.isSameDay(currentTime, date) ? HuoDiConstants.DF_HH_mm.format(date) : HuoDiConstants.DF_MM_dd.format(date);
    }
}
