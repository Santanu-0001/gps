package com.gps_3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class alertDataSource {
	public static SQLiteDatabase db;
	private MySQLiteHelper dbh;
	public static Context ct=null;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID_1,
			MySQLiteHelper.COLUMN_COL1_1,MySQLiteHelper.COLUMN_COL2_1,MySQLiteHelper.COLUMN_COL3_1,MySQLiteHelper.COLUMN_COL4_1};
	
	private String[] allColumns_chkin = { MySQLiteHelper.COLUMN_ID_2,
			MySQLiteHelper.COLUMN_COL1_2};
	
	public alertDataSource(Context cnt) {
		// TODO Auto-generated constructor stub
		ct=cnt;
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
	public void insertValue(String str,String str1,String dt,int i) 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1_1, str);
		values.put(MySQLiteHelper.COLUMN_COL2_1, str1);
		values.put(MySQLiteHelper.COLUMN_COL3_1, dt);
		values.put(MySQLiteHelper.COLUMN_COL4_1, i);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME_1, null,
				values);
	}
	
	public static void chkin_insertValue(String str) 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1_2, str);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME_2, null,
				values);
	}

	public void deleteValue(String str) 
	{
		long insertId = db.delete(MySQLiteHelper.TABLE_NAME_1, MySQLiteHelper.COLUMN_ID_1 + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}
	public static void deleteJobid(String str) 
	{
		long insertId = db.delete(MySQLiteHelper.TABLE_NAME_1, MySQLiteHelper.COLUMN_COL1_1 + " = '" + str + "'", null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}
	
	public static void chkin_deleteJobid(String str) 
	{
		long insertId = db.delete(MySQLiteHelper.TABLE_NAME_2, MySQLiteHelper.COLUMN_COL1_2 + " = '" + str + "'", null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}
	
	public String showValue(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				allColumns, null, null, null, null, null);
		cursor.moveToLast();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "/" + cursor.getInt(4) + "/";
		//return "01/abc/efg/1";
		
	}

	public static void updateAcc(String jid)
	{
		//acc.show_alert("acc");
		Cursor cursor = db.rawQuery("select * from popup_alert where jobid = ?", new String[] { jid });//.query(MySQLiteHelper.TABLE_NAME_1,allColumns, MySQLiteHelper.COLUMN_COL1_1 + " = " + str, null, null, null, null);
		cursor.moveToFirst();
		ContentValues cv = new ContentValues();
		//cv.put(MySQLiteHelper.COLUMN_COL1_1, cursor.getString(1));
		//cv.put(MySQLiteHelper.COLUMN_COL2_1, "A");
		//cv.put(MySQLiteHelper.COLUMN_COL3_1, "A");
		cv.put(MySQLiteHelper.COLUMN_COL4_1, Integer.parseInt("1"));
		
		long insertId = db.update(MySQLiteHelper.TABLE_NAME_1, cv, MySQLiteHelper.COLUMN_ID_1 + " = " + cursor.getInt(0) , null);
		//Toast.makeText(ct, "acc", 3000).show();
		//acc.show_alert("acc");
	}

	
	public static Cursor showJobDet(String str)
	{
		Cursor cursor = db.rawQuery("select * from popup_alert where jobid = ?", new String[] { str });//.query(MySQLiteHelper.TABLE_NAME_1,allColumns, MySQLiteHelper.COLUMN_COL1_1 + " = " + str, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "/" + cursor.getInt(4) + "/";
		//return "01/abc/efg/1";
		return cursor;
	}
	
	public static Cursor checkin_showJobDet(String str)
	{
		Cursor cursor = db.rawQuery("select * from checkin where jobid = ?", new String[] { str });//.query(MySQLiteHelper.TABLE_NAME_1,allColumns, MySQLiteHelper.COLUMN_COL1_1 + " = " + str, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "/" + cursor.getInt(4) + "/";
		//return "01/abc/efg/1";
		return cursor;
	}
	
	public Cursor checkin_showJobDet()
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_2,
				allColumns_chkin, null, null, null, null, null);//db.rawQuery("select * from checkin");// where jobid = ?", new String[] { str });//.query(MySQLiteHelper.TABLE_NAME_1,allColumns, MySQLiteHelper.COLUMN_COL1_1 + " = " + str, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "/" + cursor.getInt(4) + "/";
		//return "01/abc/efg/1";
		return cursor;
	}
	
	public alertRecord getRecord(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				allColumns, null, null, null, null, null);
		cursor.moveToLast();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		if(cursor.getCount()>0)
		{
		alertRecord r1=new alertRecord();
		r1.setId(cursor.getInt(0));
		r1.setJobid(cursor.getString(1));
		r1.setMessage(cursor.getString(2));
		r1.setDt(cursor.getString(3));
		r1.setAcc(cursor.getInt(4));
		return r1;
		}
		else
			return null;
		
	}
	public List<String> showAllValue(Context cnt)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + "/" + cursor.getInt(4) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "/" );
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}

	public List<String> showAllJobid(Context cnt)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				allColumns, MySQLiteHelper.COLUMN_COL4_1 + " = 0 " , null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getString(1));
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}


	public List<String> showAllAccJobid(Context cnt)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				allColumns, MySQLiteHelper.COLUMN_COL4_1 + " = 1 " , null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getString(1));
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}

}
