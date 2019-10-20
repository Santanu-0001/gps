package com.gps_3;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "gps";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COL1 = "lati";
	public static final String COLUMN_COL2 = "longi";
	
	public static final String TABLE_NAME_1 = "popup_alert";
	public static final String COLUMN_ID_1 = "_id";
	public static final String COLUMN_COL1_1 = "jobid";
	public static final String COLUMN_COL2_1 = "message";
	public static final String COLUMN_COL3_1 = "dt";
	public static final String COLUMN_COL4_1 = "acc";

	public static final String TABLE_NAME_2 = "checkin";
	public static final String COLUMN_ID_2 = "_id";
	public static final String COLUMN_COL1_2 = "jobid";
	
	public static final String TABLE_NAME_3 = "travel";
	public static final String COLUMN_ID_3 = "_id";
	public static final String COLUMN_COL1_3 = "jobid";
	public static final String COLUMN_COL2_3 = "dt";
	public static final String COLUMN_COL3_3 = "fooding";
	public static final String COLUMN_COL4_3 = "lodging";

	public static final String TABLE_NAME_4 = "transport";
	public static final String COLUMN_ID_4 = "_id";
	public static final String COLUMN_COL1_4 = "jobid";
	public static final String COLUMN_COL2_4 = "dt";
	public static final String COLUMN_COL3_4 = "from_loc";
	public static final String COLUMN_COL4_4 = "to_loc";
	public static final String COLUMN_COL5_4 = "mode";
	public static final String COLUMN_COL6_4 = "amount";
	
	public static final String TABLE_NAME_5 = "othexp";
	public static final String COLUMN_ID_5 = "_id";
	public static final String COLUMN_COL1_5 = "jobid";
	public static final String COLUMN_COL2_5 = "dt";
	public static final String COLUMN_COL3_5 = "desc";
	public static final String COLUMN_COL4_5 = "amount";

	private static final String DATABASE_NAME = "gps.db";
	private static final int DATABASE_VERSION = 3;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_COL1
			+ " text not null," + COLUMN_COL2
			+ " text not null);";

	private static final String DATABASE_CREATE_1 = "create table "
			+ TABLE_NAME_1 + "( " + COLUMN_ID_1
			+ " integer primary key autoincrement, " + COLUMN_COL1_1
			+ " text not null," + COLUMN_COL2_1
			+ " text not null," + COLUMN_COL3_1
			+ " text not null," + COLUMN_COL4_1
			+ " integer not null);";

	private static final String DATABASE_CREATE_2 = "create table "
			+ TABLE_NAME_2 + "( " + COLUMN_ID_2
			+ " integer primary key autoincrement, " + COLUMN_COL1_2
			+ " text not null);";

	private static final String DATABASE_CREATE_3 = "create table "
			+ TABLE_NAME_3 + "( " + COLUMN_ID_3
			+ " integer primary key autoincrement, " + COLUMN_COL1_3
			+ " text not null, " + COLUMN_COL2_3 
			+ " text not null, " + COLUMN_COL3_3  
			+ " integer not null, " + COLUMN_COL4_3  
			+ " integer not null);";

	private static final String DATABASE_CREATE_4 = "create table "
			+ TABLE_NAME_4 + "( " + COLUMN_ID_4
			+ " integer primary key autoincrement, " + COLUMN_COL1_4
			+ " text not null," + COLUMN_COL2_4
			+ " text not null," + COLUMN_COL3_4
			+ " text not null," + COLUMN_COL4_4
			+ " text not null," + COLUMN_COL5_4
			+ " text not null," + COLUMN_COL6_4
			+ " integer not null);";

	private static final String DATABASE_CREATE_5 = "create table "
			+ TABLE_NAME_5 + "( " + COLUMN_ID_5
			+ " integer primary key autoincrement, " + COLUMN_COL1_5
			+ " text not null," + COLUMN_COL2_5
			+ " text not null," + COLUMN_COL3_5
			+ " text not null," + COLUMN_COL4_5
			+ " integer not null);";
	
	public MySQLiteHelper(Context context) {//Environment.getExternalStorageDirectory()+ File.separator +
		super(context,  DATABASE_NAME, null, DATABASE_VERSION);
	}

	//@Override
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		//database.openOrCreateDatabase("/sdcard/gps.db", null);
		database.execSQL(DATABASE_CREATE);
		database.execSQL(DATABASE_CREATE_1);
		database.execSQL(DATABASE_CREATE_2);
		database.execSQL(DATABASE_CREATE_3);
		database.execSQL(DATABASE_CREATE_4);
		database.execSQL(DATABASE_CREATE_5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_4);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_5);
		onCreate(db);
	}

}
