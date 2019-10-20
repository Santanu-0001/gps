package com.gps_3;



import java.util.Arrays;
import java.util.List;

import com.gps_3.gps_4.MyOnItemSelectedListener;

import android.R.string;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class transport extends ListActivity implements OnClickListener{

	private String dt_val=null;
	private String jobid=null;
	private List<String> values=null;
	ArrayAdapter adapter1=null;
	private travelDataSource trv;
	Button btnadd=null;
	EditText edfrm=null;
	EditText edto=null;
	EditText edamnt=null;
	Spinner spinner=null;
	String spinner_val=null;
	private Cursor cr_edit=null;
	private String row_id=null;
	List<String> lst_mode=null;
	private Boolean mode_edit=false;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport);
        btnadd= (Button) findViewById(R.id.trpAdd);
        btnadd.setOnClickListener(this);
        edfrm= (EditText) findViewById(R.id.trpFrom);
        edto= (EditText) findViewById(R.id.trpTo);
        edamnt= (EditText) findViewById(R.id.trpAmount);
        
        spinner = (Spinner) findViewById(R.id.trpMode);
	    
        //int str= ; 
        String[] str=getResources().getStringArray(R.array.mode_array);
        lst_mode=Arrays.asList(str);
        
        
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.mode_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

	    
	    trv=new travelDataSource(this);
        trv.open();
        
	    Bundle extras = getIntent().getExtras();
	    if(extras != null){
	        dt_val=extras.getString("dt_val");
	        jobid=extras.getString("jobid");
//	        Toast.makeText(getApplicationContext(), dt_val, 3000).show();
	    }

	    values = trv.transport_getAllValue(dt_val,jobid); //new ArrayList<String>();//datasource.getAllComments();
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.preference_category, values);//.preference_category
		setListAdapter(adapter1);
		
		

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.trpAdd:
			try {
				String str1,str2,str3,str4;
				str1=edfrm.getText().toString();
				str2=edto.getText().toString();
				str4=edamnt.getText().toString();
				str3=spinner.getSelectedItem().toString();
				if(!str1.equals("") & !str2.equals("") & !str3.equals(""))
				{
					if(!mode_edit)
					{
						trv.transport_insertValue(jobid, dt_val, str1, str2, str3, Integer.parseInt(str4));
						adapter1.add(trv.transport_showLastValue());
						//Toast.makeText(getApplicationContext(), "Inserted", 3000).show();
					}
					else
					{
						trv.transport_update(row_id,str1,str2,Integer.parseInt(str4),str3);
						
						values = trv.transport_getValue(jobid,dt_val);
						adapter1 = new ArrayAdapter<String>(getApplicationContext(),
		        				android.R.layout.preference_category, values);
						setListAdapter(adapter1);
						mode_edit=false;
						btnadd.setText("Add");

					}
					
					edfrm.setText("");
					edto.setText("");
					edamnt.setText("");
					spinner.setSelection(0);
					

				}
				else
					Toast.makeText(getApplicationContext(), "Enter all data", 3000).show();
				
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(), 3000).show();
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
	    		spinner_val=parent.getItemAtPosition(pos).toString();
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
		btnadd.setText("Edit");
		int i=0;
		i=c.indexOf(">",0);
		String jid=c.substring(0,i);
		//Toast.makeText(this, c.substring(0,i), 3000).show();
		cr_edit=trv.transport_showJobDet(jid);
		row_id=jid;
		//dt.setText(cr_edit.getString(2));
		int i1=0;
		try {
			edfrm.setText(cr_edit.getString(3));
			edto.setText(cr_edit.getString(4));
	        int pos=lst_mode.indexOf(cr_edit.getString(5));
	        spinner.setSelection(pos);
			edamnt.setText(Integer.toString(cr_edit.getInt(6)));
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(),  e.getMessage(), 3000).show();
		}

	}
}
