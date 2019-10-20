package com.gps_3;





import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EmptyStackException;
import java.util.List;

import com.gps_3.acc.MyOnItemSelectedListener;

import android.R.color;
import android.R.string;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.provider.Contacts.People;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class gps_4 extends ListActivity implements OnClickListener{
	
	
	public static Context cnt1=null;
	static final private int MENU_ITEM=Menu.FIRST;
	public static final int PICK_CONTACT = 1;
	final static int RQS_GET_IMAGE = 1;
	final static int RQS_GET_BARCODE = 2;
	ImageView image=null;
	TextView tv=null;
	EditText jobid=null;
	EditText message=null;
	Bitmap bitmap=null;
	Uri imageUri=null;
	Spinner s1=null;
	ArrayAdapter<String> adapter_list=null;
	
	
	//ProgressBar pb=null;
	private alertDataSource alrt;
	public static String jstat=null; 
	public static String jid=null;
	
	private int myYear, myMonth, myDay;
	 static final int ID_DATEPICKER = 0;
	 
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_4);
        cnt1=getApplicationContext();
        Button b1= (Button)findViewById(R.id.button1);
		b1.setOnClickListener(this);
		//b1.setBackgroundColor(color.transparent);

		Button b2= (Button)findViewById(R.id.button2);
		b2.setOnClickListener(this);

