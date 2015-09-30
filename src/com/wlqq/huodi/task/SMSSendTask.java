package com.wlqq.huodi.task;

import android.app.Activity;

import com.wlqq.huodi.data.Constants;
import com.wlqq.huodi.json.NULLParser;
import com.wlqq.huodi.json.Parser;
import com.wlqq.huodi.utils.HuoDiConstants;

/**
 * User: xlw
 * Date: 14-11-20
 * Email: xlwplm@qq.com
 */
public class SMSSendTask extends GenericRemoteTask<Void> {

	public SMSSendTask(Activity activity) {
		super(activity);
	}

	@Override
	protected String getRemoteServiceAPIUrl() {
		return HuoDiConstants.API_GET_SMS_CODE;
	}

	@Override
	protected boolean isSecuredAction() {
		return false;
	}

	@Override
	protected Parser<Void> getResultParser() {
		return NULLParser.getInstance();
	}
}
