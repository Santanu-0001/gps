package com.gps_3;

import java.util.ArrayList;
import java.util.List;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class travelDataSource {
	public static SQLiteDatabase db;
	private MySQLiteHelper dbh;
	public static Context ct=null;
	private String[] allColumns_travel = { MySQLiteHelper.COLUMN_ID_3,
			MySQLiteHelper.COLUMN_COL1_3,MySQLiteHelper.COLUMN_COL2_3,MySQLiteHelper.COLUMN_COL3_3,MySQLiteHelper.COLUMN_COL4_3};
	
	private String[] allColumns_transport = { MySQLiteHelper.COLUMN_ID_4,
			MySQLiteHelper.COLUMN_COL1_4,MySQLiteHelper.COLUMN_COL2_4,MySQLiteHelper.COLUMN_COL3_4,MySQLiteHelper.COLUMN_COL4_4,MySQLiteHelper.COLUMN_COL5_4,MySQLiteHelper.COLUMN_COL6_4};
	
	private String[] allColumns_otherexp = { MySQLiteHelper.COLUMN_ID_5,
			MySQLiteHelper.COLUMN_COL1_5,MySQLiteHelper.COLUMN_COL2_5,MySQLiteHelper.COLUMN_COL3_5,MySQLiteHelper.COLUMN_COL4_5};

	public travelDataSource(Context cnt) {
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
	public void travel_insertValue(String jobid,String dt,int fooding,int lodging) 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1_3, jobid);
		values.put(MySQLiteHelper.COLUMN_COL2_3, dt);
		values.put(MySQLiteHelper.COLUMN_COL3_3, fooding);
		values.put(MySQLiteHelper.COLUMN_COL4_3, lodging);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME_3, null,
				values);
	}

	public void transport_insertValue(String jobid,String dt,String from, String to,String mode,int amount) 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1_4, jobid);
		values.put(MySQLiteHelper.COLUMN_COL2_4, dt);
		values.put(MySQLiteHelper.COLUMN_COL3_4, from);
		values.put(MySQLiteHelper.COLUMN_COL4_4, to);
		values.put(MySQLiteHelper.COLUMN_COL5_4, mode);
		values.put(MySQLiteHelper.COLUMN_COL6_4, amount);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME_4, null,
				values);
	}

	public void otherexp_insertValue(String jobid,String dt,String desc,int amount) 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1_5, jobid);
		values.put(MySQLiteHelper.COLUMN_COL2_5, dt);
		values.put(MySQLiteHelper.COLUMN_COL3_5, desc);
		values.put(MySQLiteHelper.COLUMN_COL4_5, amount);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME_5, null,
				values);
	}

	public List<String> travel_getAllValue(String str1)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
				allColumns_travel, MySQLiteHelper.COLUMN_COL1_3 + " = ?"  , new String[] {str1}, null, null, null);

//		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
//				allColumns_travel, null, null, null, null, null);

		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">" + cursor.getString(2) + "|Food:" + cursor.getInt(3) + "|Lodg.:" + cursor.getInt(4) + "|");
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
	}

	public Cursor travel_getAllValueC(String str1)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
				allColumns_travel, MySQLiteHelper.COLUMN_COL1_3 + " = ?"  , new String[] {str1}, null, null, null);

