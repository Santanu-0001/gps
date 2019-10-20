package com.gps_3;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class otherexp extends ListActivity implements OnClickListener{

	private String dt_val=null;
	Button btnadd=null;
	EditText editem=null;
	EditText edamnt=null;
	private List<String> values=null;
	ArrayAdapter adapter1=null;
	private travelDataSource trv;
	private Boolean mode_edit=false;
	private String jobid=null;
	private String row_id=null;
	private Cursor cr_edit=null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherexp);
        btnadd= (Button) findViewById(R.id.othAdd);
        btnadd.setOnClickListener(this);
        editem= (EditText) findViewById(R.id.othItem);
        edamnt= (EditText) findViewById(R.id.othAmount);

        trv=new travelDataSource(this);
        trv.open();
        
        Bundle extras = getIntent().getExtras();
	    if(extras != null){
	        dt_val=extras.getString("dt_val");
	        jobid=extras.getString("jobid");
	        //Toast.makeText(getApplicationContext(), dt_val, 3000).show();
	    }
	    values = trv.otherexp_getAllValue(dt_val,jobid); //new ArrayList<String>();//datasource.getAllComments();
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.preference_category, values);//.preference_category
		setListAdapter(adapter1);

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.othAdd:
			try {
				String str1,str2,str3,str4;
				str1=editem.getText().toString();
				str2=edamnt.getText().toString();
				
				
				if(!str1.equals("") & !str2.equals("") )
				{
					if(!mode_edit)
					{
						trv.otherexp_insertValue(jobid, dt_val, str1, Integer.parseInt(str2));
						adapter1.add(trv.otherexp_showLastValue());
						//Toast.makeText(getApplicationContext(), "Inserted", 3000).show();
					}
					else
					{
						trv.otherexp_update(row_id,str1,Integer.parseInt(str2));
						
						values = trv.otherexp_getValue(jobid,dt_val);
						adapter1 = new ArrayAdapter<String>(getApplicationContext(),
		        				android.R.layout.preference_category, values);
						setListAdapter(adapter1);
						mode_edit=false;
						btnadd.setText("Add");

					}
					
					editem.setText("");
					edamnt.setText("");
					
					
					

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
		cr_edit=trv.otherexp_showJobDet(jid);
		row_id=jid;
		//dt.setText(cr_edit.getString(2));
		int i1=0;
		try {
			editem.setText(cr_edit.getString(3));
			edamnt.setText(Integer.toString(cr_edit.getInt(4)));
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(),  e.getMessage(), 3000).show();
		}

	}

}
