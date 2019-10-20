package com.gps_3;

import java.lang.ref.SoftReference;
import java.util.Calendar;

import com.gps_3.travel.MyOnItemSelectedListener;

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

public class travel_entry extends Activity {
	 
	static final String[] item_desc = 
               new String[] { "JobID", "Date", "Fooding", "Lodging","Travel","Other"};
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
		setContentView(R.layout.travel_entry);
        try {
        	alrt=new alertDataSource(this);
            alrt.open();
	
		} catch (Exception e) {
			// TODO: handle exception
	    	Toast.makeText(this, e.getMessage(), 3000).show();
		}
        
		lst=(ListView)findViewById(R.id.list11);
		TravelArrayAdapter ta=new TravelArrayAdapter(this, item_desc,image,msg);
		lst.setAdapter(ta);
		
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
				switch (pos) {
				case 0:
					initiatePopupWindow(h);	
					break;
				case 1:
					date_select();
					break;
				case 2:
					initiateFoodingWindow(h);	
					break;
				case 3:
					initiateLodgingWindow(h);	
					break;
				case 4:
					try {
						String dt_val=dt;//.getText().toString();
						if(!dt_val.equals(""))
						{
						Intent myIntent = new Intent(travel_entry.this,transport.class);
						myIntent.putExtra("dt_val", dt_val);
						myIntent.putExtra("jobid", jobid);
						travel_entry.this.startActivity(myIntent);
						}
						else
							Toast.makeText(getApplicationContext(),"Select Date", 3000).show();
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(),e.getMessage(), 3000).show();
					}
					break;
				case 5:
					try {
						String dt_val=dt;//.getText().toString();
						if(!dt_val.equals(""))
						{
						Intent myIntent = new Intent(travel_entry.this,otherexp.class);
						myIntent.putExtra("dt_val", dt_val);
						myIntent.putExtra("jobid", jobid);
						travel_entry.this.startActivity(myIntent);
						}
						else
							Toast.makeText(getApplicationContext(),"Select Date", 3000).show();
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(),e.getMessage(), 3000).show();
					}

					break;
				default:
					break;
				}
