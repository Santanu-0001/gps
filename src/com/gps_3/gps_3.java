package com.gps_3;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;



import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class gps_3 extends ListActivity implements View.OnClickListener {

	/** Called when the activity is first created. */

	Button buttonStart;
	Button buttonStop;
	Button b1;
	Button b2;
	Intent intt;
	private gpsDataSource gps;
	private alertDataSource alrt;
	public static Context cnt=null;
	EditText jobid=null;
	
	//private static final int PROGRESS = 0x1;

    //private ProgressBar mProgress;
    //private int mProgressStatus = 0;

    //private Handler mHandler = new Handler();

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(this);
        
        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(this);

        jobid= (EditText)findViewById(R.id.editText1);
        
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        
        intt=new Intent(this, gpsService.class);
        try{
        gps=new gpsDataSource(this);
		gps.open();
        }catch (Exception e) {
			// TODO: handle exception
    		Toast.makeText(this,e.getMessage(), 30000).show();
		}
        
		alrt=new alertDataSource(this);
		alrt.open();
		
		List<String> vals = gps.showAllValue(this);//new ArrayList<String>();
		//List<String> vals = alrt.showAllValue(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.preference_category, vals);
		setListAdapter(adapter);
		
		//mProgress = (ProgressBar) findViewById(R.id.progressBar1);
    }

	@Override
	public void onClick(View src) {
		// TODO Auto-generated method stub
		//send s1;
		sendAcc s1 = null;
		Thread th;
		switch (src.getId()) {
	    case R.id.buttonStart:
	    	try{
	    	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
	        wl.acquire();
	    	}
	    	catch(Exception e)
	    	{
	    		Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	    	}
//	        wl.release();

	    	//Intent serviceIntent1 = new Intent();
			//serviceIntent1.setAction("com.gpsService");
			//startService(serviceIntent1);
	    	startService(intt);
	      break;
	    case R.id.buttonStop:
	    	//Intent serviceIntent2 = new Intent();
			//serviceIntent2.setAction("com.gpsService");
			//stopService(serviceIntent2);
			stopService(intt);//new Intent(this, gpsService.class)
	      break;
	    case R.id.button1:
	    	/*
	    	s1=new send(this);
			th=new Thread(s1);
			th.start();*/

	    	String str;
	    	str=jobid.getText().toString();
	    	if(!str.trim().equals(""))
	    	{
	    	s1=new sendAcc(this,str,getString(R.string.ip),"Acceptance");
			th=new Thread(s1);
			th.start();
	    	}
	    	else
	    		Toast.makeText(this, "Blank Field", 3000).show();
			
	    	//Toast.makeText(this, alrt.showValue(this), 3000).show();
	    	
	    	//alrt.insertValue("as", "str", 10);
	    	
	    	//alrt.deleteValue("1");
	    	
	    	//gps.insertValue("abc","SDcS");
    		break;
	    case R.id.button2:
	    	//alrt.insertValue("as", "str", 10);
/*	    	try{
	    	String rec=alrt.showValue(this);
	    	if(rec.length()>0)
	    		Toast.makeText(this,rec, 3000).show();
	    	else
	    		Toast.makeText(this,"no rec", 3000).show();
	    	}catch (Exception e) {
				// TODO: handle exception
	    		Toast.makeText(this,e.getMessage(), 3000).show();
			}
	    	//cal_event("15/03/2012 15:30:00", this);
*/	    	
	    	gpsService.sendMsg("Hello");
	    	break;
	    	
	    }
		
	}
/*	
	public static void show_alert(final String str)
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			
			Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();
    		//vi.vibrate(100);
			}
			});
	}
*/	
	private static void cal_event(String str,Context ct)
	{
		Uri uri = Uri.parse("content://com.android.calendar/events");
    	ContentResolver cr = ct.getContentResolver();

    	ContentValues values = new ContentValues();
    	values.put("eventTimezone", "EST");
    	values.put("calendar_id", 1); // query content://calendar/calendars
    	//for more
    	values.put("title", "Test Event");
    	values.put("allDay", 0);
    	long dtstart=dt(str);//1315087200000L;;
    	long dtend=dt(str);//1315087200000L;;
		values.put("dtstart", dtstart); // long (start date in ms)
		values.put("dtend", dtend);     // long (end date in ms)
    	values.put("description", "Santanu");
    	values.put("eventLocation", "India");
    	values.put("transparency", 0);
    	values.put("visibility", 0);
    	values.put("hasAlarm", 1);

    	try {
    		cr.insert(uri, values);
		} catch (Exception e) {
			Toast.makeText(ct, e.getMessage(), 3000).show();
			// TODO: handle exception
		} 

	}
	
	private static long dt(String str)
	{
		System.out.println(str);
		long days,hour,min,sec;
		int day,month,year;
		int i,j;
		int yr,lpyr;
		int[] mn_day={31,28,31,30,31,30,31,31,30,31,30,31};
		
		i=j=0;
		j=str.indexOf("/",i);
		day=Integer.parseInt(str.substring(i, j));
		i=++j;
		j=str.indexOf("/",i);
		month=Integer.parseInt(str.substring(i, j));
		year=Integer.parseInt(str.substring(++j, j+4));
		
		i=j=0;
		j=str.indexOf(":",i);
		hour=Integer.parseInt(str.substring(j-2, j));
		min=Integer.parseInt(str.substring(j+1, j+3));
		System.out.println(min);
		
		yr=year-1970;
		if((year%4==0) & month>2)
			lpyr=(yr/4)+1;
		else
			lpyr=(yr/4);
		
		for(i=0,days=0;i<month-1;i++)
			days=days+mn_day[i];
		
		
		days=(days+day)+lpyr+(yr*365)-1;
		hour=hour+days*24-5;
		min=min+hour*60-30;
		sec=min*60;
		//System.out.println(sec);
		return sec*1000;
	}

}