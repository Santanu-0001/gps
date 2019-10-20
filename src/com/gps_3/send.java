package com.gps_3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class send implements Runnable{
	private boolean cnt=true;
	private Context ct=null;
	private gpsDataSource gps;
	private String iemi;
	public DatagramSocket socket;// = new DatagramSocket(10300);
	private Thread th;
	DatagramPacket receivePacket=null;
	private DatagramPacket packet=null;
	InetAddress serverAddr=null;
	private String ip=null;
	public send(Context ct1,String str)
	{
		ct=ct1;
		gps=new gpsDataSource(ct);
		gps.open();
		iemi=gpsService.iemi;
		ip=str;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			serverAddr = InetAddress.getByName(ip);//"180.151.96.155"//"192.168.1.114");//
            socket = new DatagramSocket(10300);  
    	    long ctr=1;
    	    	
    	    	int th_cnt=0;
    	    	byte[] buf=null;
    	    	byte[] recv_data="as".getBytes();
    	    	record r=null;
    	    	do
	    	    {
	        	    if(cnt)
	        	    {
	        	    	do{
	        	    	r=gps.getRecord(ct);
	        	    	}while(r==null);
	        	    	buf = (iemi + "/" + r.getLatitude()+ "/" + r.getLongtude() + "/" ).getBytes();//Integer.toString(ctr).getBytes();
	        	    	packet = new DatagramPacket(buf, buf.length, serverAddr, 10400);
	        	    	socket.send(packet);
	        	    	cnt=false;
	        	    }

	                if(th_cnt==0)
	                {
	        	    th = new Thread(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								byte[] buffer= new byte[4];
				                receivePacket = new DatagramPacket(buffer, buffer.length,serverAddr, 10400); //, serverAddr, Server.SERVERPORT);
				                socket.receive(receivePacket);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//"180.151.96.155"//"192.168.1.114");//
						}
	        	    	
	        	    });//.start();
	        	    th.start();
	        	    th_cnt++;
	                }
	        	    Thread.sleep(1000);
	        	    
	        	    String str=new String(receivePacket.getData());
	                if(str.trim().equals("next"))
	                {
	        	    	cnt=true;
	        	    	th.stop();
	        	    	socket.close();
	        	    	socket = new DatagramSocket(10300);  
	        	    	ctr=1;
	        	    	th_cnt=0;
	                }
	        	    else
	        	    	cnt=false;
	        	    
	        	    if(cnt)
	        	    	gps.deleteValue(Integer.toString(r.getId()));
	        	    
	        	    ctr++;
	        	    
	        	    if(ctr==20)
	        	    {
	        	    	cnt=true;
	        	    	
	        	    	th.stop();
	        	    	socket.close();
	        	    	socket = new DatagramSocket(10300);  
	        	    	ctr=1;
	        	    	th_cnt=0;
	        	    }
	        	    
	    	    }
	        	while(true);
    	    }
    		catch (Exception e) {
    			// TODO: handle exception
			}

	}

	
}
