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
import android.text.Layout;
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

public class rpt_entry extends Activity {
	 
	static final String[] item_desc = 
               new String[] { "JobID", "Status", "Barcode", "Remarks","Image","Preview"};
	static final int[] image = 
            new int[] {R.drawable.jobid,R.drawable.date,R.drawable.fooding,R.drawable.hotel,R.drawable.travelexp,R.drawable.otherexp };
	static String[] msg = 
            new String[] {"Select Job ID","Set Job Status","Material Details","Enter Remarks","Select Picture","Show Pyreview"};

	private alertDataSource alrt;

	private ListView lst=null;
	private LayoutAnimationController controller=null;
	private LayoutAnimationController controller1=null;
	private View vw=null;
	private int pos=0;
	private PopupWindow pw;
	private EditText mjobid;
	private String jobid="";
	private String jobstatus="";
	private String dt="";
	private Spinner s1=null;
	private int myYear, myMonth, myDay;
	static final int ID_DATEPICKER = 0;
	private View layout=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rpt_entry);
        try {
        	alrt=new alertDataSource(this);
            alrt.open();
	
		} catch (Exception e) {
			// TODO: handle exception
	    	Toast.makeText(this, e.getMessage(), 3000).show();
		}
        
		lst=(ListView)findViewById(R.id.listRpt);
		RptArrayAdapter ta=new RptArrayAdapter(this, item_desc,msg);
		lst.setAdapter(ta);
		
		TextView tx=(TextView)findViewById(R.id.rptText);
		tx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View lay=(View)findViewById(R.id.rptList);
				Rect r=new Rect();
				int[] i = new int[2];
				lay.getGlobalVisibleRect(r);
				Toast.makeText(getApplicationContext(), lay.getLayoutParams().height + "/" + r.top + "/" + r.left + "/" + r.bottom + "/" + r.right , Toast.LENGTH_LONG).show();
//		        Toast.makeText(getApplicationContext(), r.top + "/" + r.left + "/" + r.bottom + "/" + r.right, Toast.LENGTH_LONG).show();
//				Toast.makeText(getApplicationContext(), i[0] + "/", Toast.LENGTH_LONG).show();
			}
		});
		
		
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
//					date_select();
					initiateStatusWindow(h);
					break;
				case 2:
//					initiateFoodingWindow(h);	
					break;
				case 3:
