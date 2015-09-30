package com.wlqq.huodi.task;

import android.app.Activity;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public class RemoteTaskContext {

    private Activity activity;

    private GenericRemoteTask genericRemoteTask;

    private TaskParams taskParams;

    public RemoteTaskContext(Activity activity, GenericRemoteTask genericRemoteTask, TaskParams taskParams) {
        this.activity = activity;
        this.genericRemoteTask = genericRemoteTask;
        this.taskParams = taskParams;
    }

    public Activity getActivity() {
        return activity;
    }

    public GenericRemoteTask getGenericRemoteTask() {
        return genericRemoteTask;
    }

    public TaskParams getTaskParams() {
        return taskParams;
    }
}
