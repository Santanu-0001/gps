package com.gps_3;



import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.Toast;

public class gpsDataSource {
	private SQLiteDatabase db;
	private MySQLiteHelper dbh;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_COL1,MySQLiteHelper.COLUMN_COL2};
	
	public gpsDataSource(Context cnt) {
		// TODO Auto-generated constructor stub
		dbh=new MySQLiteHelper(cnt);
	}
	public void open() throws SQLException
	{
		db=dbh.getWritableDatabase();
	}
	public void close()
	{
		dbh.close();
	}
	public void insertValue(String str,String str1) 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1, str);
		values.put(MySQLiteHelper.COLUMN_COL2, str1);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME, null,
				values);
	}
	public void deleteValue(String str) 
	{
		long insertId = db.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}
	public String showValue(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToLast();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}
	public record getRecord(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToLast();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		if(cursor.getCount()>0)
		{
		record r1=new record();
		r1.setId(cursor.getInt(0));
		r1.setLatitude(cursor.getString(1));
		r1.setLongitude(cursor.getString(2));
		return r1;
		}
		else
			return null;
		
	}
	public List<String> showAllValue(Context cnt)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/");
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}

}
