package com.seuic.cloudprinter_client;

public class PersonalInfo {
	private String personID;
	private String addr;
	private String telnum;
	private String name;
	public PersonalInfo(){
		personID = "-1";
	}
	public PersonalInfo(String Addr,String TelNum,String Name)
	{
		this.addr = Addr;
		this.telnum = TelNum;
		this.name = Name;
	}
	public void setPersonID(String personID){
		this.personID = personID;
	}
	public String getPersonID(){
		return this.personID;
	}
	public void setAddr(String Addr){
		this.addr = Addr;
	}
	public void setTelNum(String TelNum){
		this.telnum = TelNum;
	}
	public void setName(String Name){
		this.name = Name;
	}
	public void setPersonalInfo(String Addr,String TelNum,String Name){
		this.addr = Addr;
		this.telnum = TelNum;
		this.name = Name;
	}
	public String getAddr()
	{
		return this.addr;
	}
	public String getTelNum(){
		return this.telnum;
	}
	public String getName(){
		return this.name;
	}

}
