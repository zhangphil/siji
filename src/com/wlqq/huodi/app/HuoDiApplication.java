package com.wlqq.huodi.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.baidu.mapapi.SDKInitializer;
import com.mato.sdk.proxy.Proxy;
import com.wlqq.huodi.dao.SeedDataDBHelper;
import com.wlqq.huodi.task.DownloadHostsTask;

/**
 * User: xlw
 * Date: 14-11-17
 * Email: xlwplm@qq.com
 */
public class HuoDiApplication extends Application {

	private static Context context;
	private static SharedPreferences preferences;
	private static SQLiteDatabase db;

	@Override
	public void onCreate() {
		super.onCreate();

		context = this.getApplicationContext();
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		db = SeedDataDBHelper.getInstance().getReadableDatabase();
		SDKInitializer.initialize(this);
	}


	public static SQLiteDatabase getDb() {
		return db;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Proxy.stop();

		db.close();

	}

	public static Context getContext() {
		return context;
	}

	public static SharedPreferences getPreferences() {
		return preferences;
	}
}
