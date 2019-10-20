package com.gps_3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class gpsService extends Service {
	private recv r1;
	private sendAcc r2=null;
	private send s1=null;
	private Thread th=null;
	private Thread th1=null;
	
	private int data=0;
	private int reso=0;
	public static LocationListener mlocListener;
	public static LocationManager lm;
	public static Context cnt=null;
	public static Vibrator vi=null;
	public static NotificationManager mNotificationManager=null;
	public static String iemi=null;	
	private static int HELLO_ID = 1;
	private gpsDataSource gps;
	public static alertDataSource alrt;
	public static double lati=0;
	public static double longi=0;
	 private NotificationManager mNM;
	 private static Timer timer = new Timer(); 
/*
	private recv r1;
//	private Handler handler;
	private Timer tm; 
	private Runnable doSomething;
//	private int temp_int=01;
	private int data=0;
*/
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
//***************Temporary code ******************
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "ABC";

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.message_alert, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, acc.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, "ABC",
                       text, contentIntent);

        // Send the notification.
        mNM.notify(++HELLO_ID, notification);
    }
  //***************Temporary code ******************
    
	@Override
	public void onCreate() {
		super.onCreate();
		//***************Temporary code ******************
		//mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		//showNotification();
		//***************Temporary code ******************
		Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
		
		cnt=getApplicationContext();
		//toggleGPS(true);
		
		/*try
		{
			show_noti("Service Created");
		}
		catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		*/
		
		lati=0;
		longi=0;
		vi=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        String ns = Context.NOTIFICATION_SERVICE;
		mNotificationManager = (NotificationManager) getSystemService(ns);
		gps=new gpsDataSource(this);
		gps.open();
		
		alrt=new alertDataSource(this);
		alrt.open();
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		iemi = telephonyManager.getDeviceId().toString();

		s1=new send(this,getString(R.string.ip));
		th1=new Thread(s1);
		th1.start();
		
		
		timer.scheduleAtFixedRate(new mainTask(), 0, 5000);
//		try {
//			Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, true);	
//		} catch (Exception e) {
//			// TODO: handle exception
//			Toast.makeText(gpsService.this, e.getMessage(), 3000).show();
//		}
		
		OnInit();
        
		
		r1=new recv(this,getString(R.string.ip));
		th=new Thread(r1);
		th.start();
		//cnt=this;
		//cnt=getApplicationContext();

		
		
		//show_alert();
        /*r1=new recv();
        
        tm=new Timer();
        tm.schedule(new TimerTask() {
        	@Override
        	public void run() {
        	timerMethod();
        	}
        	}, 0, 1000);
        doSomething = new Runnable() {
        	public void run() {
        		//int res=10;//r1.data.compareTo(r1.data);
        		if(data!=r1.flag)
        		{
        			//data=r1.flag;
        			//b1.setText("santanu" + r1.flag);}
        	}};*/

	}
	
	
	private class mainTask extends TimerTask
    { 
        public void run() 
        {
            toastHandler.sendEmptyMessage(0);
        }
    }    

//    public void onDestroy() 
//    {
//          super.onDestroy();
//          Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
//    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
        	
        	//if(canToggleGPS())
        	//	toggleGPS(true);
        	
//        	boolean gpsStatus = locmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        	if (!gpsStatus) {
//        	    Settings.Secure.putString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED, "network,gps");
//        	}

        }
    };   
	
	
	
/*
    private void timerMethod()
    {
    this.runOnUiThread(doSomething);
    }
    */

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		//show_noti("Service Destroyed");
		//send2srv("Mobile Off");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		cnt=getApplicationContext();
		//try {
		//	show_noti("Service Started");
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		//}
		
		//LocationListener mlocListener = new MyLocationListener();

		
