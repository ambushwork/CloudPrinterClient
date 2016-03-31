package com.seuic.cloudprinter_client;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Dish  implements Serializable {
	private String dishID;
	private String name;
	private int price;
	private int count;
	private int sale;
	private String remark;
	private String img_url;
	private int CheckedCount;
	Dish(String name,int price, int count, int sale ,String remark,String img_url)
	{
		this.dishID = "-1";
		this.CheckedCount = 0;
		this.name = name;
		this.price = price;
		this.count = count;
		this.sale = sale;
		this.remark = remark;
		this.img_url = img_url;
	}
	public Dish() {
		// TODO Auto-generated constructor stub
	}
	public void setDishID(String dishID){
		this.dishID = dishID;
	}
	public String getDishID(){
		return this.dishID;
	}
	public String getName()
	{
		return name;
	}
	public int getPrice()
	{
		return price;
	}
	public int getCount(){
		return count;
	}
	public int getSale(){
		return sale;
	}
	public String getRemark(){
		return remark;
	}
	public String getImgUrl(){
		return img_url;
	}
	public int getCheckedCount(){
		return CheckedCount;
	}
	public void addCheckedCount(){
		CheckedCount++;
	}
	public void decCheckedCount(){
		if(CheckedCount>0)
			CheckedCount--;
	}
}