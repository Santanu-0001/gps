package com.gps_3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class reject extends Activity implements OnClickListener {
	private Button b1;
	private Button b2;
	private EditText ed=null;
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reject);
        b1=(Button)findViewById(R.id.rejSend);
        b1.setOnClickListener(this);
        b2=(Button)findViewById(R.id.rejCancel);
        b2.setOnClickListener(this);
        ed=(EditText)findViewById(R.id.editText1);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.rejSend:
			
			
			sendAcc s2=null;
			Thread th=null;
			String str;
	    	str=acc.jid;//jobid.getText().toString();
	    	if(!str.trim().equals("") & !ed.getText().toString().trim().equals(""))
	    	{
	    		try {
	    			start.closeEnable=false;
	    			s2=new sendAcc(this,str+"/Reject/"+ed.getText().toString(),getString(R.string.ip),"Rejection");
					th=new Thread(s2);
					th.start();
	    			
	    		    

					Thread thread = new Thread(){
			             @Override
			            public void run() {
			                 try {
			                	 while(start.closeEnable==false)
			                	 {
				                    Thread.sleep(1000); // As I am using LENGTH_LONG in Toast
				                    
			                	 }
			                	 ((Activity) reject.this).finish();
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }
			             }  
			           };
			           thread.start();

			    	
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(this, "error", 3000).show();
				}
	    	}
	    	else
	    		Toast.makeText(this,"Can't leave blank", 3000).show();
	    	

			break;
		case R.id.rejCancel:
			//reject.this.getWindow().getCallback();
			super.finish();
			break;
		default:
			break;
		
		}
	}
	@Override
	public void onBackPressed() 
	{
		//Toast.makeText(this, "close", Toast.LENGTH_LONG).show();
		if(start.closeEnable)
			super.onBackPressed();
	}

}