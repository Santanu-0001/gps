package com.gps_3;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class surveydetails extends Activity implements OnClickListener{

	Spinner spinner=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surveydetails);
		
		
//	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//	            this, R.array.mode_array, android.R.layout.simple_spinner_item);
//	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	    spinner.setAdapter(adapter);
	    //spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
