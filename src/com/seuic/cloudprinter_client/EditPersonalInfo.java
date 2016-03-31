package com.seuic.cloudprinter_client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seuic.cloudprinter_client.ShowMenu.ViewHolder;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EditPersonalInfo extends ActionBarActivity {
	ListView listview;
	Button BTN_OK;
	SimpleAdapter adapter;
	Map<Integer,Object> map ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_personal_info);
		map = new HashMap<Integer,Object>();
		listview = (ListView)findViewById(R.id.lv_pesonalinfo);
		/*adapter = new SimpleAdapter(this, getStringList(), 
				R.layout.item_edit_info, 
				new String[]{"title","hint"}, 
				new int[]{R.id.tv_edit_title,R.id.et_edit_value});*/
		myAdapter adapter = new myAdapter();
		listview.setAdapter(adapter);
		BTN_OK = (Button)findViewById(R.id.btn_ok);
		BTN_OK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EditPersonalInfo.this,OrderConfirm.class);
				if((map.get(0)==null)||(map.get(2)==null)||(map.get(0)==null)){
					Toast.makeText(getApplicationContext(), "信息不能为空", Toast.LENGTH_SHORT).show();
				}
				else{
					intent.putExtra("name",map.get(0).toString());
					intent.putExtra("telnum", map.get(1).toString());
					intent.putExtra("addr", map.get(2).toString());
					intent.putExtra("SUPER", "EditPersonalInfo");
					setResult(1,intent);
					finish();
				}
			}
			
		});
		
	}
	public class myAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return getStringList().size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return getStringList().get(position);
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
			ViewHolder holder = new ViewHolder();
			view = getLayoutInflater().inflate(R.layout.item_edit_info, null);
			holder.TV_TITLE = (TextView)view.findViewById(R.id.tv_edit_title);
			holder.ET_VALUE = (EditText)view.findViewById(R.id.et_edit_value);
			holder.TV_TITLE.setText(getStringList().get(position).get("title").toString());
			holder.ET_VALUE.setHint(getStringList().get(position).get("hint").toString());
			holder.ET_VALUE.setTag(position);

			class MyTextWatcher implements TextWatcher {
                public MyTextWatcher(ViewHolder holder) {
                    mHolder = holder;
                }

                private ViewHolder mHolder;

                @Override
                public void onTextChanged(CharSequence s, int start,
                        int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                        int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && !"".equals(s.toString())) {
                        int position = (Integer) mHolder.ET_VALUE.getTag();
                        map.put(position,
                                s.toString());// 当EditText数据发生改变的时候存到data变量中
                    }
                }
            }
			holder.ET_VALUE.addTextChangedListener(new MyTextWatcher(holder));
			view.setTag(holder);
			
			/*
			holder.ET_VALUE.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void afterTextChanged(Editable s) {
					map.put(position, s.toString());
					
				}
				
			});
              
            //如果hashMap不为空，就设置的editText  
            if(map.get(position) != null){  
                holder.ET_VALUE.setText(map.get(position).toString());  
            } */
			
			return view;
		}
		
	}
	public final class ViewHolder{
		public TextView TV_TITLE;
		public EditText ET_VALUE;
		
	}
	
	private List<Map<String,Object>> getStringList(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "联系人");
        map.put("hint", "您的姓名");
        list.add(map);
        
        map = new HashMap<String, Object>();
        map.put("title", "联系电话");
        map.put("hint", "您的手机号");
        list.add(map);
        
        map = new HashMap<String, Object>();
        map.put("title", "收货地址");
        map.put("hint", "详细地址");
        list.add(map);
        
		return list;
        
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_personal_info, menu);
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
