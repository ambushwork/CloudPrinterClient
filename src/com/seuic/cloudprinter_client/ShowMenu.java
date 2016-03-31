package com.seuic.cloudprinter_client;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.seuic.cloudprinter_client.ShowShop.shopAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMenu extends Activity {
	public static String URL ;
	public static String imgUrl ;
	static TextView TV_H;
	static TextView TV_TOTAL;
	Button BTN_SUBMIT;
	static List<Dish> dish_list ;
	static Bitmap bitmap;
	public static ListView listview1;
	public int TotalPrice;
	String shopID;
	private HashMap<String,Integer> checkMap=new HashMap<String,Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_menu);
		Bundle bundle = getIntent().getExtras();
		listview1 = (ListView)findViewById(R.id.listview1);
		BTN_SUBMIT = (Button)findViewById(R.id.btn_submit);
		TV_TOTAL = (TextView)findViewById(R.id.tv_totalprice);
		imgUrl = getString(R.string.imgurl);
		URL = getString(R.string.url);
		if(bundle!=null)
		{
			shopID =  bundle.getString("shopID");
			Toast.makeText(ShowMenu.this,shopID, Toast.LENGTH_SHORT).show();
			new DoloadMenuAsyncTask(ShowMenu.this).execute(new String[]{URL,String.valueOf(NetworkAsyncTask.doDownloadMenuList),shopID});
		}
		BTN_SUBMIT.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShowMenu.this,OrderConfirm.class);
				List<Dish> myList = new ArrayList<Dish>();
				Iterator<Dish> it = dish_list.iterator();
				while(it.hasNext())
				{
					Dish dish = it.next();
					if(dish.getCheckedCount()>0)
						myList.add(dish);
				}
				intent.putExtra("list", (Serializable)myList);
				intent.putExtra("SUPER","ShowMenu");
				intent.putExtra("shopID", shopID);
				startActivity(intent);
				
			}
			
		});
	}
	/*
	public static class getPicAsyncTask extends AsyncTask<String,Void,String>
	{

		@Override
		protected String doInBackground(String... params) {
			String url  = params[0];
			bitmap = HttpClientMethods.getBitmap(url);
			return null;
		}
		@Override
		protected void onPostExecute(String result)
		{
			listview1.setAdapter(new menuAdapter());
			//TV_H.setText(result);
		}
		
	}*/
	
	
	
	public class DoloadMenuAsyncTask extends NetworkAsyncTask
	{

		public DoloadMenuAsyncTask(Context context){
			super(context);
		}
		@Override
		protected void onPostExecute(String result)
		{
			dish_list = HttpClientMethods.ParseGsonToDish(result);
			listview1.setAdapter(new menuAdapter());

		}
		
	}
	public class menuAdapter extends BaseAdapter
	{
		@Override
		public int getCount() {
			return dish_list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dish_list.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			View view;
			
			if(convertView == null)
			{
				holder = new ViewHolder();
				view = getLayoutInflater().inflate(R.layout.item_menu, null);
				holder.TV_NAME = (TextView)view.findViewById(R.id.menu_name);
				holder.TV_PRICE = (TextView)view.findViewById(R.id.menu_price);
				holder.TV_COUNT = (TextView)view.findViewById(R.id.menu_count);
				holder.IV_HEAD = (ImageView)view.findViewById(R.id.menu_head);
				holder.TV_REMARK = (TextView)view.findViewById(R.id.menu_remark);
				holder.IB_DEC = (ImageButton)view.findViewById(R.id.ic_menu_dec);
				holder.IB_PLUS = (ImageButton)view.findViewById(R.id.ic_menu_plus);
				holder.TV_NUM = (TextView)view.findViewById(R.id.tv_menu_num);
				view.setTag(holder);
			}
			else
			{
				view = convertView;
				holder = (ViewHolder)view.getTag();
			}
			Dish dish = new Dish();
			dish = dish_list.get(position);
			holder.TV_NAME.setText(dish.getName());
			holder.TV_NAME.setTextColor(Color.parseColor("#333333"));
			holder.TV_PRICE.setText("￥ "+dish.getPrice());
			holder.TV_PRICE.setTextColor(Color.parseColor("#FFA722"));
			holder.TV_COUNT.setText("还剩"+dish.getCount()+"份");
			holder.TV_COUNT.setTextColor(Color.parseColor("#919191"));
			String dishremark = dish.getRemark();
			if(dishremark!=null)
				holder.TV_REMARK.setText(dishremark);
			else
				holder.TV_REMARK.setText("");
			
			holder.TV_NUM.setText(String.valueOf(dish.getCheckedCount()));
			holder.IB_DEC.setTag(position);
			holder.IB_PLUS.setTag(position);
			//holder.TV_REMARK.setText(dish_list.get(position).remark);
			//holder.IB_NEXT.setTag(data.get(position).get("id").toString());
			
			String bmpUrl =imgUrl + dish.getImgUrl();
			//new getPicAsyncTask().execute(bmpUrl);
			AsyncImageLoader imageLoader = new AsyncImageLoader(ShowMenu.this);
			Bitmap bmp =  imageLoader.loadImage(holder.IV_HEAD, bmpUrl);
			holder.IV_HEAD.setImageBitmap(bmp);
			
			holder.IB_PLUS.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Integer tag = (Integer)v.getTag();
					dish_list.get(tag).addCheckedCount();
					notifyDataSetChanged();
					TV_TOTAL.setText("总价格"+String.valueOf(getTotalPrice()));
				}
				
			});
			holder.IB_DEC.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Integer tag = (Integer)v.getTag();
					dish_list.get(tag).decCheckedCount();
					notifyDataSetChanged();
					TV_TOTAL.setText("总价格"+String.valueOf(getTotalPrice())+"￥");
				}
				
			});
			
			return view;
		}
		
	}
	public int getTotalPrice()
	{
		int TotalPrice = 0;
		Iterator<Dish> it = dish_list.iterator();
		while(it.hasNext())
		{
			Dish dish = it.next();
			TotalPrice+=dish.getPrice()*dish.getCheckedCount();
		}
		return TotalPrice;
	}
	
	
	
	
	public final class ViewHolder{
		public TextView TV_NAME;
		public TextView TV_PRICE;
		public TextView TV_COUNT;
		public ImageView IV_HIDE;
		public ImageButton IB_DEC;
		public ImageButton IB_PLUS;
		public ImageView IV_HEAD;
		public TextView TV_REMARK;
		public TextView TV_NUM;
	}
}
