package com.gps_3;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class rcv extends BroadcastReceiver {
    /** Called when the activity is first created. */
    @Override
    public void onReceive(Context context, Intent intent) {
    	Intent serviceIntent = new Intent();
		try
		{
    	serviceIntent.setAction("com.gpsService");//com.wissen.startatboot.MyService");
		context.startService(serviceIntent);
		}
		catch(Exception e){
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
		}

}