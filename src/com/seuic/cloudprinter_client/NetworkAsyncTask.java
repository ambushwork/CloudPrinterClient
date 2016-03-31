package com.seuic.cloudprinter_client;

import android.content.Context;
import android.os.AsyncTask;

public class NetworkAsyncTask extends AsyncTask<String,Void, String> {
	public static final int doDownloadShopInfo = 1;
	public static final int doDownloadMenuList = 2;
	public static final int doUploadOrder =3;
	private Context context;
	
	public NetworkAsyncTask(Context context){
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		String url = params[0];
		int comm = Integer.parseInt(params[1]);
		
		String result="";
		switch(comm){
		case doDownloadShopInfo:
			result = HttpClientMethods.doDownload(url+"?COMMAND=CLI_REQUEST_INFO");
			break;
		case doDownloadMenuList:
			String shopID = params[2];
			result = HttpClientMethods.doDownload(url+"?COMMAND=CLI_REQUEST_MENU"+"&shopID="+shopID);
			break;
		case doUploadOrder:
			result = HttpClientMethods.doUpload(url+"?COMMAND=CLI_SUBMIT_ORDER",params[2]);
		}
		return result;

	}

}