//		Button b3= (Button)findViewById(R.id.button3);
//		b3.setOnClickListener(datePickerButtonOnClickListener);

		Button b4= (Button)findViewById(R.id.button4);
		b4.setOnClickListener(this);

		//b2.setBackgroundColor(color.transparent);
		//pb=(ProgressBar)findViewById(R.id.progressBar1);
		
		//pb.setVisibility(android.view.View.INVISIBLE);
		
		image= (ImageView)findViewById(R.id.imageView1);
		
		//tv= (TextView)findViewById(R.id.textView1);//to display image location from browser
		
		jobid= (EditText)findViewById(R.id.editText1);
		message= (EditText)findViewById(R.id.editText2);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.planets_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
	    alrt=new alertDataSource(this);
        alrt.open();
        
	    s1=(Spinner)findViewById(R.id.spinner2);
	    ArrayAdapter<String> adapter1  = new ArrayAdapter<String>(
	            this,android.R.layout.simple_spinner_item,alrt.showAllAccJobid(this));
	    adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
	    s1.setAdapter(adapter1);
	    s1.setOnItemSelectedListener(new MyOnItemSelectedListener_1());
	    
	    try
	    {
	    List<String> values =new ArrayList<String>();//alrt.showAllValue(this); //new ArrayList<String>();//datasource.getAllComments();
        
		adapter_list = new ArrayAdapter<String>(this,
				android.R.layout.preference_category, values);//.preference_category
		
		setListAdapter(adapter_list);
		
	    }
	    
	    catch (Exception e) {
			// TODO: handle exception
	    	Toast.makeText(this, e.getMessage(), 3000).show();
		}
	    //adapter_list.add("ABCD/EFGH");

    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	super.onListItemClick(l, v, position, id);
    	try{
    		final String c = (String)l.getItemAtPosition(position);
    		
    		new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Barcode Application")
            .setMessage("Want to delete " + c + "?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter_list.remove(c);
                }

            })
            .setNegativeButton("No", null)
            .show();

    		
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		Toast.makeText(this, e.getMessage(), 3000).show();
		}
    }

    
    private Button.OnClickListener datePickerButtonOnClickListener = new Button.OnClickListener(){

		  @Override
		  public void onClick(View v) {
		   // TODO Auto-generated method stub
		   final Calendar c = Calendar.getInstance();
			   myYear = c.get(Calendar.YEAR);
			   myMonth = c.get(Calendar.MONTH);
			   myDay = c.get(Calendar.DAY_OF_MONTH);
		   showDialog(ID_DATEPICKER);
		  }
   };

   private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener(){
    @Override
    public void onDateSet(DatePicker view, int year, 
      int monthOfYear, int dayOfMonth) {
	     String date = "Year: " + String.valueOf(year) + "\n"
				      + "Month: " + String.valueOf(monthOfYear+1) + "\n"
				      + "Day: " + String.valueOf(dayOfMonth);
     Toast.makeText(gps_4.this, date,Toast.LENGTH_LONG).show();
    }

	
  };

   
   @Override
   protected Dialog onCreateDialog(int id) {
    // TODO Auto-generated method stub
    switch(id){
     case ID_DATEPICKER:
      Toast.makeText(gps_4.this, 
        "- onCreateDialog -", 
        Toast.LENGTH_LONG).show();
		
		return new DatePickerDialog(gps_4.this,
    		  myDateSetListener,
    		  myYear, myMonth, myDay);
     default:
      return null;
    }
   } 
   
   
   public class MyOnItemSelectedListener implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          //Toast.makeText(parent.getContext(), "The planet is " +
          //    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
          gps_4.jstat=parent.getItemAtPosition(pos).toString();
        }
    	@Override
        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }

    public class MyOnItemSelectedListener_1 implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          //Toast.makeText(parent.getContext(), "The planet is " +
          //    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
          gps_4.jid=parent.getItemAtPosition(pos).toString();
        }
    	@Override
        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }

    @Override
	public void onActivityResult(int reqCode, int resCode, Intent data)
	{
		super.onActivityResult(reqCode, resCode, data);
		//Toast.makeText(cnt1, "Result..."+data.getDataString(), Toast.LENGTH_LONG).show();
		//Toast.makeText(cnt1, "Result..."+str.length(), Toast.LENGTH_LONG).show();
		try{
		//if(data.getDataString().length()>0)
			
			//tv.setText(data.getDataString());//to display image location from browser
			
		switch(reqCode)
		{
		
		case (RQS_GET_IMAGE):
		{
		imageUri = data.getData();
		//if(imageUri.toString().length()>0)
		{
	    String imageFile = imageUri.toString();
	    //Uri u=Uri.parse(imageFile);

	    
	    
	    /*********************  add Image Name ***********/ 
//        String[] projection = { MediaStore.Images.ImageColumns.DATA,MediaStore.Images.ImageColumns.DISPLAY_NAME};
//
//        Cursor c = managedQuery(imageUri, projection, null, null, null);
//        if (c!=null && c.moveToFirst()) {
//                String column1Value = c.getString(1);
//                adapter_list.add(column1Value);
//        }
	    /*********************  add Image Name ***********/
	    
	    //ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
	    
	    Bitmap bm = ShrinkBitmap(imageUri, 300, 300);
	    image.setImageBitmap(bm);
		}
		}
		break;
		case (RQS_GET_BARCODE):
			//Toast.makeText(getApplicationContext(), data.getExtras().getString("Title"), 3000).show();
			String bar=data.getExtras().getString("barcode");
			String rem=data.getExtras().getString("remarks");
			
			String str=bar+"/"+rem;
			if(!bar.equals(""))
				adapter_list.add(str);
		
			//message.setText();
		
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	          
	}
    
    /*
    @Override
    protected void onResume() {
    	super.onResume();
    	Toast.makeText(cnt1, "Resume...", Toast.LENGTH_LONG).show();
    	
    		String str1=" ";
    		if(dlg1.str!=null)
    		{
    		str1=dlg1.str.toString();
    		if(str1.length()>0)
    			Toast.makeText(cnt1, str1, Toast.LENGTH_LONG).show();
    		}
    	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
		int groupId=0;
		int menuItemId=MENU_ITEM;
		int menuItemOrder=Menu.NONE;
		int menuItemText=R.string.menu_item1;
		
		MenuItem menuItem1=menu.add(groupId, menuItemId++, menuItemOrder, menuItemText);
		//menuItemText=R.string.menu_item1;
		menuItem1=menu.add(groupId, menuItemId++, menuItemOrder, "abc");
		
		
		
		//MenuItem menuItem2=menu.add(groupId, menuItemId, menuItemOrder, menuItemText);
    	
		return true;
    	
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
		switch(item.getItemId())
		{
		case MENU_ITEM:
			Toast.makeText(this, "Menu 1 selected", Toast.LENGTH_LONG).show();
			return true;
		}
    	
		
    	return false;
    	
    }
    
    */

    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		Context cnt=gps_4.this;//getApplicationContext();
		//Toast.makeText(this, "hello", 3000).show();
		try
		{
			switch(id)
			{
			case R.id.button1:
				
				//send2srv("10/"+jobid.getText().toString()+"/"+message.getText().toString()+"/");
				TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				String iemi = telephonyManager.getDeviceId().toString();
				final String str=iemi + "/" + gps_4.jid + "/" + message.getText().toString()+ "/" + jstat + "/";
				//String str=iemi + "/" + jobid.getText().toString() + "/" + message.getText().toString()+ "/" + jstat + "/";
				//iemi + "/" + 
				
				//Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();


				final ProgressDialog dialog = ProgressDialog.show(this, "", "Uploading. Please wait...", true,true);
				//Toast.makeText(getApplicationContext(), str, 3000).show();
		        Thread thread=new Thread(new Runnable(){

		        public void run(){
		        	try {
		        		
		        		//show_alert(str);
		        		send2srvimgTCP(imageUri,str);	
					} catch (Exception e) {
						// TODO: handle exception
						//Toast.makeText(getApplicationContext(), e.getMessage(), 3000).show();
						try {
							show_alert(e.getMessage());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
		        	
		            //LoadData();
		            runOnUiThread(new Runnable(){

		                @Override
		                public void run() {
		                    if(dialog.isShowing())
		                        dialog.dismiss();

		                }

		            });
		        }

		        });
		        thread.start();

				//send2srvimgTCP(imageUri,str);

	   	    	   
				
				
				
				/*Intent info = new Intent(cnt, dlg1.class);
				try{
				cnt.startActivity(info);
				}
				catch(Exception e){
					Toast.makeText(cnt, e.toString(), Toast.LENGTH_LONG).show();
				}*/
				break;
				
			case R.id.button2:
			{
				
				
				try
				{
					Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, RQS_GET_IMAGE);	
					
//					Intent i = new Intent();
//					i.setClassName("com.android.calendar","com.android.calendar.AgendaActivity");
//					startActivity(i);

				}
				catch (Exception e) {
					Toast.makeText(cnt, e.toString(), Toast.LENGTH_LONG).show();
					// TODO: handle exception
				}
			}
			break;
			case R.id.button4:
				try {
					Intent myIntent = new Intent(gps_4.this,barcode.class);
					myIntent.putExtra("remarks", "");
					myIntent.putExtra("barcode", "");
					//gps_4.this.startActivity(myIntent);
					startActivityForResult(myIntent, RQS_GET_BARCODE);
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(this,e.getMessage(), 3000).show();
				}
				break;
		}
		}
		catch (Exception e) {
			Toast.makeText(cnt, e.toString(), Toast.LENGTH_LONG).show();
			// TODO: handle exception
		}
	}
	
	Bitmap ShrinkBitmap(Uri uri, int width, int height)
	{
	       
			BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
			bmpFactoryOptions.inJustDecodeBounds = true;

			bitmap = null;;
			try 
			{
				//Toast.makeText(cnt1, BitmapFactory.decodeStream(getContentResolver().openInputStream(uri)).toString(), Toast.LENGTH_LONG).show();
				
			   bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
			    
			   			   
			   
			   
			   
			   int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
			   int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);
	             
	           if (heightRatio > 1 || widthRatio > 1)
	           {
		           if (heightRatio > widthRatio)
		           {
		        	   bmpFactoryOptions.inSampleSize = heightRatio;
		           }
		           else 
		           {
		        	   bmpFactoryOptions.inSampleSize = widthRatio;
		           }
	           }
	             
	            bmpFactoryOptions.inJustDecodeBounds = false;
	            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

  return bitmap;
   }
	
	public static void show_alert(final String str)  throws IOException
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			Toast.makeText(cnt1, str, Toast.LENGTH_LONG).show();
    		//vi.vibrate(100);
			}
			});
	}

	public void send2srv(String msg){
		try{
			//String iemi;
			//TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			//iemi=telephonyManager.getDeviceId();
            //msg=iemi+msg;
            //String messageStr="s";
    	    //String s1="santanu";
    	    int server_port = 10400;
    	    
    	    DatagramSocket s = new DatagramSocket(10300);
    	    InetAddress local = InetAddress.getByName("180.151.96.155");//"180.151.96.155" "192.168.1.114"//);//59.93.248.125
    	    int msg_length=msg.length();
    	    byte[] message = msg.getBytes();//messageStr.getBytes();
    	    //byte[] message2 = s1.getBytes();
    	    //byte[] message=new byte[message1.length+message2.length];
    	    //message = message1 + message2;
    	    DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
    	    s.send(p);
    	    s.close();
    	   
    		}
    		catch (Exception e) {
    			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
				// TODO: handle exception
			}
	
    }

	public void send2srvimg(Bitmap bmp)
	{
		try{
			//String iemi;
			//TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			//iemi=telephonyManager.getDeviceId();
            //msg=iemi+msg;
            //String messageStr="s";
    	    //String s1="santanu";
    	    
			int server_port = 10400;
    	    
    	    DatagramSocket s = new DatagramSocket(10300);
    	    InetAddress local = InetAddress.getByName("180.151.96.155");//"180.151.96.155" "192.168.1.114");//59.93.248.125
    	    
    	    
    	    int size = bmp.getRowBytes() * bmp.getHeight();
			   ByteBuffer b = ByteBuffer.allocate(size);
			   bmp.copyPixelsToBuffer(b);
			   byte[] bytes = new byte[size];
			   
			   try {
			       bytes=b.array();//.get(bytes, 0, bytes.length);
			       //Toast.makeText(cnt1, bytes.toString() ,Toast.LENGTH_LONG).show();
			   }
			   catch (Exception e)
			   {
				   //Toast.makeText(cnt1, e.toString(),Toast.LENGTH_LONG).show();
			       //always happens
			   }
    	    
    	    
    	    int msg_length= bytes.length;
    	    //byte[] message =msg.getBytes();//messageStr.getBytes();
    	    
    	    //Bitmap.Config bc=bmp.getConfig();
    	   
    	    
    	    //byte[] message2 = s1.getBytes();
    	    //byte[] message=new byte[message1.length+message2.length];
    	    //message = message1 + message2;
    	    int i=0;
    	    int pack=msg_length/60000;
    	    int rem=msg_length-(pack*60000);

    	    byte[] bt=new byte[65000];	
	    	
	    	
	    	Toast.makeText(this, "s " + pack, Toast.LENGTH_LONG).show();
	    	ByteArrayInputStream ba=null;
    	    //i=1;
    	    for(i=0;i<pack;i++)
    	    {
    	    	ba=new ByteArrayInputStream(bytes,i*60000+1,60000);
    	    	ba.read(bt, 0 , 60000);
    	    	Toast.makeText(this, "s " + i*60000+1, Toast.LENGTH_LONG).show();
    	    	DatagramPacket p = new DatagramPacket(bt, 6,local,server_port);
        	    s.send(p);
        	    //s.close();
        	    
    	    	
    	    	//Toast.makeText(this, "s " + (i*60000+1)+ "/" + ((i+1)*60000), Toast.LENGTH_LONG).show();
    	    }
    	    ba=new ByteArrayInputStream(bytes,i*60000+1,msg_length-(i*60000));
    	    ba.read(bt, 0, msg_length-(i*60000));
    	    
    	    Toast.makeText(this, "s " + bt.toString(), Toast.LENGTH_LONG).show();
    		
    	    DatagramPacket p = new DatagramPacket(bt,6 ,local,server_port);//msg_length-(i*60000)
    	    s.send(p);
    	    
    	    s.close();	
    	    
    	    
    	    //Toast.makeText(this, "s " + (i*60000+1)+ "/" + msg_length, Toast.LENGTH_LONG).show();
    	    //Toast.makeText(this, "s " + pack + "/" + rem, Toast.LENGTH_LONG).show();
    	    
    	    
    	    
    	    	
    	    /*	
    	    DatagramPacket p = new DatagramPacket(bytes, 60000,local,server_port);
    	    s.send(p);
    	    s.close();
    	   	*/
    	    
    		}
    		catch (Exception e) {
    			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
				// TODO: handle exception
			}
	
    }

	public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


	public void send2srvimgTCP_1(Uri uri,String job_desc)//Bitmap bmp)
	{
		//try{
    	    
			   
			   
			   
    		try
    		{
    		   /*
    			Bitmap bmp = Media.getBitmap(getContentResolver(), uri);
 			   ByteArrayOutputStream bt1 = new ByteArrayOutputStream();
 			   bmp.compress(Bitmap.CompressFormat.JPEG, 40, bt1);
 			   */
    			Bitmap bmp = Media.getBitmap(getContentResolver(), uri);
    			ByteArrayOutputStream bt1 = new ByteArrayOutputStream();
 			   	bmp.compress(Bitmap.CompressFormat.PNG, 100, bt1);
 			   	
    			//Toast.makeText(cnt1, getRealPathFromURI(uri), Toast.LENGTH_LONG).show();
 			   /*
 			   InputStream imageInputStream=this.getContentResolver().openInputStream(uri);
 			   String str1=imageInputStream.toString();
 			   byte[] imageBits=str1.getBytes();
 			   */
 			  
    			
 			   
 			   //ByteArrayInputStream fileInputStream = new ByteArrayInputStream(bytes.toByteArray());
 			   
 			   
 			   Toast.makeText(cnt1, "s "+ bt1.toByteArray().length, Toast.LENGTH_LONG).show();
        	         
    	         
    	         //int size = bmp.getRowBytes() * bmp.getHeight();
    				//ByteBuffer b = ByteBuffer.allocate(size);
    				//bmp.copyPixelsToBuffer(b);
    				byte[] bytes = new byte[bt1.toByteArray().length];
    				try {
    				    bytes=bt1.toByteArray();//b.array();//.get(bytes, 0, bytes.length);
    				}
    				catch (Exception e){}
    				
    				int msg_length= bytes.length;
    	    	    int i=0;
    	    	    
    	    	    //int pack=msg_length/60000;
    	    	    //int rem=msg_length-(pack*60000);
    	    	    
    	    	    /*
    	    	    byte[] bt=new byte[65000];	
    	    	    ByteArrayInputStream ba=null;
    	    	    ba=new ByteArrayInputStream(bytes,1,60000);
    	    	    ba.read(bt, 0 , 60000);
    	    	   
    	    	    Toast.makeText(cnt1, "s/"+msg_length, Toast.LENGTH_LONG).show();
    	    	    */
    	    	    
    	    	 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
       			 PrintStream out = System.out;   	
       			 InetAddress remote = InetAddress.getByName("localhost");
       	         Socket c = new Socket(getString(R.string.ip),8000);//"180.151.96.155" "192.168.1.114"//10500);//59.93.248.125//
       	         BufferedWriter w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));

       	         OutputStream out1 = c.getOutputStream();

       	         BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
       	         
       	         
       	    	
 				//byte[] bytes2 = new byte[15];
       	         
       	         String str=padRight(job_desc,100);//28
       	         
 				byte[] bytes2=str.getBytes();//"*** Santanu Bhattacharya ***".getBytes();
 				byte[] bytes1 = new byte[bytes2.length+bt1.toByteArray().length];
 				System.arraycopy(bytes2, 0, bytes1, 0, bytes2.length);
 	    	    System.arraycopy(bytes, 0, bytes1, bytes2.length, bytes.length);
 	    	    
 	    	    
       	         
       	         String m = "santanu";// "santanu";
       	         //while ((m=r.readLine())!= null)
       	         {
       	            //out.println(m);
       	            //m = in.readLine();
       	            
       	        	 //w.write(bytes.toString());//m,0,m.length());
       	        	 
       	        	 
       	        	 //out1.write("ssssssssssssss".getBytes());
       	        	 //this.wait(100);
       	        	 
       	        	 out1.write(bytes1);
       	            
       	            //w.newLine();
       	         
       	            //w.flush();
       	         }
       	         w.close();
       	         r.close();
       	         c.close();
       	      Toast.makeText(cnt1, "send complete", Toast.LENGTH_LONG).show();
    	         
    		}
    		
            catch (Exception e)
            {
            	Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            } 
    }
	
	public void send2srvimgTCP(Uri uri,String job_desc) throws IOException//Bitmap bmp)
	{
		//try{
    	    
			   
			   
			   
    		try
    		{
  	    	   

     	    	  
    			
    			Bitmap bmp = Media.getBitmap(getContentResolver(), uri);
    			ByteArrayOutputStream bt1 = new ByteArrayOutputStream();
 			   	bmp.compress(Bitmap.CompressFormat.PNG, 100, bt1);
 			   	
    			//Toast.makeText(cnt1, getRealPathFromURI(uri), Toast.LENGTH_LONG).show();
 			   
 			   InputStream imageInputStream=this.getContentResolver().openInputStream(uri);
 			   //String str1=imageInputStream.toString();
 			   byte[] imageBits=new byte[imageInputStream.available()];//60000];
 			   imageInputStream.read(imageBits);

 			  
    			
 			   
 			   //ByteArrayInputStream fileInputStream = new ByteArrayInputStream(bytes.toByteArray());
 			   
 			   
 			   //Toast.makeText(cnt1, "s "+ imageBits.length, Toast.LENGTH_LONG).show();
 			   show_alert("s "+ imageBits.length);      
    	         
    	         //int size = bmp.getRowBytes() * bmp.getHeight();
    				//ByteBuffer b = ByteBuffer.allocate(size);
    				//bmp.copyPixelsToBuffer(b);
    				byte[] bytes = new byte[imageBits.length];
    				try {
    				    //bytes=bt1.toByteArray();//b.array();//.get(bytes, 0, bytes.length);
    					bytes=imageBits;
    				}
    				catch (Exception e){}
    				
    				int msg_length= bytes.length;
    	    	    int i=0;
    	    	    
    	    	    //int pack=msg_length/60000;
    	    	    //int rem=msg_length-(pack*60000);
    	    	    
    	    	    /*
    	    	    byte[] bt=new byte[65000];	
    	    	    ByteArrayInputStream ba=null;
    	    	    ba=new ByteArrayInputStream(bytes,1,60000);
    	    	    ba.read(bt, 0 , 60000);
    	    	   
    	    	    Toast.makeText(cnt1, "s/"+msg_length, Toast.LENGTH_LONG).show();
    	    	    */
    	    	    
    	    	 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
       			 PrintStream out = System.out;   	
       			 InetAddress remote = InetAddress.getByName("localhost");
       	         Socket c = new Socket( getString(R.string.ip),8000);//"180.151.96.155" "192.168.1.114"//10500);//59.93.248.125//
       	         BufferedWriter w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));

       	         OutputStream out1 = c.getOutputStream();

       	         BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
       	         
       	         
       	    	
 				//byte[] bytes2 = new byte[15];
       	         
       	         String str=padRight(job_desc,100);//28
       	         
 				byte[] bytes2=str.getBytes();//"*** Santanu Bhattacharya ***".getBytes();
 				byte[] bytes1 = new byte[bytes2.length+bt1.toByteArray().length];
 				System.arraycopy(bytes2, 0, bytes1, 0, bytes2.length);
 	    	    System.arraycopy(bytes, 0, bytes1, bytes2.length, bytes.length);
 	    	    
 	    	    	    			 
       	         
       	         String m = "santanu";// "santanu";
       	         //while ((m=r.readLine())!= null)
       	         {
       	            //out.println(m);
       	            //m = in.readLine();
       	            
       	        	 //w.write(bytes.toString());//m,0,m.length());
       	        	 
       	        	 
       	        	 //out1.write("ssssssssssssss".getBytes());
       	        	 //this.wait(100);
       	        	 
       	        	 out1.write(bytes1);
       	             
       	            //w.newLine();
       	         
       	            //w.flush();
       	         }
       	         w.close();
       	         r.close();
       	         c.close();
       	      
       	         //Toast.makeText(cnt1, "send complete", Toast.LENGTH_LONG).show();
       	      show_alert("send complete");
    		}
    		
            catch (Exception e)
            {
            	//Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            	show_alert(e.getLocalizedMessage());
            } 
    }
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

}