package com.example.simpleclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	public static String BaseUrl="http://10.0.2.2:38080";
	String mProgramURL;
	ArrayAdapter<String> madapter;
	private ArrayList<String> mProgramList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		String Url=BaseUrl+"/";
		//new SimpleHttpClient().execute(URL);
		mProgramList=doSomethingExpensive(Url);
		final ListView listview = (ListView) findViewById(R.id.listview);
		madapter=new ArrayAdapter<String>(this, R.layout.fruit_list,mProgramList);
		listview.setAdapter(madapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			    Toast.makeText(getApplicationContext(),
			      "Click ListItem Number " + position, Toast.LENGTH_LONG)
			      .show();
			    mProgramURL=BaseUrl+"/program="+position;
			    //new SimpleHttpClient().execute(mProgramURL);
			    doSomethingExpensive(mProgramURL);
			    CreateNewActivity();
			  }
			}); 
	}
	public void CreateNewActivity()
	{
	    Intent intent = new Intent(this, DisplayCompileActivity.class);	
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public ArrayList<String> doSomethingExpensive(String Url)
	{
 
		ArrayList<String> ar=new ArrayList<String>();
		DefaultHttpClient client = new DefaultHttpClient();
		  HttpGet request= new HttpGet(Url);  
		  try {
		      // Execute the method.
			  HttpResponse response = client.execute(request);
			  BufferedReader rd = new BufferedReader
					  (new InputStreamReader(response.getEntity().getContent()));
			  String line = "";
			 
			  while ((line = rd.readLine()) != null) {
				  if(line.contains("Content-Type") | line.contains("HTTP") |
						  line.contains("Server")  | line.contains("Date") )
					  continue;
				  ar.add(line);
				  
			  }


		  }catch (IOException e) {
		      e.printStackTrace();
		    }

		  
		return ar;
	}

}
