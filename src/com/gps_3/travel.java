package com.gps_3;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.gps_3.gps_4.MyOnItemSelectedListener_1;

import android.R.bool;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

public class travel extends ListActivity implements OnClickListener{
	private alertDataSource alrt;
	private travelDataSource trv;
	Spinner s1=null;
	private int myYear, myMonth, myDay;
	static final int ID_DATEPICKER = 0;
	public static TextView dt;//=null;
	public static TextView jobdesc;
	ArrayAdapter adapter=null;
	Button trans=null;
	Button add=null;
	Button others=null;
	EditText food=null;
	EditText lodge=null;
	String jobid=null;
	private List<String> values=null;
	private Boolean mode_edit=false;
	private Cursor cr_edit=null;
	private String row_id=null;
	public static String iemi=null;
	public static Context cnt=null;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel);
        try {
        	alrt=new alertDataSource(this);
            alrt.open();
	
		} catch (Exception e) {
			// TODO: handle exception
	    	Toast.makeText(this, e.getMessage(), 3000).show();
		}
        cnt=getApplicationContext();
        
        trv=new travelDataSource(this);
        trv.open();

        Button b3= (Button)findViewById(R.id.trvDate);
		b3.setOnClickListener(datePickerButtonOnClickListener);

        Button add= (Button)findViewById(R.id.trvAdd);
		add.setOnClickListener(this);

		trans= (Button)findViewById(R.id.trvTransport);
		trans.setOnClickListener(this);
		
		others= (Button)findViewById(R.id.trvOther);
		others.setOnClickListener(this);
		
		dt= (TextView)findViewById(R.id.trvLab3);
		jobdesc= (TextView)findViewById(R.id.trvLab2);
		food= (EditText)findViewById(R.id.trvFood);
		lodge= (EditText)findViewById(R.id.trvLodge);
		
		
	    s1=(Spinner)findViewById(R.id.trvJob);
	    ArrayAdapter<String> adapter1  = new ArrayAdapter<String>(
	            this,android.R.layout.simple_spinner_item,alrt.showAllJobid(this));
	    adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
	    //adapter1.insert("Job ID", 0);
	    s1.setAdapter(adapter1);
	    
	    s1.setOnItemSelectedListener(new MyOnItemSelectedListener());

	    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		iemi = telephonyManager.getDeviceId().toString();

		