//					initiateLodgingWindow(h);	
					break;
				case 4:
					try {
//						String dt_val=dt;//.getText().toString();
//						if(!dt_val.equals(""))
						{
						Intent myIntent = new Intent(rpt_entry.this,rpt_barcode_list.class);
//						myIntent.putExtra("dt_val", dt_val);
//						myIntent.putExtra("jobid", jobid);
						rpt_entry.this.startActivity(myIntent);
						}
//						else
//							Toast.makeText(getApplicationContext(),"Select Date", 3000).show();
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(),e.getMessage(), 3000).show();
					}
					break;
				case 5:
					try {
//						String dt_val=dt;//.getText().toString();
//						if(!dt_val.equals(""))
						{
						Intent myIntent = new Intent(rpt_entry.this,otherexp.class);
//						myIntent.putExtra("dt_val", dt_val);
//						myIntent.putExtra("jobid", jobid);
						rpt_entry.this.startActivity(myIntent);
						}
//						else
//							Toast.makeText(getApplicationContext(),"Select Date", 3000).show();
						
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

	private int getRelativeTop(View mv)
	{
		if(mv.getParent()==mv.getRootView())
			return mv.getTop();
		else
			return mv.getTop() + getRelativeTop((View) mv.getParent());
	}
	private void initiatePopupWindow(int p) {
	    try {
	    	
			View lay=(View)findViewById(R.id.rptList);
			Rect r=new Rect();
			lay.getGlobalVisibleRect(r);
	        LayoutInflater inflater = (LayoutInflater) rpt_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View layout = inflater.inflate(R.layout.popup_window,(ViewGroup) findViewById(R.id.popup_element));
	        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	        pw = new PopupWindow(layout, 280, layout.getMeasuredHeight(), true);//280,70
	        pw.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        pw.setOutsideTouchable(true);
	        pw.showAtLocation(layout, Gravity.TOP, 0,r.top+(p/56)*1);//109+p+(p/56)*1//110
	        ImageButton cancelButton = (ImageButton) layout.findViewById(R.id.imageButton1);
	        cancelButton.setOnClickListener(cancel_button_click_listener);
	        s1=(Spinner)layout.findViewById(R.id.trvJob1);
		    ArrayAdapter<String> adapter1  = new ArrayAdapter<String>(
		            this,android.R.layout.simple_spinner_item,alrt.showAllJobid(this));
		    adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
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
				msg[0]=jobid;
		    	pw.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	};

	private void initiateStatusWindow(int p) {
	    try {
	    	
			View lay=(View)findViewById(R.id.rptList);
			Rect r=new Rect();
			lay.getGlobalVisibleRect(r);
	        LayoutInflater inflater = (LayoutInflater) rpt_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View layout = inflater.inflate(R.layout.popup_window,(ViewGroup) findViewById(R.id.popup_element));
	        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	        pw = new PopupWindow(layout, 280, layout.getMeasuredHeight(), true);//280,70
	        pw.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        pw.setOutsideTouchable(true);
	        pw.showAtLocation(layout, Gravity.TOP, 0,r.top+p);//109+p+(p/56)*1//110
	        ImageButton cancelButton = (ImageButton) layout.findViewById(R.id.imageButton1);
	        cancelButton.setOnClickListener(cancel_button_status_listener);
	        s1=(Spinner)layout.findViewById(R.id.trvJob1);
		    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		            this, R.array.planets_array, android.R.layout.simple_spinner_item);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    s1.setAdapter(adapter);
		    s1.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					jobstatus=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	    } catch (Exception e) {
	    	Toast.makeText(getApplicationContext(), e.toString() + "/" + e.getMessage(), Toast.LENGTH_LONG).show();
	    }
	}
	
	private OnClickListener cancel_button_status_listener = new OnClickListener() {
	    public void onClick(View v) {
	    	try {
	    		View vw1=lst.getChildAt(1);
				TextView tx =(TextView)vw1.findViewById(R.id.det);
				tx.setText(jobstatus);
				msg[1]=jobstatus;
		    	pw.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	};

	
/*	private void initiateFoodingWindow(int p) {
	    try {
			View lay=(View)findViewById(R.id.rptList);
			Rect r=new Rect();
			lay.getGlobalVisibleRect(r);

	        LayoutInflater inflater = (LayoutInflater) rpt_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        layout = inflater.inflate(R.layout.fooding,(ViewGroup) findViewById(R.id.popup_element));
	        
	        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	        pw = new PopupWindow(layout, 280, layout.getMeasuredHeight(), true);//280,70
	        pw.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        pw.setOutsideTouchable(true);
	        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	        pw.showAtLocation(layout, Gravity.TOP, 0, r.top+p);//109//110
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
				msg[2]="Rs. " + et.getText();
		    	pw.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	};
*/

/*	private void initiateLodgingWindow(int p) {
	    try {
			View lay=(View)findViewById(R.id.rptList);
			Rect r=new Rect();
			lay.getGlobalVisibleRect(r);

	        LayoutInflater inflater = (LayoutInflater) rpt_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        layout = inflater.inflate(R.layout.lodging,(ViewGroup) findViewById(R.id.popup_element));
	        
	        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	        pw = new PopupWindow(layout, 280, layout.getMeasuredHeight(), true);//280,70
	        
	        pw.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
	        pw.setBackgroundDrawable(new BitmapDrawable());
	        pw.setOutsideTouchable(true);
	        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	        pw.showAtLocation(layout, Gravity.TOP, 0, r.top+p);//110
	        
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
				msg[3]="Rs. " + et.getText();
		    	pw.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	};
*/
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            try {
            	jobid=parent.getItemAtPosition(pos).toString();
  		} catch (Exception e) {
  			Toast.makeText(getApplicationContext(), e.getMessage(), 3000).show();
  		}
        }
    	@Override
        public void onNothingSelected(AdapterView parent) {
        }
    }

	
}

