package com.wlqq.huodi.task;

import android.app.Activity;

import com.wlqq.huodi.json.NULLParser;
import com.wlqq.huodi.json.Parser;
import com.wlqq.huodi.json.StringParser;
import com.wlqq.huodi.utils.HuoDiConstants;

/**
 * @author Cai
 *         Date 12-6-26
 */
public class CheckVehicleIsExistTask extends GenericRemoteTask<String> {
	private Activity activity;

	public CheckVehicleIsExistTask(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected String getRemoteServiceAPIUrl() {
		return HuoDiConstants.API_CHECK_PLN_IS_EXIST;
	}

	@Override
	protected boolean isSecuredAction() {
		return false;
	}


	@Override
	protected Parser getResultParser() {
		return StringParser.getInstance();
	}

	@Override
	protected boolean isShowProgressDialog() {
		return false;
	}
}
