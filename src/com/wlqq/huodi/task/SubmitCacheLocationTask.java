package com.wlqq.huodi.task;

import com.wlqq.huodi.json.NULLParser;
import com.wlqq.huodi.json.Parser;
import com.wlqq.huodi.utils.HostProvider;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.WFileUtils;

/**
 * @author xlw
 *         Date 13-12-11
 */
public class SubmitCacheLocationTask extends GenericRemoteTask<Void> {
	@Override
	protected String getRemoteServiceAPIUrl() {
		return HuoDiConstants.API_URL_LOCATION_CACHE;
	}

	@Override
	protected boolean isSecuredAction() {
		return true;
	}

	@Override
	protected HostProvider.HostType getHostType() {
		return HostProvider.HostType.LOC;
	}

	@Override
	protected boolean isShowProgressDialog() {
		return false;
	}

	@Override
	protected Parser<Void> getResultParser() {
		return NULLParser.getInstance();
	}

	@Override
	protected void onSucceed(Void aVoid) {
		super.onSucceed(aVoid);
		WFileUtils.clearTextFile(HuoDiConstants.LOCATION_CACHE_FILE);
	}
}
