package com.gps_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class RptArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final String[] msg;
//	private final int[] image_val;
//	private final String[] values1=new String[] { "Android1", "iOS1", "WindowsMobile1", "Blackberry1"};
 
	public RptArrayAdapter(Context context, String[] values,String[] msg) {
		super(context, R.layout.list_rpt, values);
		this.context = context;
		this.values = values;
//		this.image_val=image_val;
		this.msg=msg;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_rpt, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView textView1 = (TextView) rowView.findViewById(R.id.det);
//		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		
		textView.setText(values[position]);
//		textView1.setText(msg[position]);
		
		
//		String s = values[position];
//		imageView.setImageResource(image_val[position]);
//		System.out.println(s);
		return rowView;
	}
	
	@Override
	public void add(String object) {
		// TODO Auto-generated method stub
		super.add(object);
		
	}
}

