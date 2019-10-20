package com.gps_3;



import java.io.ObjectOutputStream.PutField;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class barcode extends Activity implements OnClickListener {
	private Button b1;
	private Button b2;
	private EditText ed=null;
	private ImageButton b3=null;
	private TextView tx=null;
	//private TextView tx1=null;
	
	
/*	static final String[] COUNTRIES = new String[] {
		  "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
		  "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
		  "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
		  "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
		  "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
		  "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
		  "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
		  "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
		  "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
		  "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
		  "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
		  "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
		  "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
		  "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
		  "Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia",
		  "French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
		  "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
		  "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong", "Hungary",
		  "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
		  "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
		  "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
		  "Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
		  "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
		  "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
		  "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand",
		  "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas",
		  "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru",
		  "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
		  "Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena",
		  "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon",
		  "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal",
		  "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
		  "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Korea",
		  "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden",
		  "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas",
		  "The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
		  "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
		  "Ukraine", "United Arab Emirates", "United Kingdom",
		  "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
		  "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara",
		  "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"
		};*/
	
	
	
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode);
        
/********************** auto complete text view **********************/        
//        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES);
//        textView.setAdapter(adapter);
/***************************************************/
        

        b1=(Button)findViewById(R.id.barAdd);
        b1.setOnClickListener(this);
        b2=(Button)findViewById(R.id.barCancel);
        b2.setOnClickListener(this);
      ed=(EditText)findViewById(R.id.barRemarks);
      tx=(TextView)findViewById(R.id.barTxt);
      
      b3=(ImageButton)findViewById(R.id.scan);
      b3.setOnClickListener(this);
      //tx.setText(getIntent().getExtras().getString("Title"));
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.barAdd:
			Intent intent1=new Intent();
			intent1.putExtra("remarks",  ed.getText().toString());
		    intent1.putExtra("barcode", tx.getText());
		    setResult(RESULT_OK, intent1);
		    finish();
			break;
		case R.id.barCancel:
			super.finish();
			break;
		case R.id.scan:
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "ONE_D_MODE");//QR_CODE_MODE//DATA_MATRIX_MODE
			intent.putExtra("RESULT_DISPLAY_DURATION_MS", 0L);
			//intent.putExtra("ENCODE_SHOW_CONTENTS", "true");
//			intent.putExtra("PROMPT_MESSAGE", "");
			//intent.putExtra("SCAN_WIDTH", 3000L);
			//intent.putExtra("SCAN_HEIGHT", 3000L);
			
			startActivityForResult(intent, 0);
			break;

		default:
			break;
		}
	}
	@Override
	public void onBackPressed() 
	{
		//Toast.makeText(this, "close", Toast.LENGTH_LONG).show();
		if(start.closeEnable)
			super.onBackPressed();
	}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	         String contents = intent.getStringExtra("SCAN_RESULT");

    	         	
    	         tx.setText(contents);
    	         
//					try
//					{
//    	         	alrt.insertValue(contents, "0.00");
//    	         	adapter.add(alrt.showValue(this));
//					}
//					catch (Throwable e) {
//						Toast.makeText(this, e.getMessage(), 3000).show();
//						// TODO: handle exception
//					}
					//adapter.add(e_barcode.getText().toString() + "/" +(e_price.getText().toString()) + "/");
					

    	         // Handle successful scan
    	      } else if (resultCode == RESULT_CANCELED) {
    	         // Handle cancel
    	      }
    	   }
    }

}