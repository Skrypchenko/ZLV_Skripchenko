package com.example.zlv_skripchenko.db;

import com.example.zlv_skripchenko.utils.Contract.Names;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseOpenHelper extends SQLiteOpenHelper {

	public boolean onCreateCalled;
	public boolean onUpgradeCalled;
	public boolean onOpenCalled;

    private static final String DATABASE_NAME = "db.db";
    private static final int DATABASE_VERSION = 1;

	private static DatabaseOpenHelper instance;
	public static DatabaseOpenHelper getDatabaseOpenHelperInstance(Context context){
	//	if(instance==null){
			instance = new DatabaseOpenHelper(context);
	//	}
		return instance;
	}

	public static DatabaseOpenHelper getDatabaseOpenHelperInstance(){
		return instance;
	}



	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		 db.execSQL("CREATE TABLE " + Names.TABLE_NAME + " ("
				 + Names._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				 + Names.NAME + " TEXT NOT NULL, "
			     + Names.SURNAME + " TEXT, "
			      + Names.EMAIL + " TEXT, "
				 + Names.TELEPHONE + " TEXT );");
		onCreateCalled = true;
	}



	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		onUpgradeCalled = true;
	}

	@Override
	public void onOpen(SQLiteDatabase database) {
		onOpenCalled = true;
	}

	@Override
	public synchronized void close() {
		onCreateCalled = false;
		onUpgradeCalled = false;
		onOpenCalled = false;

		super.close();
	}

}