//				TextView tx =(TextView)arg1.findViewById(R.id.det);
//				Toast.makeText(getApplicationContext(), tx.getText(), Toast.LENGTH_LONG).show();
//				tx.setText("santanu/"+pos);
			}
		});
		lst.setTextFilterEnabled(true);
		controller  = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
		lst.setLayoutAnimation(controller);
	}

	private void initiatePopupWindow(int p) {
	    try {
	        LayoutInflater inflater = (LayoutInflater) travel_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View layout = inflater.inflate(R.layout.popup_window,(ViewGroup) findViewById(R.id.popup_element));
	        
	        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	        pw = new PopupWindow(layout, 280, layout.getMeasuredHeight()+1, true);//280,70
	        pw.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        pw.setOutsideTouchable(true);
	        pw.showAtLocation(layout, Gravity.TOP, 0, 109+p+(p/56)*1);//110
	        ImageButton cancelButton = (ImageButton) layout.findViewById(R.id.imageButton1);
	        cancelButton.setOnClickListener(cancel_button_click_listener);
	        
//	        EditText et=(EditText)layout.findViewById(R.id.editText10);
//	        et.setText("Santanu");
	        
//	        AlertDialog.Builder ab=new AlertDialog.Builder(this);
//	        ab.setView(layout);
//	        AlertDialog customDialog= ab.create();
//	        customDialog.show();
	        
	        s1=(Spinner)layout.findViewById(R.id.trvJob1);
		    ArrayAdapter<String> adapter1  = new ArrayAdapter<String>(
		            this,android.R.layout.simple_spinner_item,alrt.showAllJobid(this));
		    adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		    //adapter1.insert("Job ID", 0);
		    s1.setAdapter(adapter1);
		    
		    s1.setOnItemSelectedListener(new MyOnItemSelectedListener());

	        
	    } catch (Exception e) {
	    	Toast.makeText(getApplicationContext(), e.toString() + "/" + e.getMessage(), Toast.LENGTH_LONG).show();
	    }
	}
	
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
	    public void onClick(View v) {
	    	try {
	    		View vw1=lst.getChildAt(0);
				TextView tx =(TextView)vw1.findViewById(R.id.det);
				tx.setText(jobid);
		    	pw.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	};

	private void initiateFoodingWindow(int p) {
	    try {
	        LayoutInflater inflater = (LayoutInflater) travel_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        layout = inflater.inflate(R.layout.fooding,(ViewGroup) findViewById(R.id.popup_element));
	        
	        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	        pw = new PopupWindow(layout, 280, layout.getMeasuredHeight()+1, true);//280,70
	        pw.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        pw.setOutsideTouchable(true);
	        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	        pw.showAtLocation(layout, Gravity.TOP, 0, 109+p+(p/56)*1);//110
	        ImageButton cancelButton = (ImageButton) layout.findViewById(R.id.imageButton1);
	        cancelButton.setOnClickListener(cancel_button_fooding_listener);
	    } catch (Exception e) {
	    	Toast.makeText(getApplicationContext(), e.toString() + "/" + e.getMessage(), Toast.LENGTH_LONG).show();
	    }
	}
	
	private OnClickListener cancel_button_fooding_listener = new OnClickListener() {
	    public void onClick(View v) {
	    	try {
	    		View vw1=lst.getChildAt(2);
				TextView tx =(TextView)vw1.findViewById(R.id.det);
				EditText et=(EditText)layout.findViewById(R.id.editText10);
				tx.setText("Rs. " + et.getText());
		    	pw.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	};


	private void initiateLodgingWindow(int p) {
	    try {
	        LayoutInflater inflater = (LayoutInflater) travel_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        layout = inflater.inflate(R.layout.lodging,(ViewGroup) findViewById(R.id.popup_element));
	        
	        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	        pw = new PopupWindow(layout, 280, layout.getMeasuredHeight()+1, true);//280,70
	        
	        pw.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        pw.setOutsideTouchable(true);
	        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	        pw.showAtLocation(layout, Gravity.TOP, 0, 109+p+(p/56)*1);//110
	        
	        ImageButton cancelButton = (ImageButton) layout.findViewById(R.id.imageButton1);
	        cancelButton.setOnClickListener(cancel_button_lodging_listener);
	    } catch (Exception e) {
	    	Toast.makeText(getApplicationContext(), e.toString() + "/" + e.getMessage(), Toast.LENGTH_LONG).show();
	    }
	}
	
	private OnClickListener cancel_button_lodging_listener = new OnClickListener() {
	    public void onClick(View v) {
	    	try {
	    		View vw1=lst.getChildAt(3);
				TextView tx =(TextView)vw1.findViewById(R.id.det);
				EditText et=(EditText)layout.findViewById(R.id.editText10);
				tx.setText("Rs. " + et.getText());
		    	pw.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	};

	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            Cursor cursor=null;
//            mode_edit=false;
            try {
            	jobid=parent.getItemAtPosition(pos).toString();
//				TextView tx =(TextView)vw.findViewById(R.id.det);
////				Toast.makeText(getApplicationContext(), tx.getText(), Toast.LENGTH_LONG).show();
//				tx.setText(jobid);

//            	cursor=alrt.showJobDet(jobid);
//              	jobdesc.setText(cursor.getString(2));
//        	    values = trv.travel_getAllValue(jobid);//travel_getValue(jobid); //new ArrayList<String>();//datasource.getAllComments();
//        		adapter = new ArrayAdapter<String>(getApplicationContext(),
//        				android.R.layout.preference_category, values);//.preference_category
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

//    private Button.OnClickListener datePickerButtonOnClickListener = new Button.OnClickListener(){
//
//		  @Override
//		  public void onClick(View v) {
	private void date_select()
	{
	// TODO Auto-generated method stub
		   final Calendar c = Calendar.getInstance();
			   myYear = c.get(Calendar.YEAR);
			   myMonth = c.get(Calendar.MONTH);
			   myDay = c.get(Calendar.DAY_OF_MONTH);
		   showDialog(ID_DATEPICKER);
	}
//   };

   private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener(){
    @Override
    public void onDateSet(DatePicker view, int year, 
      int monthOfYear, int dayOfMonth) {
	     String date=new String().format("%02d/%02d/%04d", dayOfMonth,monthOfYear+1,year);
//	    		 "Year: " + String.valueOf(year) + "\n"
//				 + "Month: " + String.valueOf(monthOfYear+1) + "\n"
//				 + "Day: " + String.valueOf(dayOfMonth);
//     Toast.makeText(travel_entry.this, date,Toast.LENGTH_LONG).show();
	     //travel.dt.setText("santanu");
	     
	     
	     dt=date;
 			View vw1=lst.getChildAt(1);
			TextView tx =(TextView)vw1.findViewById(R.id.det);
			tx.setText(dt);

    }

	
  };
  
  @Override
  protected Dialog onCreateDialog(int id) {
   // TODO Auto-generated method stub
   switch(id){
    case ID_DATEPICKER:
     //Toast.makeText(travel.this,"- onCreateDialog -",Toast.LENGTH_LONG).show();
		
		return new DatePickerDialog(travel_entry.this,
   		  myDateSetListener,
   		  myYear, myMonth, myDay);
    default:
     return null;
   }
  } 

	
}