//		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
//				allColumns_travel, null, null, null, null, null);

		return cursor;
	}

	public Cursor transport_getAllValueC(String str1)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_4,
				allColumns_transport, MySQLiteHelper.COLUMN_COL1_4 + " = ?"  , new String[] {str1}, null, null, null);

		return cursor;
	}
	
	public Cursor otherexp_getAllValueC(String str1)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_5,
				allColumns_otherexp, MySQLiteHelper.COLUMN_COL1_5 + " = ?"  , new String[] {str1}, null, null, null);

		return cursor;
	}

	
	public List<String> travel_getValue(String str)
	{
		List<String> lst = new ArrayList<String>();
//		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
//				allColumns_travel, null, null, null, null, null);
//		cursor.moveToFirst();
		Cursor cursor = db.rawQuery("select * from travel where jobid = ?", new String[] { str });
		cursor.moveToFirst();

		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">" + cursor.getString(2) + "|Food:" + cursor.getInt(3) + "|Lodg.:" + cursor.getInt(4) + "|" );
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
	}

	public List<String> transport_getValue(String str,String str1)
	{
		List<String> lst = new ArrayList<String>();
//		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
//				allColumns_travel, null, null, null, null, null);
//		cursor.moveToFirst();
		Cursor cursor = db.rawQuery("select * from transport where jobid = ? and dt=?", new String[] { str,str1 });
		cursor.moveToFirst();

		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">" + cursor.getString(3) + "-" + cursor.getString(4) + ":" + cursor.getInt(6) + "(" + cursor.getString(5) + ")" );
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
	}

	public List<String> otherexp_getValue(String str,String str1)
	{
		List<String> lst = new ArrayList<String>();
//		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
//				allColumns_travel, null, null, null, null, null);
//		cursor.moveToFirst();
		Cursor cursor = db.rawQuery("select * from othexp where jobid = ? and dt=?", new String[] { str,str1 });
		cursor.moveToFirst();

		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">" + cursor.getString(3) + ":" + cursor.getInt(4) + "|");
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
	}

	
	public List<String> transport_getAllValue(String str1, String str2)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_4,
				allColumns_transport,  MySQLiteHelper.COLUMN_COL2_4 + " =? and jobid=?"  , new String[] {str1,str2}, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">" + cursor.getString(3) + "-" + cursor.getString(4) + ":" + cursor.getInt(6) + "(" + cursor.getString(5) + ")" );
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
	}


	
	public List<String> otherexp_getAllValue(String str1, String str2)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_5,
				allColumns_otherexp, MySQLiteHelper.COLUMN_COL2_5 + " =? and jobid=?"  , new String[] {str1,str2}, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">" + cursor.getString(3) + ":" + cursor.getInt(4) + "|" );
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
	}

	public String travle_showLastValue()
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_3,
				allColumns_travel, null, null, null, null, null);
		cursor.moveToLast();
		return cursor.getInt(0) + ">" + cursor.getString(2) + "|Food:" + cursor.getInt(3) + "|Lodg.:" + cursor.getInt(4) + "|";
	}

	public String transport_showLastValue()
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_4,
				allColumns_transport, null, null, null, null, null);
		cursor.moveToLast();
		return cursor.getInt(0) + ">" + cursor.getString(3) + "-" + cursor.getString(4) + ":" + cursor.getInt(6) + "(" + cursor.getString(5) + ")" ;
	}

	public String otherexp_showLastValue()
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_5,
				allColumns_otherexp, null, null, null, null, null);
		cursor.moveToLast();
		return cursor.getInt(0) + ">" + cursor.getString(3) + ":" + cursor.getInt(4) + "|" ;
	}

	
	public static Boolean travel_isDuplicate(String str,String str1)
	{
		Cursor cursor = db.rawQuery("select * from travel where jobid = ? and dt = ?", new String[] { str,str1 });
		cursor.moveToFirst();
		if(cursor.getCount()>0)
			return true;
		else
			return false;
	}

	public static Cursor travel_showJobDet(String str)
	{
		Cursor cursor = db.rawQuery("select * from travel where _id = ?", new String[] { str });
		cursor.moveToFirst();
		return cursor;
	}

	public static Cursor transport_showJobDet(String str)
	{
		Cursor cursor = db.rawQuery("select * from transport where _id = ?", new String[] { str });
		cursor.moveToFirst();
		return cursor;
	}

	public static Cursor otherexp_showJobDet(String str)
	{
		Cursor cursor = db.rawQuery("select * from othexp where _id = ?", new String[] { str });
		cursor.moveToFirst();
		return cursor;
	}

	
	public void travel_update(String id,int i1,int i2) 
	{
		ContentValues cv=new ContentValues();
		cv.put(MySQLiteHelper.COLUMN_COL3_3, i1);
		cv.put(MySQLiteHelper.COLUMN_COL4_3, i2);
		
		long insertId = db.update(MySQLiteHelper.TABLE_NAME_3, cv, MySQLiteHelper.COLUMN_ID_3 + " = " + id, null);
				//delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}

	public void transport_update(String id,String str1,String str2,int i1,String str3) 
	{
		ContentValues cv=new ContentValues();
		cv.put(MySQLiteHelper.COLUMN_COL3_4, str1);
		cv.put(MySQLiteHelper.COLUMN_COL4_4, str2);
		cv.put(MySQLiteHelper.COLUMN_COL5_4, str3);
		cv.put(MySQLiteHelper.COLUMN_COL6_4, i1);
		
		long insertId = db.update(MySQLiteHelper.TABLE_NAME_4, cv, MySQLiteHelper.COLUMN_ID_4 + " = " + id, null);
				//delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}

	public void otherexp_update(String id,String str1,int i1) 
	{
		ContentValues cv=new ContentValues();
		cv.put(MySQLiteHelper.COLUMN_COL3_5, str1);
		cv.put(MySQLiteHelper.COLUMN_COL4_5, i1);
		
		long insertId = db.update(MySQLiteHelper.TABLE_NAME_5, cv, MySQLiteHelper.COLUMN_ID_5 + " = " + id, null);
				//delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}

	
}
