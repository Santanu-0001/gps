package com.gps_3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;





public class sendsms implements Runnable{
public String data="";
public String data1="";
public int flag=0;
public int flag1=0;
public Context cnt=null;
	public sendsms(Context baseContext) {
		cnt=baseContext;
	// TODO Auto-generated constructor stub
}
	/*public void recv(Context c)
	{
		cnt=c;
	}*/
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {  
            // Retrieve the ServerName  
			data="santanu";
			//gpsService.show_note("asa", "16/02/2012 15:03:00");
			
			while(true){
			InetAddress serverAddr = InetAddress.getByName("192.168.0.24");//"180.151.96.155"//"192.168.1.114");//
            DatagramSocket socket = new DatagramSocket(10900);  
            byte[] buf = new byte[800];//("Hello........").getBytes();
            
            DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, 10800);
            
            //socket.send(packet);  
            
            //gpsService.show_alert();
            //Thread.sleep(5000);

            socket.receive(packet);
            
            //socket.send(packet);  
            socket.close();
            
            data= new String(packet.getData());
            
            int type=0;
            
            int i,j;
            i=j=0;
            j=data.indexOf("/",i);
            //type=Integer.parseInt(data.substring(0,j));
            //start.show_alert(data.substring(0, j));
            //start.show_alert(data.substring(++j));
            start.sms(data.substring(0, j), data.substring(++j).trim());
            j++;
            i=j;
            
            
            /*
            if(type==1)
            {
	            j=data.indexOf("/",i);
	            flag=Integer.parseInt(data.substring(i,j));
	            j++;
	            i=j;
	            
	            j=data.indexOf("/",i);
	            flag1=Integer.parseInt(data.substring(i,j));
	            j++;
	            i=j;
	            gpsService.update(flag, flag1);
            }
            else if(type==2)
            {
            	String dt=data.substring(j, j+20);
                j+=20;
                
            	String str=data.substring(j).trim();
            	//gpsService.show_alert(str);
            	gpsService.show_note(str,dt);
            	
            }
            */
            /*
            char[] temp;
            temp=new char[4];
            int i=0;
            i=data.indexOf("/");
            if(i>=0)
            {
            data.getChars(0,i , temp, 0);
            String data1= new String(temp);
            flag= Integer.parseInt(data1);
            
            //temp=new char[4];
            data.getChars(i+1, data.lastIndexOf("/"), temp, 0);
            data1= new String(temp);
            flag1= Integer.parseInt(data1);
            }
            
            //gpsService.show_alert("Hello");
            gpsService.update(flag, flag1);*/
            
            
            
            
            //byte[] temp;//("23456").getBytes();
            //temp=new byte[4];
            //data.getBytes(0, 4, temp, 0);
            //data1= new String(temp);
            //flag= Integer.parseInt(data1);
            
            //data.getBytes(5, 9, temp, 0);
            //data1= new String(temp);
            //flag1= Integer.parseInt(data1);
            
			
			}
            
       } catch (Exception e) {  
            Log.e("UDP", "C: Error", e);  
       }   
	}

}
