package com.gps_3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;





public class sendAcc implements Runnable{
	private boolean cnt1=true;
	private boolean cnt2=true;
	private Context ct=null;
	//private gpsDataSource gps;
	//private String iemi;
	
	public DatagramSocket socket;
	private Thread th;
	private DatagramPacket receivePacket=null;
	private DatagramPacket packet=null;
	private InetAddress serverAddr=null;
	private String msg=null; 
	public Context cnt=null;
	private String ip=null;
	private String type=null;
	
	public sendAcc(Context baseContext,String str,String str1,String str2) {
		cnt=baseContext;
		msg=str;
		ip=str1;
		type=str2;
	// TODO Auto-generated constructor stub
}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {  
			
			serverAddr = InetAddress.getByName(ip);//"180.151.96.155"//"192.168.1.114");//
            socket = new DatagramSocket(10900);  
            //gpsService.show_alert("asa");
            //acc.show_alert("1");
            
            long ctr=1;
    	    	
    	    	int th_cnt=0;
    	    	byte[] buf=null;
    	    	byte[] recv_data="as".getBytes();
    	    	record r=null;
    	    	
    	    	//gpsService.show_note("asa", "16/02/2012 15:03:00");
    	    	
    	    	do
	    	    {
	        	    if(cnt1)
	        	    {
	        	    	//do{
	        	    	//r=gps.getRecord(ct);
	        	    	//}while(r==null);
	        	    	//buf = (iemi + "/" + r.getLatitude()+ "/" + r.getLongtude() + "/" ).getBytes();//Integer.toString(ctr).getBytes();
	        	    	//gpsService.show_alert("asa");
	        	    	buf = msg.getBytes();
	        	    	packet = new DatagramPacket(buf, buf.length, serverAddr, 10800);
	        	    	socket.send(packet);
	        	    	cnt1=false;
	        	    	//acc.show_alert("2");
	                    
	        	    	//gpsService.show_alert("asa1");
	        	    }

	                if(th_cnt==0)
	                {
	        	    th = new Thread(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								byte[] buffer= new byte[4];
				                receivePacket = new DatagramPacket(buffer, buffer.length,serverAddr, 10800); //, serverAddr, Server.SERVERPORT);
				                socket.receive(receivePacket);
							} catch (Exception e) {
								try {
									start.show_alert("Error");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								// TODO Auto-generated catch block
								//e.printStackTrace();
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
	        	    	cnt1=true;
	        	    	th.stop();
	        	    	socket.close();
	        	    	//socket = new DatagramSocket(10900);  
	        	    	ctr=1;
	        	    	th_cnt=0;
	        	    	cnt2=false;
	        	    	//acc.show_alert(type + " Sent");
	        	    	
	        	    	
	        	    	//acc.show_alert(dt.substring(0, dt.length()-1));//dt.substring(0, dt.length()-1).trim()
	        	    	start.show_alert(type + " Sent");
	        	    	if(type.equals("Acceptance"))
        	    		{
	        	    		Cursor c=alertDataSource.showJobDet(acc.jid);
		        	    	String dt=c.getString(3);
		        	    	
		        	    	gpsService.cal_event(c.getString(3), c.getString(2));//c.getString(3)//"17/03/2012 18:30:00"	
        	    			alertDataSource.updateAcc(acc.jid);
	        	    		acc.refreshJob();
	        	    		start.closeEnable=true;
        	    		}
	        	    	else if(type.equals("Check In"))
	        	    	{
	        	    		alertDataSource.chkin_insertValue(inout.jid);
	        	    	}
	        	    	else if(type.equals("Check Out"))
	        	    	{
	        	    		alertDataSource.chkin_deleteJobid(inout.jid);
	        	    	}
	        	    	else
	        	    	{
	        	    		alertDataSource.deleteJobid(acc.jid);
	        	    		acc.refreshJob();
	        	    		start.closeEnable=true;
	        	    	}
	        	    	
	        	    	
	        	    		
						
	        	    	
	        	    	
	        	    	
	        	    	
	        	    	
	        	    	//gpsService.show_note("Acceptance Sent", "16/02/2012 15:03:00");
	                }
	        	    else
	        	    	cnt1=false;
	        	    
	                //if(cnt)
	        	    	//gps.deleteValue(Integer.toString(r.getId()));
	        	    
	        	    ctr++;
	        	    
	        	    if(ctr==5)
	        	    {
	        	    	cnt1=true;
	        	    	cnt2=false;
	        	    	th.stop();
	        	    	socket.close();
	        	    	//socket = new DatagramSocket(10900);  
	        	    	ctr=1;
	        	    	th_cnt=0;
	        	    	String str1="Server not found";
	        	    	//gpsService.show_note(str1, "16/02/2012 15:03:00");
	        	    	
	        	    	start.show_alert(str1);
	        	    	
	        	    	start.closeEnable=true;
	        	    }
	        	    
	    	    }
	        	while(cnt2);

			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}