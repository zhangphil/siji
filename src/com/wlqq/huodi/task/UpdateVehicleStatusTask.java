package com.wlqq.huodi.task;

import android.app.Activity;
import com.wlqq.huodi.data.Constants;
import com.wlqq.huodi.json.NULLParser;
import com.wlqq.huodi.json.Parser;

/**
 * User: xlw
 * Date: 14-11-20
 * Email: xlwplm@qq.com
 */
public class UpdateVehicleStatusTask extends GenericRemoteTask<Void>{

    public UpdateVehicleStatusTask(Activity activity) {
        super(activity);
    }

    @Override
    protected String getRemoteServiceAPIUrl() {
        return "/mobile/nearly/allocation-cargo.do";
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