//	    values = trv.travel_getAllValue(); //new ArrayList<String>();//datasource.getAllComments();
//		adapter = new ArrayAdapter<String>(this,
//				android.R.layout.preference_category, values);//.preference_category
//		
//		setListAdapter(adapter);
		
		
	    
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mnutravel, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		String app_ver=null;
		
	    switch (item.getItemId()) {
	    case R.id.mnu_send:
	    	//Toast.makeText(this, "Send", 3000).show();
	    	sendTravel s1;
	    	Thread th1;
			s1=new sendTravel(this,getString(R.string.ip),jobid);
			th1=new Thread(s1);
			th1.start();

	        return true;
	    case R.id.mnu_clear:
	    	Toast.makeText(this, "Clear", 3000).show();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
    		
	}

	public static void show_alert(final String str)  throws IOException
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();
//    		vi.vibrate(100);
			}
			});
	}

    private Button.OnClickListener datePickerButtonOnClickListener = new Button.OnClickListener(){

		  @Override
		  public void onClick(View v) {
		   // TODO Auto-generated method stub
		   final Calendar c = Calendar.getInstance();
			   myYear = c.get(Calendar.YEAR);
			   myMonth = c.get(Calendar.MONTH);
			   myDay = c.get(Calendar.DAY_OF_MONTH);
		   showDialog(ID_DATEPICKER);
		  }
   };

   private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener(){
    @Override
    public void onDateSet(DatePicker view, int year, 
      int monthOfYear, int dayOfMonth) {
	     String date=new String().format("%02d/%02d/%04d", dayOfMonth,monthOfYear+1,year);
//	    		 "Year: " + String.valueOf(year) + "\n"
//				 + "Month: " + String.valueOf(monthOfYear+1) + "\n"
//				 + "Day: " + String.valueOf(dayOfMonth);
     //Toast.makeText(travel.this, date,Toast.LENGTH_LONG).show();
	     //travel.dt.setText("santanu");
	     dt.setText(date);
    }

	
  };

   
   @Override
   protected Dialog onCreateDialog(int id) {
    // TODO Auto-generated method stub
    switch(id){
     case ID_DATEPICKER:
      //Toast.makeText(travel.this,"- onCreateDialog -",Toast.LENGTH_LONG).show();
		
		return new DatePickerDialog(travel.this,
    		  myDateSetListener,
    		  myYear, myMonth, myDay);
     default:
      return null;
    }
   } 
 
   
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.trvTransport:
			try {
				String dt_val=dt.getText().toString();
				if(!dt_val.equals(""))
				{
				Intent myIntent = new Intent(travel.this,transport.class);
				myIntent.putExtra("dt_val", dt_val);
				myIntent.putExtra("jobid", jobid);
				travel.this.startActivity(myIntent);
				}
				else
					Toast.makeText(this,"Select Date", 3000).show();
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this,e.getMessage(), 3000).show();
			}
			break;
		case R.id.trvOther:
			try {
				String dt_val=dt.getText().toString();
				if(!dt_val.equals(""))
				{
				Intent myIntent = new Intent(travel.this,otherexp.class);
				myIntent.putExtra("dt_val", dt_val);
				myIntent.putExtra("jobid", jobid);
				travel.this.startActivity(myIntent);
				}
				else
					Toast.makeText(this,"Select Date", 3000).show();
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this,e.getMessage(), 3000).show();
			}
			break;
		case R.id.trvAdd:
			try {
				String fd=(food.getText().toString());
				if(fd.equals(""))
					fd="0";
				int fd_val=Integer.parseInt(fd);
				
				String ld=(lodge.getText().toString());
				if(ld.equals(""))
					ld="0";

				int ld_val=Integer.parseInt(ld);
				if(!mode_edit)
				{
				String dt_val=dt.getText().toString();
				if(!jobid.equals("") & !dt_val.equals("") & fd_val>=0 & ld_val>=0)
				{
					if(!trv.travel_isDuplicate(jobid, dt_val))
					{
					trv.travel_insertValue(jobid, dt_val, fd_val, ld_val);
					adapter.add(trv.travle_showLastValue());
					food.setText("");
					lodge.setText("");
					}
					else
						Toast.makeText(getApplicationContext(), "Duplicate Date", Toast.LENGTH_LONG).show();
				}
				else
					Toast.makeText(getApplicationContext(), "Fillup all the data", Toast.LENGTH_LONG).show();
				}
				else
				{
					trv.travel_update(row_id, fd_val, ld_val);
					values = trv.travel_getValue(jobid);
					adapter = new ArrayAdapter<String>(getApplicationContext(),
	        				android.R.layout.preference_category, values);
					setListAdapter(adapter);
					mode_edit=false;
					//adapter.notifyDataSetChanged();
				}
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}
	}
	
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            Cursor cursor=null;
            mode_edit=false;
            try {
            	jobid=parent.getItemAtPosition(pos).toString();
            	cursor=alrt.showJobDet(jobid);
              	jobdesc.setText(cursor.getString(2));
        	    values = trv.travel_getAllValue(jobid);//travel_getValue(jobid); //new ArrayList<String>();//datasource.getAllComments();
        		adapter = new ArrayAdapter<String>(getApplicationContext(),
        				android.R.layout.preference_category, values);//.preference_category
        		setListAdapter(adapter);


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
protected void onListItemClick(ListView l, View v, int position, long id) {
	// TODO Auto-generated method stub
	super.onListItemClick(l, v, position, id);
		String c = (String)l.getItemAtPosition(position);
		mode_edit=true;
		int i=0;
		i=c.indexOf(">",0);
		String jid=c.substring(0,i);
		//Toast.makeText(this, c.substring(0,i), 3000).show();
		cr_edit=trv.travel_showJobDet(jid);
		row_id=jid;
		dt.setText(cr_edit.getString(2));
		int i1=0;
		try {
			food.setText(Integer.toString(cr_edit.getInt(3)));
			lodge.setText(Integer.toString(cr_edit.getInt(4)));
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(),  e.getMessage(), 3000).show();
		}

}
}
