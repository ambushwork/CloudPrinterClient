package com.seuic.cloudprinter_client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

public class Shop implements Serializable{
	private String shopID;
	private String userID;
	private String name;
	private String telnum;
	private String addr;
	private String imgurl;
	public Shop(){}
	public void PrintOut()
	{
		System.out.println("shopID"+this.shopID);
		System.out.println("Name:"+this.name);
		System.out.println("Tel:"+this.telnum);
		System.out.println("Address:"+this.addr);	
	}
	public void setShopID(String shopID){
		this.shopID = shopID;
	}
	public void setUserID(String userID){
		this.userID = userID;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setTelnum(String telnum){
		this.telnum = telnum;
	}
	public void setAddr(String addr){
		this.addr = addr;
	}
	public void setImgurl(String imgurl){
		this.imgurl = imgurl;
	}
	public String getImgurl(){
		return this.imgurl;
	}
	public String getShopID(){
		return this.shopID;
	}
	public String getUserID(){
		return this.userID;
	}
	public String getName(){
		return this.name;
	}
	public String getTelnum(){
		return this.telnum;
	}
	public String getAddr(){
		return this.addr;
	}
	public String toGson(){
		Gson gson = new Gson();
		String result = gson.toJson(this);
		return result;
	}
	public Shop ParseGsonToShop(String gsonString)
	{
		String gsonS = gsonString.trim();
		Gson gson = new Gson();
		Log.e("gsonString_ToGson:", gsonS);
		Shop shop  = gson.fromJson(gsonS, new TypeToken<Shop>(){}.getType());
		return shop;
	}
}
