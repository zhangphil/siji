package com.wlqq.huodi.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.wlqq.huodi.R;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tiger Tang
 *         Date: 12-2-25
 *         Time: 下午1:26
 * @since 0.1.20
 */
public abstract class SelectPhoneNumberOnClickListener implements View.OnClickListener {

	protected Activity parentActivity;
	protected AlertDialog alertDialog;

	protected List<String> phoneNumbers = new LinkedList<String>();

	public SelectPhoneNumberOnClickListener(Activity parentActivity, List<String> phoneNumbers) {
		this.parentActivity = parentActivity;
		for (String num : phoneNumbers) {
			if (StringUtils.isNotBlank(num)) {
				this.phoneNumbers.add(num);
			}
		}
	}

	public void onClick(View v) {
		if (phoneNumbers.size() > 1) {

			final View selector = LayoutInflater.from(parentActivity).inflate(R.layout.phone_selector, null);
			final ListView phoneNumberListView = (ListView) selector.findViewById(R.id.phone_list);
			phoneNumberListView.setAdapter(new ArrayAdapter<String>(parentActivity, android.R.layout.simple_list_item_1, phoneNumbers));
			phoneNumberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					alertDialog.dismiss();
					onPhoneNumberSelected((String) parent.getItemAtPosition(position));
				}
			});

			alertDialog = new AlertDialog.Builder(parentActivity).setView(selector).setTitle(R.string.phone_list).show();
		} else if (!phoneNumbers.isEmpty()) {
			onPhoneNumberSelected(phoneNumbers.get(0));
		}
	}

	protected abstract void onPhoneNumberSelected(String s);
}
