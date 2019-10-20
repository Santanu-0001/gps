package com.gps_3;




import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class inout extends ListActivity implements OnClickListener {

	/** Called when the activity is first created. */
	private Button b1;
	private Button b2;
	public static Spinner s1;
	public static TextView tv;
	public static TextView tv1;
	public static alertDataSource alrt;
	private boolean flag=false;
	public static String jid=null;
	public static Context cnt=null;
	public static ArrayAdapter<String> adapter=null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout);
        b1=(Button)findViewById(R.id.add2list);
        b1.setOnClickListener(this);
        tv=(TextView)findViewById(R.id.TextView1);
        tv1=(TextView)findViewById(R.id.TextView2);
        b2=(Button)findViewById(R.id.rmlist);
        b2.setOnClickListener(this);
        
        //String[] Items = {"Alarm","Office","Meeting","Party","Lunch","Breakfast","Supper","Home","Private","Outdoor","Family","Friends","Others"};

        alrt=new alertDataSource(this);
        alrt.open();
        cnt=this;

        List<String> lst =alrt.showAllAccJobid(this);
        
    s1=(Spinner)findViewById(R.id.spinner1);
    adapter  = new ArrayAdapter<String>(
            this,android.R.layout.simple_spinner_item,alrt.showAllAccJobid(this));
    adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
    s1.setAdapter(adapter);
    s1.setOnItemSelectedListener(new MyOnItemSelectedListener());
    
    //Spinner s1=new Spinner(this);

    Bundle extras = getIntent().getExtras();
    
    if(extras != null){
        String jobid=extras.getString("item_id");
        Toast.makeText(getApplicationContext(), jobid, 3000).show();
        int pos=lst.indexOf(jobid);
        s1.setSelection(pos);
        //Toast.makeText(getApplicationContext(), "s" + pos, 3000).show();
    }

    
    
}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          inout.jid=parent.getItemAtPosition(pos).toString();
          //Toast.makeText(getApplicationContext(), acc.jid, 3000).show();
          Cursor cursor=null;
          try {
        	  cursor=alrt.showJobDet(inout.jid);
              //if(cursor.isAfterLast())//!cursor.isBeforeFirst())
            	  tv.setText(cursor.getString(3));
            	  tv1.setText(cursor.getString(2));

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.getMessage(), 3000).show();
		}
          
        }
    	@Override
        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
	}
	
	@Override
	public void onClick(View src) {
		// TODO Auto-generated method stub
		//send s1;
		switch (src.getId()) {
		case R.id.add2list:
			//Toast.makeText(this, "sample", 3000).show();
			try {
				//adapter  = new ArrayAdapter<String>(
			    //        this,android.R.layout.simple_spinner_item,alrt.showAllJobid(this));
			    //adapter.setDropDownViewResource(android.R.layout.preference_category);
			    /*
				if(!flag)
			    {
				adapter.add("santanu");
			    s1.setAdapter(adapter);
			    flag=true;
			    }
			    else
			    {
			    	adapter.remove("santanu");
				    s1.setAdapter(adapter);
				    flag=false;
			    }
			    */	
				sendAcc s2=null;
				Thread th=null;
				String str;
		    	str=inout.jid;//jobid.getText().toString();
		    	//Cursor c=alrt.checkin_showJobDet(str);
		    	Cursor c=alrt.checkin_showJobDet();
		    	
		    	if(c.getCount()==0) 
//		    	if((!str.trim().equals("")))// & (c.getCount()>0))
		    	{
		    		try {
		    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//yyyyMMdd_HHmmss");
		    			String curentDateandTime = sdf.format(new Date(System.currentTimeMillis()));
		    			if(gpsService.lati!=0 & gpsService.longi!=0)
		    			{
		    			s2=new sendAcc(this,gpsService.iemi+"/"+str+"/<Checkin>/"+gpsService.lati+"/"+gpsService.longi+"/"+curentDateandTime,getString(R.string.ip),"Check In");
						th=new Thread(s2);
						th.start();
						
						alrt.chkin_insertValue(str);

		    			}
		    			else
		    				Toast.makeText(getApplicationContext(), "GPS not available", 3000).show();
				    	
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(this, "error", 3000).show();
					}
		    	}
		    	else
		    		Toast.makeText(this, "Already checked in at " + c.getString(1), 3000).show();
		    	
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, e.getLocalizedMessage(), 3000).show();
			}
			
			break;

		case R.id.rmlist:
			
			try {
				sendAcc s2=null;
				Thread th=null;
				String str;
		    	str=inout.jid;//jobid.getText().toString();
		    	Cursor c=alrt.checkin_showJobDet(str);
		    	//if(alrt.checkin_showJobDet()==0)
		    	//{
		    	if(c.getCount()>0) //if(!str.trim().equals(""))
		    	{
		    		try {
		    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//yyyyMMdd_HHmmss");
		    			String curentDateandTime = sdf.format(new Date(System.currentTimeMillis()));
		    			
		    			if(gpsService.lati!=0 & gpsService.longi!=0)
		    			{
		    			s2=new sendAcc(this,gpsService.iemi+"/"+str+"/<Checkout>/"+gpsService.lati+"/"+gpsService.longi+"/"+curentDateandTime,getString(R.string.ip),"Check Out");
						th=new Thread(s2);
						th.start();
						//alrt.chkin_deleteJobid(str);
		    			}
		    			else
		    				Toast.makeText(getApplicationContext(), "GPS not available", 3000).show();
				    	
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(this, "error", 3000).show();
					}
		    	}
		    	else
		    		Toast.makeText(this, "Not Checked In", 3000).show();
		    	//}
		    	//else
		    		//Toast.makeText(this, "Not Checked In", 3000).show();
		    	
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, e.getLocalizedMessage(), 3000).show();
			}
			
			//Toast.makeText(this, "sample", 3000).show();
			/*try {
				//adapter  = new ArrayAdapter<String>(
			    //        this,android.R.layout.simple_spinner_item,alrt.showAllJobid(this));
			    //adapter.setDropDownViewResource(android.R.layout.preference_category);
			    try {
			    	alrt.deleteJobid(acc.jid);
					
			    	//adapter.remove(acc.jid);
				    
				    adapter  = new ArrayAdapter<String>(
				            this,android.R.layout.simple_spinner_item,alrt.showAllJobid(this));
				    adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				    s1.setAdapter(adapter);
				    Toast.makeText(this, "Job Deleted", 3000).show();
				    
	
				} catch (Exception e) {
					Toast.makeText(this, e.getMessage(), 3000).show();
					// TODO: handle exception
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, e.getLocalizedMessage(), 3000).show();
			}
			*/
			break;


		default:
			break;
		}
	}

	public static void refreshJob()  throws IOException
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			    adapter  = new ArrayAdapter<String>(
			            cnt,android.R.layout.simple_spinner_item,alrt.showAllJobid(cnt));
			    adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			    s1.setAdapter(adapter);
    		//vi.vibrate(100);
			}
			});
	}

	
	public static void show_alert(final String str)  throws IOException
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
	@Override
	public void onBackPressed() 
	{
		//Toast.makeText(this, "close", Toast.LENGTH_LONG).show();
		super.onBackPressed();
	}
	/*
	public boolean onKeyDown(int keyCode, KeyEvent event)  {

	       if (keyCode == KeyEvent.KEYCODE_BACK)  //Override Keyback to do nothing in this case.
	       {
	           Toast.makeText(this, "close", Toast.LENGTH_LONG).show();
	    	   return super.onKeyDown(keyCode, event);
	       }
	       return super.onKeyDown(keyCode, event);  //-->All others key will work as usual
	   }
*/
}
