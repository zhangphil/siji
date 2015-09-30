package com.wlqq.huodi.task;

import android.app.Activity;

import com.wlqq.huodi.json.NULLParser;
import com.wlqq.huodi.json.Parser;
import com.wlqq.huodi.utils.HuoDiConstants;

/**
 * User: xlw
 * Date: 14-11-20
 * Email: xlwplm@qq.com
 */
public class SubmitBookingInfoTask extends GenericRemoteTask<Void> {

	public SubmitBookingInfoTask(Activity activity) {
		super(activity);
	}

	@Override
	protected String getRemoteServiceAPIUrl() {
		return HuoDiConstants.API_SUBMIT_BOOKING_INFO;
	}

	@Override
	protected boolean isSecuredAction() {
		return true;
	}

	@Override
	protected Parser<Void> getResultParser() {
		return NULLParser.getInstance();
	}
}
