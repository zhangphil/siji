package com.wlqq.huodi.dao;

import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wlqq.huodi.app.HuoDiApplication;


public class SeedDataDBHelper extends SQLiteOpenHelper {

	private static final String TAG = "SeedDataDBHelper";

	public static final String NAME = "seed_data.db";

	public static final int VERSION = 3;

	private static SeedDataDBHelper instance = null;

	public static String DATABASE_PATH = "/data/data/" + HuoDiApplication.getContext().getPackageName() + "/lib/libseed_data.db.so";

	public static SeedDataDBHelper getInstance() {
		if (instance == null) {
			instance = new SeedDataDBHelper(DATABASE_PATH);
			Log.v("SeedDataDBHelper", "DATABASE_PATH is : " + DATABASE_PATH);
		}
		return instance;
	}

	private SeedDataDBHelper(final String path) {
		super(new ContextWrapper(HuoDiApplication.getContext()) {
			@Override
			public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
				SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

				return db;
			}
		}, path, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}