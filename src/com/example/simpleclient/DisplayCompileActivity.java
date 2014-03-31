package com.example.simpleclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class DisplayCompileActivity extends Activity {
	TextView text;
	ScrollView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_compile);
		text=(TextView) findViewById(R.id.textView1);
		ScrollView sv=(ScrollView)findViewById(R.id.scrollView1);
		Log.i("REMOVE", "to force compilation");
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		// Show the Up button in the action bar.
		setupActionBar();
		//TextView textView = new TextView(this);
		//textView.setTextSize(40);
		//textView.setText(message);
		//setContentView(textView);
	}

	void doSomethingexpensive(String Url)
	{
		String response = "";
		DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Url);
        try {
          HttpResponse execute = client.execute(httpGet);
          InputStream content = execute.getEntity().getContent();

          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
          String line = "";
          while ((line = buffer.readLine()) != null) {
        	  if(line.contains("Content-Type") | line.contains("HTTP") |
					  line.contains("Server")  | line.contains("Date") )
				  continue;
            response += line;
          }
        } catch (Exception e) {
	          e.printStackTrace();
	        }
        text.setText(response);
        //sv.addView(text);
        text.setMovementMethod(new ScrollingMovementMethod());

	}
	
	public void getCompileLog(View view) {
	    // Do something in response to button
		String Url=MainActivity.BaseUrl+"/compile";
		//TextView t=(TextView) findViewById(R.id.textView1);
		//new GetTextFromUrl().execute(URL);
		doSomethingexpensive(Url);
	}
	public void getResult(View view) {
	    // Do something in response to button
		String Url=MainActivity.BaseUrl+"/result";
		doSomethingexpensive(Url);
		//new GetTextFromUrl().execute(URL);
	}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_compile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*private class GetTextFromUrl extends AsyncTask<String, Void, String>{
		private static final String TAG = "GetTextFromUrl"; 
		@Override
		protected String doInBackground(String... Urls) {
			String response = "";
			DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(Urls[0]);
	        try {
	          HttpResponse execute = client.execute(httpGet);
	          InputStream content = execute.getEntity().getContent();

	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          String s = "";
	          while ((s = buffer.readLine()) != null) {
	            response += s;
	          }

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	        return response;
		}
		 protected void onPostExecute(String result) {
			 
	         text.setText(result);
	     }
	}*/

}


