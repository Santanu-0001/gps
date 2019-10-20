package com.gps_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class TravelArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final String[] msg;
	private final int[] image_val;
//	private final String[] values1=new String[] { "Android1", "iOS1", "WindowsMobile1", "Blackberry1"};
 
	public TravelArrayAdapter(Context context, String[] values,int[] image_val,String[] msg) {
		super(context, R.layout.list_mobile, values);
		this.context = context;
		this.values = values;
		this.image_val=image_val;
		this.msg=msg;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_mobile, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView textView1 = (TextView) rowView.findViewById(R.id.det);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
//		TextView textView1 = (TextView) rowView.findViewById(R.id.label1);
		textView.setText(values[position]);
		textView1.setText(msg[position]);
//		textView1.setText(values1[position]);
 
		// Change icon based on name
		String s = values[position];
		imageView.setImageResource(image_val[position]);
//		String s1 = values1[position];
		
		System.out.println(s);
//		System.out.println(s1);
		
//		if (s.equals("WindowsMobile")) {
//			imageView.setImageResource(R.drawable.fooding);
//		} else if (s.equals("iOS")) {
//			imageView.setImageResource(R.drawable.hotel);
//		} else if (s.equals("Blackberry")) {
//			imageView.setImageResource(R.drawable.rihanna);
//		} else {
//			imageView.setImageResource(R.drawable.jobid);
//		}
 
		return rowView;
	}
}