/*		******** Previous Start/Init *******
		OnInit();
        
		
		r1=new recv(this,getString(R.string.ip));
		th=new Thread(r1);
		th.start();
		//cnt=this;
		cnt=getApplicationContext();
*/
		
		/*r2=new sendAcc(this);
		th=new Thread(r2);
		th.start();*/
		//send2srv("Mobile On");
	}
	
	@Override
	public boolean onUnbind(Intent intent) 
	{
		//show_noti("Unbind");
		Toast.makeText(this, "Unbind", Toast.LENGTH_LONG).show();
		return true;
	};

	@Override
	public void onRebind(Intent intent) 
	{
		//show_noti("Rebind");
		Toast.makeText(this, "Rebind", Toast.LENGTH_LONG).show();
		//return true;
	};
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		//show_noti("onConfigurationChanged");
		
		//Toast.makeText(this, "onConfigurationChanged", Toast.LENGTH_LONG).show();
		super.onConfigurationChanged(newConfig);
	};
	
	
	
	public void OnInit(){
		try{
			
			//Looper.prepare();
    		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            String provider = null;

            if ( lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                provider = LocationManager.GPS_PROVIDER ;
                Toast.makeText(this, "GPS available", 3000).show();
                //show_noti("GPS available");
            } 
            else 
            {
//            	Settings.Secure.putString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED, "network,gps");
            	Toast.makeText(this, "GPS not available", 3000).show();
            	send2srv("/Gps Disabled/");
            	//show_noti("GPS not available");
            }
            
            if(provider != null) {
        		mlocListener = new MyLocationListener();
        		
        		lm.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
                //lm.requestLocationUpdates(provider, 1000, 100,this);
        		
        		Toast.makeText(this, "Search Started", 3000).show();
        		//show_noti("Search Started");
        		
        		Vibrator v1=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        		v1.vibrate(100);
            }
            //Looper.loop();
    		}
    		catch (Exception e) {
    			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
				// TODO: handle exception
			}
	}
	
	public static void update(final int data,final int reso)//,Context c
	{
		//Toast.makeText(cnt, "s" + data + "/" + reso, Toast.LENGTH_LONG).show();
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
					lm.requestLocationUpdates( LocationManager.GPS_PROVIDER, data, reso, mlocListener);
					Toast.makeText(cnt, "Setting: " + data + " / " + reso, Toast.LENGTH_LONG).show();
			}
			});
		
	}
	
    public static void show_noti(String str) 
    {
    	int icon = R.drawable.message_alert;
		CharSequence tickerText = "Message from Server";
		long when = System.currentTimeMillis();

		
		String msg=null;
		String jobid=null;
		int i=0;
		Notification notification = new Notification(icon, tickerText, when);
		
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		
		Context context = cnt;//getApplicationContext();
		CharSequence contentTitle = "Message from Server";
		CharSequence contentText = str;
		
		Intent notificationIntent = new Intent(context, acc.class);
		int i1=++HELLO_ID;
		
		PendingIntent contentIntent = PendingIntent.getActivity(cnt, i1, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		mNotificationManager.cancel(i1);
		mNotificationManager.notify(i1, notification);
    }

	
	
	
	
    public static void show_note(String str,String dt) throws IOException
    {
    	
		

    	int icon = R.drawable.message_alert;
		CharSequence tickerText = "Message from Server";
		long when = System.currentTimeMillis();

		
		String msg=null;
		String jobid=null;
		int i=0;
		i=str.indexOf("/", 0);
		jobid=str.substring(0, i);
		msg=str.substring(i+1);

		
		
		Notification notification = new Notification(icon, tickerText, when);
		
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		
		Context context = cnt;//getApplicationContext();
		CharSequence contentTitle = "Message from Server";
		CharSequence contentText = str;
		
		//Intent notificationIntent = new Intent(Intent.ACTION_MAIN);
		//notificationIntent.setClass(cnt, acc.class);

		Intent notificationIntent = new Intent(context, acc.class);
		notificationIntent.putExtra("item_id", jobid); 
	    
		//Intent notificationIntent = new Intent(cnt, acc.class);
		
		//Intent notificationIntent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://www.android.com"));
		
		int i1=++HELLO_ID;
		
		PendingIntent contentIntent = PendingIntent.getActivity(cnt, i1, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		mNotificationManager.cancel(i1);
		mNotificationManager.notify(i1, notification);
		
		
		
		
		
		//Toast.makeText(cnt, "Hello Santanu ...", Toast.LENGTH_LONG).show();
		
		//show_alert(jobid);
		show_alert(jobid + "/" + msg);
		//gps.insertValue(str, str1)
		
		alrt.insertValue(jobid, msg.trim(),dt, 0);
		
		//calender entry
		//cal_event(dt, str);
		

    }
    
    public static void cal_event(String str,String msg)
    {
    	Uri uri = Uri.parse("content://com.android.calendar/events");
    	ContentResolver cr = cnt.getContentResolver();

    	ContentValues values = new ContentValues();
    	values.put("eventTimezone", "EST");
    	values.put("calendar_id", 1); // query content://calendar/calendars
    	//for more
    	values.put("title", "Job");
    	values.put("allDay", 0);
    	long dtstart=dt(str);//1315087200000L;;
    	long dtend=dt(str);//1315087200000L;;
		values.put("dtstart", dtstart); // long (start date in ms)
		values.put("dtend", dtend);     // long (end date in ms)
    	values.put("description", msg);
    	values.put("eventLocation", "India");
    	values.put("transparency", 0);
    	values.put("visibility", 0);
    	values.put("hasAlarm", 1);

    	try {
    		cr.insert(uri, values);
		} catch (Exception e) {
			Toast.makeText(cnt, e.getMessage(), 3000).show();
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

	private void toggleGPS(boolean enable) {
	    String provider = Settings.Secure.getString(getContentResolver(), 
	        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	    try {
			show_alert("toggle GPS start");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    if(provider.contains("gps") == enable) {
	        return; // the GPS is already in the requested state
	    }

	    final Intent poke = new Intent();
	    poke.setClassName("com.android.settings", 
	        "com.android.settings.widget.SettingsAppWidgetProvider");
	    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	    poke.setData(Uri.parse("custom:3"));
	    cnt.sendBroadcast(poke);
	    
	    
	    try {
			show_alert("toggle GPS end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean canToggleGPS() {
	    PackageManager pacman = getPackageManager();
	    PackageInfo pacInfo = null;

	    try {
	        pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
	    } catch (NameNotFoundException e) {
	        return false; //package not found
	    }

	    if(pacInfo != null){
	        for(ActivityInfo actInfo : pacInfo.receivers){
	            //test if recevier is exported. if so, we can toggle GPS.
	            if(actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported){
	                return true;
	            }
	        }
	    }

	    return false; //default
	}

	public static void show_alert(final String str)  throws IOException
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();
    		vi.vibrate(100);
			}
			});
	}
	
	
	public static void show_alert1(final String str)
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			//Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();
				
				AlertDialog.Builder alertbox = new AlertDialog.Builder(cnt);
		        alertbox.setMessage(str);
		        alertbox.setNeutralButton("Ok",null);//,new DialogInterface.OnClickListener() 
		        /*{
		        	public void onClick(DialogInterface arg0, int arg1) 
		        	{
		                Toast.makeText(cnt, "OK button clicked", Toast.LENGTH_LONG).show();
		            }
		        });*/

		        alertbox.show();

			}
			});
		

	}
	
	
	
	public void showLocation(LocationManager mlocManager)
    {
		double latitude=0;
		double longitude=0;
		
		Location lastKnownLocation=null;
    	
		lastKnownLocation = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(lastKnownLocation !=null)
		{
			latitude = lastKnownLocation.getLatitude();// Altitude();
			longitude=lastKnownLocation.getLongitude();
			Toast.makeText( getApplicationContext(),"Latitude:"+latitude+" Longitude:"+longitude,Toast.LENGTH_SHORT).show();
			//send2srv("Latitude:"+latitude+" Longitude:"+longitude);
			send2srv("/"+latitude+"/"+longitude+"/");
		}
		else
		{
			send2srv("Co-ordinate not found");
			Toast.makeText( getApplicationContext(),"Latitude: Not found",Toast.LENGTH_SHORT).show();
		}
		Vibrator v=(Vibrator)getSystemService(VIBRATOR_SERVICE);
		v.vibrate(100);
    }

	public void send2srv(String msg){
		try{
			String iemi;
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//			iemi="IEMI:"+telephonyManager.getDeviceId();
			iemi=telephonyManager.getDeviceId().toString();
            msg=iemi+msg;
            String messageStr="s";
    	    //String s1="santanu";
    	    int server_port = 10400;
    	    
    	    DatagramSocket s = new DatagramSocket(11100);//(10300);
    	    InetAddress local = InetAddress.getByName(getString(R.string.ip));//192.168.0.159//"180.151.96.155"//"192.168.1.114");//59.93.248.125
    	    int msg_length=msg.length();
    	    byte[] message = msg.getBytes();//messageStr.getBytes();
    	    //byte[] message2 = s1.getBytes();
    	    //byte[] message=new byte[message1.length+message2.length];
    	    //message = message1 + message2;
    	    DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
    	    s.send(p);
    	    s.close();
    		}
    		catch (Exception e) {
    			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
				// TODO: handle exception
			}
	
    }

	public static void sendMsg(String msg){
		try{
			String iemi;
			//TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//			iemi="IEMI:"+telephonyManager.getDeviceId();
			//iemi=telephonyManager.getDeviceId().toString();
            //msg=iemi+msg;
            String messageStr="s";
    	    //String s1="santanu";
    	    int server_port = 10400;
    	    
    	    DatagramSocket s = new DatagramSocket(10300);
    	    InetAddress local = InetAddress.getByName("180.151.96.155");//192.168.0.159//"180.151.96.155"//"192.168.1.114");//59.93.248.125
    	    int msg_length=msg.length();
    	    byte[] message = msg.getBytes();//messageStr.getBytes();
    	    //byte[] message2 = s1.getBytes();
    	    //byte[] message=new byte[message1.length+message2.length];
    	    //message = message1 + message2;
    	    DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
    	    s.send(p);
    	    s.close();
    		}
    		catch (Exception e) {
    			//Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
				// TODO: handle exception
			}
	
    }
	

	public void add2db(String lati,String longi)
	{
		gps.insertValue(lati,longi);
	}
	
	public class MyLocationListener implements LocationListener
    {
    //@Override
    public void onLocationChanged(Location loc)
    {
	    try{
    	lati=loc.getLatitude();
    	longi=loc.getLongitude();
//	    String Text =  "Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();
	    String Text =  "/" + loc.getLatitude() + "/" + loc.getLongitude()+ "/" ;
	    
	    //send2srv(Text);
	    add2db(Double.toString(lati), Double.toString(longi));

//    	lati=0;
//    	longi=0;

	    //add2db("/" + lati, "/" + loc.getLongitude());
	    
	    //Toast.makeText( getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
		
	    //Vibrator v=(Vibrator)getSystemService(VIBRATOR_SERVICE);
		//v.vibrate(100);
	    }
	    catch (Exception e) {
	    	Toast.makeText( getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
			// TODO: handle exception
		}
    }

    //@Override
    
    //@Override
    public void onProviderDisabled(String provider)
    {
    	//Toast.makeText( getApplicationContext(),"Hi! Gps Disabled",Toast.LENGTH_SHORT ).show();
    	send2srv("/Gps Disabled/");
    	
    	//toggleGPS(true);
    	
//    	try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			try {
//				show_alert(e.getMessage());
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
    	
    }
    //@Override
    public void onProviderEnabled(String provider)
    {
    	//Toast.makeText( getApplicationContext(),"Hi! Gps Enabled",Toast.LENGTH_SHORT).show();
    	send2srv("/Gps Enabled/");
    }
    //@Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    	//Toast.makeText( getApplicationContext(),"Hi! Gps Status Changed",Toast.LENGTH_SHORT).show();
    	send2srv("/Gps Status Changed/");
    	
//    	if( status == GpsStatus.GPS_EVENT_SATELLITE_STATUS){
//    		try {
//				show_alert("GPS fixed");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		//Toast.makeText(getApplicationContext(), "GPS fixed",3000).show();
//        }
    	
    }
    }/* End of Class MyLocationListener */
    
}
