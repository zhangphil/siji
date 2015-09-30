package com.wlqq.huodi.task;

import android.app.Activity;
import com.wlqq.huodi.json.NULLParser;
import com.wlqq.huodi.json.Parser;

/**
 * User: xlw
 * Date: 14-11-20
 * Email: xlwplm@qq.com
 */

public class RegisterTask extends GenericRemoteTask<Void> {

    public RegisterTask(Activity activity) {
        super(activity);
    }

    @Override
    protected String getRemoteServiceAPIUrl() {
        return "/mobile/nearly/register.do";
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
