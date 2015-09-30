package com.wlqq.huodi.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.PoiSearchListActivity;

/**
 * @author Cai
 *         Date 12-7-20
 */
public class BankMsgOnClickListener implements ViewItemClickListener {
    private Activity activity;

    public BankMsgOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick() {
        {
            AlertDialog.Builder bankBuilder = new AlertDialog.Builder(activity);
            bankBuilder.setTitle(activity.getResources().getString(R.string.please_choice));
            bankBuilder.setItems(R.array.bank, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String name = activity.getResources().getStringArray(R.array.bank)[i];
                    Intent intent = new Intent(activity, PoiSearchListActivity.class);
                    intent.putExtra("name", name);
                    activity.startActivity(intent);
                }
            });
            AlertDialog dialog = bankBuilder.create();
            dialog.show();
        }
    }
}
