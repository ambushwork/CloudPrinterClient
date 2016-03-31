package com.seuic.cloudprinter_client;



import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ClientApp extends ActionBarActivity {
	Button BT_ORDER;
	Button BT_PRINTER;
	Dialog dialog1;
	ProgressBar PB;
	String url;
	ShareActionProvider provider  = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_app);
		BT_ORDER = (Button)findViewById(R.id.bt_order);
		BT_PRINTER = (Button)findViewById(R.id.btn_printer);
		PB = (ProgressBar)findViewById(R.id.pb1);
		PB.setVisibility(View.INVISIBLE);
		ActionBar actionBar=getActionBar();
		actionBar.setTitle("SEUIC");
		
		BT_ORDER.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//new GetAsyncTask().execute(url);
				new GetAsyncTask(ClientApp.this).execute(new String[]{getString(R.string.url),String.valueOf(NetworkAsyncTask.doDownloadShopInfo)});
				AlertDialog.Builder builder = new AlertDialog.Builder(ClientApp.this);
				dialog1 = builder.setMessage("获取店铺信息...").show();
			}
			
		});
		
		
	}
	public class GetAsyncTask extends NetworkAsyncTask
	{
		@Override 
		protected void onPreExecute(){
			/*
			CircleProgressBar cpb = new CircleProgressBar(ClientApp.this);
			cpb = (CircleProgressBar)findViewById(R.id.progress);
			//cpb.setShowArrow(true);
			cpb.setVisibility(View.VISIBLE);
			//cpb.onAnimationStart();
			*/
			PB.setVisibility(View.VISIBLE);
		}
		public GetAsyncTask(Context context)
		{
			super(context);
		}
		@Override
		protected void onPostExecute(String result)
		{
			dialog1.dismiss();
			Intent intent = new Intent(ClientApp.this,ShowShop.class);
			if(result!="")
				intent.putExtra("gson", result);
			startActivity(intent);
			finish();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_app, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
