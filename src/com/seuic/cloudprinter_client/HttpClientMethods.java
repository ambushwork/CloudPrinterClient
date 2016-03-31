package com.seuic.cloudprinter_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpClientMethods {
	public static final String relativeDownloadPath = "/baidu/Download_Menu.xml";
	public static final String relativeUploadPath = "/baidu/Upload_Menu.xml";
	public static String doDownload(String url)
	{
		String respStr="";
		try {
		HttpPost httpRequest = new HttpPost(url);
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setTimeout(params, 1000);
		HttpConnectionParams.setConnectionTimeout(params, 3000);  
        HttpConnectionParams.setSoTimeout(params, 5000);  
        httpRequest.setParams(params);
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
        HttpEntity entity = httpResponse.getEntity();
        InputStream in = entity.getContent();
        byte[] bb = new byte[1024];
        String gson = new String();
        int ret;
        while((ret = in.read(bb))!=-1)
        	gson += new String(bb,"UTF-8");
        return gson;
        
        
        /*
        HttpEntity entity = httpResponse.getEntity();  
        InputStream in = entity.getContent();
        String Dir = Environment.getExternalStorageDirectory().getAbsolutePath()+relativeDownloadPath;
        File file = new File(Dir);
        file.getParentFile().mkdirs();  
        FileOutputStream fileout = new FileOutputStream(file); 
        byte[] buffer=new byte[128];  
        int ch = 0;  
        while ((ch = in.read(buffer)) != -1) {  
            fileout.write(buffer,0,ch);  
        }  
        in.close();  
        fileout.flush();  
        fileout.close();
		*/
		} catch(ClientProtocolException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		return respStr;
	}
	public static String doUpload(String url,String gson)
	{
		int ret=-1;
		HttpPost httpRequest = new HttpPost(url);
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setTimeout(params, 5000);
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		httpRequest.setParams(params);
		try {
			StringEntity s1 = new StringEntity(gson, "UTF-8");
			s1.setContentType("application/json;charset=UTF-8");
			s1.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			httpRequest.setEntity(s1);
			HttpResponse httpResponse;
			httpResponse = new DefaultHttpClient().execute(httpRequest);
			ret = httpResponse.getStatusLine().getStatusCode();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return String.valueOf(ret);  
	}
	public static  List<Shop> ParseGsonToShop(String gson)
	{
		String gsonString = gson.trim();
		Gson gson1 = new Gson();
		List<Shop> list = new ArrayList<Shop>();
		Type type = new TypeToken<List<Shop>>(){}.getType();
		list = gson1.fromJson(gsonString, type);
		return list;
	}
	public static  List<Dish> ParseGsonToDish(String gson)
	{
		String gsonString = gson.trim();
		Gson gson1 = new Gson();
		List<Dish> list = new ArrayList<Dish>();
		Type type = new TypeToken<List<Dish>>(){}.getType();
		Log.e("tab", gsonString);
		list = gson1.fromJson(gsonString, type);
		return list;
	}
	public static void uploadOrder(){
		
	}
	
	
	
	public static Bitmap getBitmap(String url)
	{
		URL fileUrl = null;
		Bitmap bitmap = null;
		try {
			fileUrl = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
		
	
		
	}
	
	
	
}