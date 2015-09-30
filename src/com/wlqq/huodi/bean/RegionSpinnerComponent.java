package com.wlqq.huodi.bean;

import android.widget.TextView;

/**
 * @author xlw
 *         Date: 12-8-21
 */
public class RegionSpinnerComponent {

    private TextView textView;
    private long value;

    public RegionSpinnerComponent(TextView textView, long value) {
        this.textView = textView;
        this.value = value;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}