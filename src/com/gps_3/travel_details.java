package com.gps_3;

import com.gps_3.R.color;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html.TagHandler;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class travel_details extends TabActivity implements View.OnClickListener {
    /** Called when the activity is first created. */
	private Button bt;
	private TabHost tabHost;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details);
        Resources res = getResources(); // Resource object to get Drawables
        tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        try {
            intent = new Intent().setClass(this, travel_entry.class);

            // Initialize a TabSpec for each tab and add it to the TabHost
            spec = tabHost.newTabSpec("artists").setIndicator("Details",
                              res.getDrawable(R.drawable.ic_tab_page1))
                          .setContent(intent);
            tabHost.addTab(spec);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}

        // Do the same for the other tabs
        try {
            intent = new Intent().setClass(this, travel_summary.class);
            spec = tabHost.newTabSpec("albums").setIndicator("Summary",
                              res.getDrawable(R.drawable.ic_tab_page2))
                          .setContent(intent);
            				
            tabHost.addTab(spec);

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}

        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_select);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_select_r);
        try {
            TextView tv=(TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
            tv.setTextColor(this.getResources().getColorStateList(R.color.text_tab_indicator));
            tv=(TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
            tv.setTextColor(this.getResources().getColorStateList(R.color.text_tab_indicator));
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
        
        tabHost.setCurrentTab(0);
        

    }
    
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}