package com.gps_3;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class test extends Activity implements OnClickListener {
	private Button b1;
	private TextView tv1;
	int listViewTouchAction=0;
	ListView listView=null;
	ListView listView1=null;
	ArrayAdapter<String> adapter=null;
	ArrayList<String> listItems=null;
	//String[] values1=null;
	//Button b1=null;
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        //b1=(Button)findViewById(R.id.imageButton1);
        //b1.setOnClickListener(this);
        
        listView = (ListView) findViewById(R.id.list1);
        listView1 = (ListView) findViewById(R.id.list2);
        b1 = (Button) findViewById(R.id.scrollbar_1);
        b1.setOnClickListener(this);
        
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
        	"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        	"Linux", "OS/2" };

        String[] values1= new String[] { "Android", "iPhone", "WindowsMobile",
            	"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            	"Linux", "OS/2" };

        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the View to which the data is written
        // Forth - the Array of data
        
        
        try{
//        	tv1=new TextView(this);
//        	tv1.setText("santanu");
//        listView.addHeaderView(tv1);
        	listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
        	android.R.layout.simple_list_item_multiple_choice, values);//, android.R.id.text1

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
            	android.R.layout.preference_category, values1);//, android.R.id.text1

        // Assign adapter to ListView
        
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        //listView.setHeaderDividersEnabled(true);

        
        setListViewScrollable(listView);
        
        
        //listView.setItemsCanFocus(false);
        
        listView1.setAdapter(adapter1);
        
        setListViewScrollable(listView1);        
        
        }
        catch (Exception e) {
        	Toast.makeText(this, e.getMessage(), 3000).show();
			// TODO: handle exception
		}
        
        
        //listView1.requestFocus();
        
        //listView1.setItemsCanFocus(false);
        
        //listView1.setVisibility(View.GONE); 
	}
//	int listViewTouchAction=0;
    private void setListViewScrollable(final ListView list) {
    	//list.setClickable(true);
    	
    list.setOnTouchListener(new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            listViewTouchAction = event.getAction();
            switch (listViewTouchAction ) {
			case MotionEvent.ACTION_DOWN://.ACTION_MOVE:
				list.scrollBy(0, 3);
				break;
			case MotionEvent.ACTION_UP://.ACTION_MOVE:
				list.scrollBy(0, -3);
				break;


			default:
				break;
			}
//            if (listViewTouchAction == MotionEvent.ACTION_MOVE)
//            {
//                list.scrollBy(0, 3);//1
//            }
            return false;
        }
    });
    list.setOnScrollListener(new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view,
                int scrollState) {
        	
        }

        
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                int visibleItemCount, int totalItemCount) {
//            if (listViewTouchAction == MotionEvent.ACTION_MOVE)
//            {
//                list.scrollBy(0, -3);//1
//            }
        }
    });
    list.setOnItemClickListener(new OnItemClickListener() {
    	   @Override
    	   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
    	      Object listItem = list.getItemAtPosition(position);
    	      Toast.makeText(getApplicationContext(), "as"+position, 3000).show();
    	      
    	   } 
    	});

    
//    list.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Toast.makeText(getApplicationContext(), "as", 3000).show();
//		}
//	});
}

	
	@Override
	public void onClick(View arg0) {
		
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.scrollbar_1:
			//Toast.makeText(this, "sample", 3000).show();
			try {
				ArrayAdapter<String> ad = (ArrayAdapter<String>) listView.getAdapter();
				listItems.add("santanu");
				ad.notifyDataSetChanged();
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, e.getMessage(), 3000).show();
				
			}
			
			break;

		default:
			break;
		}
	}
}