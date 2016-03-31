package com.seuic.cloudprinter_client;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

import com.seuic.cloudprinter_client.Order.DishInOrder;
import com.seuic.cloudprinter_client.ShowMenu.ViewHolder;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderConfirm extends ActionBarActivity {
	public List<DishInOrder> order_list ;
	private final static String[] array = new String[]{"1","2"};
	HashMap<String,Object> addr_map ;
	String shopID;
	TextView TV_NAME;
	TextView TV_PRICE;
	TextView TV_COUNT;
	ListView lv_parent;
	ListView lv_child;
	Button BTN_SUBMIT;
	Order order = new Order();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_confirm);
		
		Bundle bundle =getIntent().getExtras();
		if(bundle!=null)
		{
			Intent intent = getIntent();
		    shopID = intent.getExtras().get("shopID").toString();
			order_list = order.convertMap((List<Dish>) intent.getSerializableExtra("list"));
		}
		addr_map = new HashMap<String,Object>();
		BTN_SUBMIT = (Button)findViewById(R.id.btn_submit2);
		BTN_SUBMIT.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				order.setShopID(shopID);
				order.getTime();
				order.setOrder_list(order_list);
				String gson = order.toGson();
				AlertDialog.Builder builder = new AlertDialog.Builder(OrderConfirm.this);
				Dialog dialog1 = builder.setMessage(gson).show();
				new NetworkAsyncTask(OrderConfirm.this)
				.execute(new String[]{getString(R.string.url),
						String.valueOf(NetworkAsyncTask.doUploadOrder),
						gson
						});
				
				
			}
			
		});
		
		
		/*
		if(savedInstanceState!=null)
		{
			order_list = (List<Dish>) savedInstanceState.getSerializable("restoreList");
		}else{
			Intent intent = getIntent();
			order_list = (List<Dish>) intent.getSerializableExtra("list");
		}*/
		//View view = getLayoutInflater().inflate(R.layout.item_personalinfo, null);
		//lv_child = (ListView)view.findViewById(R.id.lv_orderdish);
		//lv_item.setAdapter(new orderAdapter());
		lv_parent = (ListView)findViewById(R.id.lv_orderlist);
		lv_parent.setAdapter(new ParentAdapter());
		//lv_child.setAdapter(new orderAdapter());
	}
	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    }   
	public class orderAdapter extends BaseAdapter
	{
		@Override
		public int getCount() {
			return order_list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return order_list.get(position);
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
				view = View.inflate(getApplicationContext(), R.layout.item_orderdish, null);
				holder.TV_NAME = (TextView)view.findViewById(R.id.item_name);
				holder.TV_PRICE = (TextView)view.findViewById(R.id.item_price);
				holder.TV_COUNT = (TextView)view.findViewById(R.id.item_count);
				view.setTag(holder);
			}
			else
			{
				view = convertView;
				holder = (ViewHolder)view.getTag();
			}
			DishInOrder dish = order_list.get(position);
			holder.TV_NAME.setText(dish.getName());
			holder.TV_PRICE.setText("￥ "+dish.getPrice());
			holder.TV_COUNT.setText("×"+dish.getCheckedCount());

			
			return view;
		}
		
	}
	public final class ViewHolder{
		public TextView TV_NAME;
		public TextView TV_PRICE;
		public TextView TV_COUNT;
	}
    private class ParentAdapter extends BaseAdapter{
    	 
		@Override
		public int getCount() {
		// TODO Auto-generated method stub
		return Array.getLength(array);
		}
		 
		@Override
		public Object getItem(int position) {
		// TODO Auto-generated method stub
		return array[position];
		}
		 
		@Override
		public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
		}
		 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		ParentHolder holder = new ParentHolder();
		view = View.inflate(getApplicationContext(), R.layout.item_personalinfo, null);
		holder.TV_HINT = (TextView) view.findViewById(R.id.tv_title);
		if(addr_map.isEmpty())
			holder.TV_HINT.setText("请完善收货人信息");
		else
			holder.TV_HINT.setText(addr_map.get("name")+" "+addr_map.get("telnum")+"\n"+addr_map.get("addr"));
		holder.IV_NEXT = (ImageView) view.findViewById(R.id.imageView1);
		holder.lv_item = (ListView) view.findViewById(R.id.lv_orderdish);
		
		if(position == 0){
			holder.lv_item.setVisibility(View.INVISIBLE);
			//RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) view.getLayoutParams();
			//params.height = "100";
			//view.setLayoutParams(params);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(OrderConfirm.this,EditPersonalInfo.class);
					startActivityForResult(intent,1);
					
				}
			});
		}
		if(position == 1){
		holder.TV_HINT.setVisibility(View.INVISIBLE);
		holder.IV_NEXT.setVisibility(View.INVISIBLE);
		holder.lv_item.setVisibility(View.VISIBLE);
		holder.lv_item.setAdapter(new orderAdapter());
		setListViewHeightBasedOnChildren(holder.lv_item);
		}
		return view;
		}
    }
    public final class ParentHolder{
		public TextView TV_HINT;
		public ImageView IV_NEXT;
		public ListView lv_item;
		
	}
    @Override 
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    	if(requestCode==1)
    	{
    		if(resultCode==1){
    			Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
    			PersonalInfo person = new PersonalInfo();
				person.setAddr(data.getExtras().get("addr").toString());
				person.setName(data.getExtras().get("name").toString());
				person.setTelNum(data.getExtras().get("telnum").toString());
				order.setPerson(person);
				//order_list = (List<Dish>) intent.getSerializableExtra("list");
    			addr_map.put("name",person.getName());
    			addr_map.put("telnum",person.getTelNum());
    			addr_map.put("addr",person.getAddr());
    			lv_parent.setAdapter(new ParentAdapter());
    			

    		}
    	}
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_confirm, menu);
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
