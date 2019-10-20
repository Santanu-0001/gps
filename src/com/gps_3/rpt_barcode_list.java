package com.gps_3;

import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.util.Calendar;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class rpt_barcode_list extends Activity {
	 
	//static final String[] item_desc = 
      //         new String[] { "JobID", "Date", "Fooding", "Lodging","Travel","Other"};
	static String[] item_desc =new String[7];
	static Array ar=null;
	
	static final int[] image = 
            new int[] {R.drawable.jobid,R.drawable.date,R.drawable.fooding,R.drawable.hotel,R.drawable.travelexp,R.drawable.otherexp };
	static String[] msg = 
            new String[] {"Select Job ID","Set Date","Fooding Expense","Lodging Expense","Travel Expense","Other Expenses"};

	private alertDataSource alrt;

	private ListView lst=null;
	private LayoutAnimationController controller=null;
	private LayoutAnimationController controller1=null;
	private View vw=null;
	private int pos=0;
	private PopupWindow pw;
	private EditText mjobid;
	private String jobid="";
	private String dt="";
	private Spinner s1=null;
	private int myYear, myMonth, myDay;
	static final int ID_DATEPICKER = 0;
	private View layout=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rpt_barcode);
        try {
        	alrt=new alertDataSource(this);
            alrt.open();
	
		} catch (Exception e) {
			// TODO: handle exception
	    	Toast.makeText(this, e.getMessage(), 3000).show();
		}
        
		lst=(ListView)findViewById(R.id.listRptBar);
		//RptArrayAdapter ta=new RptArrayAdapter(this, item_desc,msg);
		//lst.setAdapter(ta);
		
//		item_desc =new String[10];
		
		
		try {
			//ViewGroup parent=(ViewGroup)lst;
			
			LayoutInflater inflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
				View rowView = inflater.inflate(R.layout.list_rpt, (ViewGroup) lst.getParent(), false);
				TextView textView = (TextView) rowView.findViewById(R.id.label);
				TextView textView1 = (TextView) rowView.findViewById(R.id.det);
				textView.setText("abcd");
				textView1.setText("efgh");


		 lst.addView(rowView);	
//		 ta.add(rowView);
		 
		 
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.toString(), 3000).show();
		}
		
		lst.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				vw=arg1;
				pos=arg2;
				int h=0;
		    	try {
			    	Rect r=new Rect();
			    	r.top=0;
			    	r.left=0;
			    	r.right=100;
			    	r.bottom=100;
			    	vw.getFocusedRect(r);//.getDrawingRect(r);
			    	h=r.height() * pos;
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
		lst.setTextFilterEnabled(true);
//		controller  = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
//		lst.setLayoutAnimation(controller);
	}
}

