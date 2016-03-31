package com.seuic.cloudprinter_client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



































import com.seuic.cloudprinter_client.R;











import com.seuic.cloudprinter_client.R.drawable;
import com.seuic.cloudprinter_client.R.id;
import com.seuic.cloudprinter_client.R.layout;
import com.seuic.cloudprinter_client.R.menu;
import com.seuic.cloudprinter_client.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowShop extends Activity {
	public static String URL ;
	Button BN_BACK,BN_SUBMIT ;
	TextView TV_ERROR;
	CheckBox CB;
	ImageButton IB_REFRESH;
	Dialog dialog1;
	//List<HashMap<String,Object>> data;
	List<Shop> shop_list;
	ListView listview;
	Map<Integer, Boolean> isCheckMap =  new HashMap<Integer, Boolean>();
	List<HashMap<String,Object>> list_submit=new ArrayList<HashMap<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_shop);
		listview = (ListView)findViewById(R.id.listview);
		TV_ERROR = (TextView)findViewById(R.id.tv_error);
		IB_REFRESH = (ImageButton)findViewById(R.id.ib_refresh);
		IB_REFRESH.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetShopInfoAsyncTask(ShowShop.this).execute(new String[]{getString(R.string.url),String.valueOf(NetworkAsyncTask.doDownloadShopInfo)});
				AlertDialog.Builder builder = new AlertDialog.Builder(ShowShop.this);
				dialog1 = builder.setMessage("获取店铺信息...").show();
				
			}
		});
		Bundle bundle =getIntent().getExtras();

		if(bundle!=null)
		{
			TV_ERROR.setVisibility(View.INVISIBLE);
			IB_REFRESH.setVisibility(View.INVISIBLE);
			String gson = bundle.getString("gson");
			showShopList(gson);
		}
		else
		{
			TV_ERROR.setVisibility(View.VISIBLE);
			IB_REFRESH.setVisibility(View.VISIBLE);
		}
	}
	
	public class GetShopInfoAsyncTask extends NetworkAsyncTask
	{
        public GetShopInfoAsyncTask(Context context){
        	super(context);
        }
		@Override
		protected void onPostExecute(String result)
		{
			dialog1.dismiss();
			if(result!="")
			{
				showShopList(result);
				IB_REFRESH.setVisibility(View.INVISIBLE);
				TV_ERROR.setVisibility(View.GONE);
			}
			else{
				Toast.makeText(ShowShop.this, "获取失败", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	

	public void showShopList(String gson){
		
		shop_list = HttpClientMethods.ParseGsonToShop(gson);
		listview.setAdapter(new shopAdapter());
		listview.setOnItemClickListener(new OnItemClickListener(){
			
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String shopID = shop_list.get(position).getShopID();
			Intent intent = new Intent(ShowShop.this,ShowMenu.class);
			intent.putExtra("shopID", shopID);
			startActivity(intent);
			
			}
		
		});
	}
	
	public class shopAdapter extends BaseAdapter
    {
    	@Override
		public int getCount() {
			return shop_list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return shop_list.get(position);
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
				view = getLayoutInflater().inflate(R.layout.item, null);
				holder.TV_NAME = (TextView)view.findViewById(R.id.tv_name);
				holder.TV_TEL = (TextView)view.findViewById(R.id.tv_tel);
				holder.TV_DISHCOUNT = (TextView)view.findViewById(R.id.tv_dishcount);
				holder.IV_HEAD = (ImageView)view.findViewById(R.id.imageView1);
				view.setTag(holder);
			}
			else
			{
				view = convertView;
				holder = (ViewHolder)view.getTag();
			}
			view.setId(position);
			holder.shopID = shop_list.get(position).getShopID();
			holder.TV_NAME.setText(shop_list.get(position).getName().toString());
			//holder.TV_TEL.setText("TEL:"+shop_list.get(position).telnum.toString());
			holder.TV_TEL.setText("TEL:4008-823-823");
			holder.TV_DISHCOUNT.setText("26个菜式");
			Drawable dr =getResources().getDrawable(R.drawable.ic_kfc);
			BitmapDrawable bd = (BitmapDrawable)dr;
			Bitmap bitmap = ThumbnailUtils.extractThumbnail(bd.getBitmap(), 100, 100);
			holder.IV_HEAD.setImageBitmap(bitmap); 

			return view;
		}
    }
	public final class ViewHolder{
		public TextView TV_NAME;
		public TextView TV_TEL;
		public TextView TV_DISHCOUNT;
		public ImageView IV_HEAD;
		public String shopID;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_menu, menu);
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
