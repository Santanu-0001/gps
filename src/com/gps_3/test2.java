package com.gps_3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class test2 extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test2);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	public void test_fn1(View v)
	{
		Context cnt=getApplicationContext();
		Toast t=Toast.makeText(cnt, "hello", Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP, 0, 0);
		LinearLayout ll=new LinearLayout(cnt);
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView tv=new TextView(cnt);
		tv.setText("santanu");
		int w=LinearLayout.LayoutParams.FILL_PARENT;
		int h=LinearLayout.LayoutParams.WRAP_CONTENT;
		ll.addView(tv,new LinearLayout.LayoutParams(w, h));
		ll.setPadding(40, 50, 0, 50);
		t.setView(ll);
		t.show();
	}
	
}
