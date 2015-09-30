package com.wlqq.huodi.listener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tiger Tang
 *         Date: 12-2-25
 *         Time: 下午1:44
 * @since 0.1.20
 */
public class DialOnClickListener extends SelectPhoneNumberOnClickListener {

    public DialOnClickListener(Activity parentActivity, String... phoneNumbers) {
        this(parentActivity, new ArrayList<String>());
        for (String s : phoneNumbers) {
            if (StringUtils.isNotBlank(s)) {
                super.phoneNumbers.add(s);
            }
        }
    }

    public DialOnClickListener(Activity parentActivity, List<String> phoneNumbers) {
        super(parentActivity, phoneNumbers);
    }

    @Override
    protected void onPhoneNumberSelected(String s) {
        final Uri uri = Uri.parse(new StringBuilder("tel:").append(s).toString());
        final Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        parentActivity.startActivity(intent);
    }

}
