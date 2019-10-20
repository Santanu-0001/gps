package com.gps_3;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gps_3.travel.MyOnItemSelectedListener;

public class survey extends ListActivity implements OnClickListener{
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
	EditText ptz=null;
	EditText fixed=null;
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
        setContentView(R.layout.survey);
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


//        Button add= (Button)findViewById(R.id.srvAdd);
//		add.setOnClickListener(this);
//
//		trans= (Button)findViewById(R.id.srvTransport);
//		trans.setOnClickListener(this);
//		
//		others= (Button)findViewById(R.id.srvOther);
//		others.setOnClickListener(this);
		
//		dt= (TextView)findViewById(R.id.srvLab3);
		jobdesc= (TextView)findViewById(R.id.srvLab2);
		ptz= (EditText)findViewById(R.id.srvPtz);
		fixed= (EditText)findViewById(R.id.srvFixed);
		
		
	    s1=(Spinner)findViewById(R.id.srvJob);
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
//        	    values = trv.travel_getAllValue(jobid);
//        		adapter = new ArrayAdapter<String>(getApplicationContext(),
//        				android.R.layout.preference_category, values);
//        		setListAdapter(adapter);


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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	public void shownext(View v)
	{
		//Toast.makeText(this,"hello",3000).show();
		
		try {
			Intent myIntent = new Intent(survey.this,surveydetails.class);
			survey.this.startActivity(myIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,e.getMessage(), 3000).show();
		}
		
	}

}
