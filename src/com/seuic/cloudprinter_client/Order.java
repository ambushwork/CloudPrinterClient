package com.seuic.cloudprinter_client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.text.format.Time;

public class Order {
	private String orderID;
	private PersonalInfo person;
	private List<DishInOrder> order_list;
	private  String time;
	private  String shopID;
	private int status;
    
	public static final int STATUS_CLIENT_WAITING  = 1;
	public static final int STATUS_SERVER_RECIEVED = 2;
	public static final int STATUS_SERVER_WAITING  = 3;
	public static final int STATUS_POS_RECIEVED    = 4;
	public static final int STATUS_POS_CONFIRMED   = 5;
	public static final int STATUS_ORDER_DONE      = 6;

	public Order(){
		person = new PersonalInfo();
		order_list = new ArrayList<DishInOrder>();
		status = STATUS_CLIENT_WAITING;
		orderID = "";
	}

	public class DishInOrder{
		private String dishID;
		private String name;
		private int price;
		private int checkedcount;
		

		public DishInOrder(Dish dish){
			this.dishID = dish.getDishID();
			this.name = dish.getName();
			this.price = dish.getPrice();
			this.checkedcount = dish.getCheckedCount();
		}
		public String getName(){
			return this.name;
		}
		public int getPrice(){
			return this.price;
		}
		public int getCheckedCount(){
			return this.checkedcount;
		}
		
		
	}
	
	public List<DishInOrder> convertMap(List<Dish> list){
		List<DishInOrder> newlist = new ArrayList<DishInOrder>();
		Iterator<Dish> it = list.iterator();
		while(it.hasNext()){
			DishInOrder dishinorder = new DishInOrder(it.next());
		    newlist.add(dishinorder);
		}
		return newlist;
		
	}
	public  String getTime(){
		SimpleDateFormat formatter =new SimpleDateFormat("yyyyƒÍMM‘¬dd»’  HH:mm:ss  ");
		Date curDate = new Date(System.currentTimeMillis());
		this.time = formatter.format(curDate);
		return this.time;
	}
    public void createID(){
    	SimpleDateFormat formatter =new SimpleDateFormat("yyyyMMdd");
    	Date curDate = new Date(System.currentTimeMillis());
    	this.orderID = formatter.format(curDate);
    }
    public String getID(){
    	return this.orderID;
    }
	public  void setShopID(String shopID){
		this.shopID = shopID;
	}
	public String getShopUuid(){
		return this.shopID;
	}
	public void setPerson(PersonalInfo person){
		this.person = person;
	}
	public PersonalInfo getPerson(){
		return this.person;
	}
	public int getStatus(){
		return this.status;
	}
	public void setOrder_list(List<DishInOrder> list){
		this.order_list = list;
	}
	public List<DishInOrder> getOrder_list(){
		return this.order_list;
	}
	public String toGson(){
		Gson gson = new Gson();
		String result = gson.toJson(this);
		return result;
	}
	public static Order FromGson(String gsonS){
		Order order = new Order();
		Gson gson = new Gson();
		order = gson.fromJson(gsonS,  new TypeToken<Order>(){}.getType());
		return order;
	}

}
