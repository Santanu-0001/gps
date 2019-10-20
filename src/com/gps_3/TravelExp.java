package com.gps_3;


import android.app.Activity;
import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
 
public class TravelExp extends Activity {
 
	static final String[] item_desc = 
               new String[] { "JobID", "Date", "Fooding", "Lodging","Travel","Other"};
	static final int[] image = 
            new int[] {R.drawable.jobid,R.drawable.date,R.drawable.fooding,R.drawable.hotel,R.drawable.travelexp,R.drawable.otherexp };
	static final String[] msg = 
            new String[] {"Select Job ID","Set Date","Fooding Expense","Lodging Expense","Travel Expense","Other Expenses"};

	private ListView lst=null;
	private LayoutAnimationController controller=null;
	private LayoutAnimationController controller1=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
//		ListView lv= getListView();
//		TextView tv=new TextView(getApplicationContext());
//		tv.setText("santanu");
//		lv.addHeaderView(tv);
//		try {
//			TextView tv1=new TextView(getApplicationContext());
//			tv1.setText("bhattacharya");
//			lv.addFooterView(tv1);
//			lv.addView(tv1);
			
//		} catch (Exception e) {
//			// TODO: handle exception
//			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//		}

		
//		setListAdapter(new TravelArrayAdapter(this, item_desc,image));
//
//		getListView().setTextFilterEnabled(true);
//		  
//		  LayoutAnimationController controller  = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
//		  getListView().setLayoutAnimation(controller);
		
		setContentView(R.layout.test_layout);
		
		lst=(ListView)findViewById(R.id.list11);
		TravelArrayAdapter ta=new TravelArrayAdapter(this, item_desc,image,msg);
//		ta.setDropDownViewResource(android.R.layout.preference_category);
		lst.setAdapter(ta);
		
		
//		lst.setTextFilterEnabled(true);
//		controller  = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
//		lst.setLayoutAnimation(controller);

//		controller1  = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller1);
		  
		  lst.setOnItemClickListener(new OnItemClickListener() {
			  
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
//				Toast.makeText(getApplicationContext(), "abc", Toast.LENGTH_LONG).show();
				
//				lst.setLayoutAnimation(controller1);
				lst.setVisibility(8);
			}
		});

			TextView tx=(TextView)findViewById(R.id.test_text2);
			tx.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

//					lst.setLayoutAnimation(controller);
					lst.setVisibility(0);
				}
			});

		  
//		  TextView tx=(TextView)findViewById(R.id.test_text2);
//		  Animation an=AnimationUtils.loadAnimation(this, R.anim.scale);
//		  //tx.setAnimation(an);
//		  tx.clearAnimation();
//		  tx.startAnimation(an);

//		  ListView lv = getListView();
		
		
//		lv.setItemsCanFocus(true);
//		ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.red));
//		lv.setDivider(sage);
//		lv.setDividerHeight(10);

	}
 
//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
// 
//		//get selected items
//		
//		String selectedValue = (String) getListAdapter().getItem(position);
//		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
// 
//	}
 
}

