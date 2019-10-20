package com.gps_3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import android.R.bool;
import android.content.Context;
import android.database.Cursor;

public class sendTravel implements Runnable{
	private boolean cnt=true;
	private Context ct=null;
//	private gpsDataSource gps;
	travelDataSource trv;
	private String iemi;
	public DatagramSocket socket;// = new DatagramSocket(10300);
	private Thread th;
	DatagramPacket receivePacket=null;
	private DatagramPacket packet=null;
	InetAddress serverAddr=null;
	private String ip=null;
	private String jobid=null;
	
	public sendTravel(Context ct1,String str,String str1)
	{
		ct=ct1;
//		gps=new gpsDataSource(ct);
//		gps.open();
		trv=new travelDataSource(ct);
		trv.open();
		iemi=travel.iemi;
		ip=str;
		jobid=str1;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
    	    long ctr=1;
    	    	
    	    	int th_cnt=0;
    	    	byte[] buf=null;
    	    	byte[] recv_data="as".getBytes();
    	    	int ctr10=0;
    	    	record r=null;
    	    	Cursor c=null;
    	    	Boolean flag=true;
    	    	do
    	    	{
    				serverAddr = InetAddress.getByName(ip);//"180.151.96.155"//"192.168.1.114");//
    	            socket = new DatagramSocket(10300);  
    	    		
    	    	switch (ctr10) {
				case 0:
	    	    	c=trv.travel_getAllValueC(jobid);
	    	    	if(c!=null)
	    	    		c.moveToFirst();
					break;
				case 1:
					try {
						c=trv.transport_getAllValueC(jobid);
//						travel.show_alert(ctr10+"/");
//						travel.show_alert(c.getCount()+"/");
					} catch (Exception e) {
						// TODO: handle exception
						travel.show_alert(e.getMessage());
					}
	    	    	
	    	    	
	    	    	if(c!=null)
	    	    		c.moveToFirst();
//	    	    	else
//	    	    		travel.show_alert("null");
					break;

				case 2:
	    	    	c=trv.otherexp_getAllValueC(jobid);
	    	    	
	    	    	if(c!=null)
	    	    	{
	    	    		c.moveToFirst();
//	    	    		travel.show_alert(c.getCount()+"/");
	    	    	}
//	    	    	else
//	    	    		travel.show_alert("null");
	    	    	
	    	    	flag=false;
					break;
				default:
					break;
				}
//    	    	travel.show_alert(ctr10+"/");
    	    	cnt=true;
    	    	do
	    	    {
	        	    if(cnt)
	        	    {
	        	    	if(ctr10==0)
	        	    	{
		        	    	try {
		        	    		buf = (iemi + "/" + c.getString(1) + "/" + c.getString(2) + "/" + Integer.toString(c.getInt(3)) + "/" + Integer.toString(c.getInt(4))).getBytes();//Integer.toString(ctr).getBytes();
//		        	    		travel.show_alert(ctr10+"/"+c.getString(1));
							} catch (Exception e) {
								// TODO: handle exception
								travel.show_alert(e.getMessage());
							}
	        	    	}
	        	    	if(ctr10==1)
	        	    	{
		        	    	try {
		        	    		
		        	    		buf = (iemi + "/" + c.getString(1) + "/" + c.getString(2) + "/" + c.getString(3) + "/" + c.getString(4) + "/" + c.getString(5) + "/" + Integer.toString(c.getInt(6))).getBytes();//Integer.toString(ctr).getBytes();
//		        	    		travel.show_alert("abc");
//		        	    		travel.show_alert(buf.toString());
							} catch (Exception e) {
								// TODO: handle exception
								travel.show_alert(e.getMessage());
							}
	        	    	}
	        	    	if(ctr10==2)
	        	    	{
		        	    	try {
		        	    		buf = (iemi + "/" + c.getString(1) + "/" + c.getString(2) + "/" + c.getString(3) + "/" + Integer.toString(c.getInt(4))).getBytes();//Integer.toString(ctr).getBytes();
		        	    		//travel.show_alert(ctr10+"/"+c.getString(1));
							} catch (Exception e) {
								// TODO: handle exception
								travel.show_alert(e.getMessage());
							}
	        	    	}
//	        	    	travel.show_alert("abc" + buf.length);
	        	    	try {
	        	    		packet = new DatagramPacket(buf, buf.length, serverAddr, 10400);
		        	    	socket.send(packet);
//		        	    	travel.show_alert("abc");
		        	    		
						} catch (Exception e) {
							// TODO: handle exception
							travel.show_alert(e.getMessage());
						}
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
	        	    	
	        	    	th.stop();
	        	    	socket.close();
	        	    	if(!c.isLast())
	        	    	{
	        	    		c.moveToNext();
	        	    		cnt=true;
	        	    	
		        	    	socket = new DatagramSocket(10300);  
		        	    	ctr=1;
		        	    	th_cnt=0;
	        	    	}
	        	    	else
	        	    	{
	        	    		
	        	    		//travel.show_alert("end");
	        	    		break;
	        	    	}
	        	    		
	                }
	        	    else
	        	    	cnt=false;
	        	    
	        	    //if(cnt)
	        	    	//gps.deleteValue(Integer.toString(r.getId()));
	        	    
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
    	    	ctr10=ctr10+1;
    	    	}
    	    	while(flag);
    	    }
    		catch (Exception e) {
    			// TODO: handle exception
			}

	}

	
}
