package com.wlqq.huodi.listener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.List;

/**
 * @author Tiger Tang
 *         Date: 12-2-25
 *         Time: 下午1:36
 * @since 0.1.20
 */
public class SMSOnClickListener extends SelectPhoneNumberOnClickListener {

    private String smsBody;

    public SMSOnClickListener(Activity parent, List<String> phoneNumbers, String smsBody) {
        super(parent, phoneNumbers);
        this.smsBody = smsBody;
    }

    @Override
    protected void onPhoneNumberSelected(String s) {
        final Uri uri = Uri.parse(new StringBuilder("smsto:").append(s).toString());
        final Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", smsBody);
        parentActivity.startActivity(intent);
    }
}
