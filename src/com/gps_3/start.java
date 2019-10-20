package com.gps_3;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.GpsStatus;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class start extends Activity implements OnClickListener {
	private Button b1;
	
	public static Context cnt=null;
	//public static boolean closeEnable=true;
	public static boolean closeEnable;
	private TextView tx=null;
	Intent intt;
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        cnt=this;
        closeEnable=true;
        tx=(TextView)findViewById(R.id.textIP);
        try {
			tx.setText(getLocalAddress().getHostAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //b1=(Button)findViewById(R.id.imageButton1);
        //b1.setOnClickListener(this);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mnu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		String app_ver=null;
		
	    switch (item.getItemId()) {
	    case R.id.new_game:
	        //newGame();
	    	//Toast.makeText(this, "m1", 3000).show();
	    	//DownloadFromUrl();
	    	String ver=DownloadVersion();
    		//Toast.makeText(cnt, ver, 3000).show();
    		PackageManager pm = this.getPackageManager();
    		try {
				app_ver = cnt.getPackageManager().getPackageInfo(cnt.getPackageName(), 0).versionName;
				//Toast.makeText(cnt, app_ver, 3000).show();
			} catch (NameNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		if(Float.parseFloat(ver)>Float.parseFloat(app_ver))
    		{
			try{
				final ProgressDialog dialog = ProgressDialog.show(this, "", "Downloading Please wait...", true,true);
		        Thread thread=new Thread(new Runnable(){
		        public void run(){
		        	try {
		        		//String ver=DownloadVersion();
		        		//Toast.makeText(cnt, ver, 3000).show();
		        		
		        		DownloadFromUrl();
		        		show_alert("File stored in /mnt/sdcard/aaps");
		        		/*Uninstall existing application*/
//		        		Intent intent = new Intent(Intent.ACTION_DELETE);
//		        		intent.setData(Uri.parse("package:com.gps_3"));
//		        		startActivity(intent);
		        		
		        		Intent intent = new Intent(Intent.ACTION_VIEW);
		        		intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/apps/" + "gps_3.apk")), "application/vnd.android.package-archive");
		        		startActivity(intent);
					} catch (Exception e) {
					}
		            runOnUiThread(new Runnable(){
		                @Override
		                public void run() {
		                    if(dialog.isShowing())
		                        dialog.dismiss();
		                }
		            });
		        }
		        });
		        thread.start();
			}
			catch (Exception e) {
				Toast.makeText(this, e.getMessage(), 3000).show();
			}
    		}
    		else
    			Toast.makeText(cnt, "Upper version not available", 3000).show();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
    		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		/*
		switch (arg0.getId()) {
		case R.id.imageButton1:
			Toast.makeText(this,"hello",3000).show();
			break;
		default:
			break;
		}
		*/
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    //Log.d(TAG, "ON NEW INTENT");
	    Toast.makeText(this, "abcd", 3000).show();
	    setIntent(intent);
	}

	
	public void fn1(View v)
	{
		//Toast.makeText(this,"hello",3000).show();
		intt=new Intent(this, gpsService.class);
		startService(intt);
		
		
		/*
		PendingIntent pi=PendingIntent.getActivity(this, 1, intt, 0);
		Notification not=new Notification(R.drawable.icon,"abc",System.currentTimeMillis());
		not.setLatestEventInfo(this, "Title", "Text", pi);
		not.flags=not.flags|Notification.FLAG_ONGOING_EVENT;
		startForeground();
		*/
		
		/*try {
			Intent myIntent = new Intent(start.this,gps_3.class);
			start.this.startActivity(myIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}*/
		//toggleGPS(false);
		
	}

//	public void update_app(View v)
//	{
//		try{
//			final ProgressDialog dialog = ProgressDialog.show(this, "", "Downloading Please wait...", true,true);
//	        Thread thread=new Thread(new Runnable(){
//	        public void run(){
//	        	try {
//	        		DownloadFromUrl();
//	        		show_alert("File stored in /mnt/sdcard/aaps");
//				} catch (Exception e) {
//				}
//	            runOnUiThread(new Runnable(){
//	                @Override
//	                public void run() {
//	                    if(dialog.isShowing())
//	                        dialog.dismiss();
//	                }
//	            });
//	        }
//
//	        });
//	        thread.start();
//		}
//		catch (Exception e) {
//			Toast.makeText(this, e.getMessage(), 3000).show();
//		}
//	}
//	
	public void DownloadFromUrl() {  //this is the downloader method
        try {
                URL url = new URL("http://192.168.0.24/gps/gps_3.apk");//"http://180.151.96.155/Mypage/image/temp.jpg"); //you can write here any link
                File file = new File("/sdcard/apps/gps_3.apk");

                long startTime = System.currentTimeMillis();
                URLConnection ucon = url.openConnection();
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.close();
//                Toast.makeText(this, "Download complete in " + ((System.currentTimeMillis() - startTime) / 1000) + " sec", 3000).show();
        } catch (IOException e) {
        	Toast.makeText(this,e.getMessage(), 3000).show();
        }

}


	public String DownloadVersion() {  //this is the downloader method
		String ver=null;
        try {
                URL url = new URL("http://192.168.0.24/gps/version.txt");//"http://180.151.96.155/Mypage/image/temp.jpg"); //you can write here any link
                //File file = new File("/sdcard/apps/gps_3.apk");

                URLConnection ucon = url.openConnection();
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                }
                ver=new String(baf.toByteArray());
                //Toast.makeText(this,ver, 3000).show();
        } catch (IOException e) {
        	Toast.makeText(this,e.getMessage(), 3000).show();
        }
        return ver;
}

	
	public void fn2(View v)
	{
		//Toast.makeText(this,"hello",3000).show();
		try {
			Intent myIntent = new Intent(start.this,acc.class);
			start.this.startActivity(myIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}
	}

	public void fn3(View v)
	{
		//Toast.makeText(this,"hello",3000).show();
		
		try {
//			Intent myIntent = new Intent(start.this,gps_4.class);
			Intent myIntent = new Intent(start.this,rpt_entry.class);
			start.this.startActivity(myIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}
		
	}
/*	public static void show_alert(final String str)  throws IOException
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();
    		//vi.vibrate(100);
			}
			});
	}*/
	
	public void fn5(View v)
	{
		//Toast.makeText(this,"hello",3000).show();
		try {
			Intent myIntent = new Intent(start.this,inout.class);
			start.this.startActivity(myIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}
	
//		GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
//            public void onGpsStatusChanged(int event) {
//                if( event == GpsStatus.GPS_EVENT_FIRST_FIX){
//                    Toast.makeText(getApplicationContext(), "GPS fixed",3000).show();
//                }
//            }
//     };
	
	}
	
	public void fn6(View v)
	{
		String ver=null;
        try {
//            URL url = new URL("http://192.168.0.24/gps/abc.txt");//"http://180.151.96.155/Mypage/image/temp.jpg"); //you can write here any link
//            //File file = new File("/sdcard/apps/gps_3.apk");
//
//            long startTime = System.currentTimeMillis();
//            URLConnection ucon = url.openConnection();
//            
//
//            InputStream is = ucon.getInputStream();
//            DataOutputStream outputStream = new DataOutputStream( ucon.getOutputStream() );
//            
//            //BufferedInputStream bis = new BufferedInputStream(is);
//            ByteArrayBuffer baf = new ByteArrayBuffer(50);
//            byte[] b="santanu".getBytes();
//            outputStream.write(b);
//            int current = 0;

            
//            while ((current = bis.read()) != -1) {
//                    baf.append((byte) current);
//            }
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(baf.toByteArray());
//            fos.close();

        } catch (Exception e) {
        	Toast.makeText(this,e.getMessage(), 3000).show();
        }
//        return ver;

		//Toast.makeText(this,"hello",3000).show();
//		try {
//			Intent myIntent = new Intent(start.this,inout.class);
//			start.this.startActivity(myIntent);
//			
//		} catch (Exception e) {
//			Toast.makeText(this,e.getMessage(), 3000).show();
//		}
	}

	
	public void fn7(View v)
	{
		//Toast.makeText(this,"hello",3000).show();
		try {
			Intent myIntent = new Intent(start.this,travel_details.class);
			start.this.startActivity(myIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}
	}

	public void fn8(View v)
	{
		//Toast.makeText(this,"hello",3000).show();
		try {
			Intent myIntent = new Intent(start.this,travel.class);
			start.this.startActivity(myIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}
	}
	
	
	public static void sms(String ph,String msg)
	{
		try {
			ContentValues values = new ContentValues();
			values.put("address", ph);
			values.put("body", msg);
			//values.put("date", "14/03/2012 14:30:00");
			cnt.getContentResolver().insert(Uri.parse("content://sms"), values);//content://sms/sent for sent message
	
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(cnt,e.getMessage(), 3000).show();
		}
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

	private InetAddress getLocalAddress()throws IOException {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        //return inetAddress.getHostAddress().toString();
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("SALMAN", ex.toString());
        }
        return null;
    }



	public void fn4(View v) throws IOException
	{
		super.finish();
		
		//Toast.makeText(getApplicationContext(), getLocalAddress().getHostAddress(), 3000).show();
		
		/*
		final Notification notif = new Notification(R.drawable.icon, tickerText, System.currentTimeMillis());


        Context context=getApplicationContext();
		final RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification_layout);
        contentView.setImageViewResource(R.id.image, R.drawable.icon);
        contentView.setTextViewText(R.id.text, tickerText);
        contentView.setProgressBar(R.id.progress,100,0, false);
        notif.contentView = contentView;                

        Intent notificationIntent = new Intent(context, Main.class);
        notificationIntent.putExtra("item_id", "1001"); // <-- HERE I PUT THE EXTRA VALUE
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notif.contentIntent = contentIntent;

        nm.notify(id, notif);

		*/
		
		
		
		
		/*
		Context context = getApplicationContext();

	    CharSequence contentTitle = "Notification";

	    CharSequence contentText = "New Notification";

	    final Notification notifyDetails =
	        new Notification(R.drawable.icon, "Consider yourself notified", System.currentTimeMillis());

	    Intent notifyIntent = new Intent(context, acc.class);
	    notifyIntent.putExtra("item_id", "1001"); 
	    //Bundle bundle = new Bundle();
        //bundle.putLong("A", 1);
        //bundle.putString("B", "abc");
        // notificationIntent.putExtras(bundle);
        //notifyIntent.replaceExtras(bundle);

	    PendingIntent intent =
	          PendingIntent.getActivity(context, 0,
	          notifyIntent,  PendingIntent.FLAG_UPDATE_CURRENT | Notification.FLAG_AUTO_CANCEL);

	    notifyDetails.setLatestEventInfo(context, contentTitle, contentText, intent);

	    ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(1, notifyDetails);
		
		*/
		
		
		
		
		
		//sms("a","b");
		/*
		Thread th;
		sendsms s1;
		s1=new sendsms(this);
		th=new Thread(s1);
		th.start();
		*/
		/*
		//add item to sms message folder
		try {
			ContentValues values = new ContentValues();
			values.put("address", "123456789");
			values.put("body", "foo bar");
			//values.put("date", "14/03/2012 14:30:00");
			getContentResolver().insert(Uri.parse("content://sms"), values);//content://sms/sent for sent message
	
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}
		*/
		
		/*
		    public static final String ADDRESS = "address";
   public static final String PERSON = "person";
        public static final String DATE = "date";
        public static final String READ = "read";
        public static final String STATUS = "status";
        public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_SENT = 2;

ContentValues values = new ContentValues();
           values.put(SMSHelper.ADDRESS, "+61408219690");
           values.put(SMSHelper.DATE, "1237080365055");
           values.put(SMSHelper.READ, 1);
           values.put(SMSHelper.STATUS, -1);
           values.put(SMSHelper.TYPE, 2);
           values.put(SMSHelper.BODY, "SMS inserting test");
                Uri inserted = getContentResolver().insert(Uri.parse("content://
sms"), values); 
		*/
		
	}
}