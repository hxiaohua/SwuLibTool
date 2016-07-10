package com.example.swubook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class HttpHelp {
	//基本数据成员
	public String Url;
	public String PostData;
	public String Cookie;
	public String Html;
	public String Encoding;
	public int Status;
	//默认构造函数
	HttpHelp()
	{
		Url=PostData=Cookie=Html=Encoding="";
	}
	public String  LoginLib(String data) throws Exception
	{
		String encoding="UTF-8";
		//数据包 	
		String ss="ScriptManager1=UpdatePanel1%7CImageButton1&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=%2FwEPDwULLTEyMTk3ODg5MTUPZBYCAgMPZBYCAgUPZBYCZg9kFggCAQ9kFgQCAQ8WAh4JaW5uZXJodG1sBQzljJfkuqzph5Hnm5hkAgMPDxYCHgRUZXh0BdcEPHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J2RlZmF1bHQuYXNweCc%2BPHNwYW4%2B6aaW6aG1PC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdkZWZhdWx0LmFzcHgnPjxzcGFuPuS5puebruafpeivojwvc3Bhbj48L0E%2BPC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI%2BPEEgaHJlZj0nTWFnYXppbmVDYW50b1NjYXJjaC5hc3B4Jz48c3Bhbj7mnJ%2FliIrnr4flkI08L3NwYW4%2BPC9BPjwvdGQ%2BPHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J1Jlc2VydmVkTGlzdC5hc3B4Jz48c3Bhbj7pooTnuqbliLDppoY8L3NwYW4%2BPC9BPjwvdGQ%2BPHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J0V4cGlyZWRMaXN0LmFzcHgnPjxzcGFuPui2heacn%2BWFrOWRijwvc3Bhbj48L0E%2BPC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI%2BPEEgaHJlZj0nTmV3Qm9vS1NjYXJjaC5hc3B4Jz48c3Bhbj7mlrDkuabpgJrmiqU8L3NwYW4%2BPC9BPjwvdGQ%2BPHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J1JlYWRlckxvZ2luLmFzcHgnPjxzcGFuPuivu%2BiAheeZu%2BW9lTwvc3Bhbj48L0E%2BPC90ZD5kZAIDDw8WAh8BBQbph5Hnm5hkZAIFD2QWBAICDw8WAh8BBTI8c3Bhbj7mrKLov47mgqg6R3Vlc3Qg6K%2B36YCJ5oup5L2g55qE5pON5L2cPC9zcGFuPmRkAgMPDxYCHgdWaXNpYmxlaGRkAgcPZBYCAgEPZBYCAgMPZBYCZg8QZBAVBAzor7vogIXmnaHnoIEM5YCf5Lmm6K%2BB5Y%2B3DOi6q%2BS7veivgeWPtwblp5PlkI0VBAzor7vogIXmnaHnoIEM5YCf5Lmm6K%2BB5Y%2B3DOi6q%2BS7veivgeWPtwblp5PlkI0UKwMEZ2dnZ2RkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQxJbWFnZUJ1dHRvbjH7vrQe9JoAYvjRbcLf2%2BUNiyrk5g%3D%3D&__EVENTVALIDATION=%2FwEWCQKrg%2BTfCgLgnZ70BALntNySDgLrr%2BCHBALXkt%2B6BALwuLirBQLs0bLrBgLs0fbZDALSwpnTCDlTzIwQuOzCdY9rJJl4DcvXMvtn&DropDownList1=%E8%AF%BB%E8%80%85%E6%9D%A1%E7%A0%81&"+data+"&__ASYNCPOST=true&ImageButton1.x=29&ImageButton1.y=12";
  		byte[] Data = ss.getBytes();
  		String uu="http://202.202.121.3/netweb/Readerlogin.aspx ";
		URL url=new URL(uu);
		 HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		 //简单的必要设置:	  
		 conn.setRequestMethod("POST");
		 conn.setDoOutput(true);		    
		 conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");	 
		 conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");	         	
		 OutputStream outStream = conn.getOutputStream();    
		 outStream.write(Data);	     
		 outStream.flush();	      
		 outStream.close();	       	    	   		 
		 //conn.getResponseCode()//响应代码 200表示成功
		 Cookie=conn.getHeaderField("Set-Cookie");
 	     InputStream is=conn.getInputStream();   //获取输入流，此时才真正建立链接
 	     Html=StreamToString(is,"utf-8");
 	     //匹配是否登录成功
		 String p="ReaderTable";
		   // 创建 Pattern 对象
	      Pattern r = Pattern.compile(p);
	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(Html);	     
	      if (m.find()) 
	      {
	    	  Log.i("Sucess","登陆成功");
	    	  return Cookie;
	      }	
	      else 
	    	  return "";
	}
	public String ReadBookInfo(String cookie) throws Exception
	{
		Cookie=cookie;	
		return Web("http://202.202.121.3/netweb/ReaderTable.aspx","utf-8");
	}
	public String Web(String burl,String code) throws Exception
	{
		Url=burl;
		 URL url=new URL(burl);
		 HttpURLConnection conn = (HttpURLConnection)url.openConnection();   
		 conn.setRequestProperty("UserAgent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		 conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");	 
		 conn.setRequestProperty("Cookie", Cookie);
		//获取输出内容
	     InputStream is=conn.getInputStream();   //获取输入流，此时才真正建立链
	 	return StreamToString(is,code);
	}
	public String BorrowBook(String Burl,String code,String cookie) throws Exception
	{
		Cookie=cookie;
		return Web(Burl,code);
	}
	
	//流的转换为字符串
	public String StreamToString(InputStream is,String encoding) throws Exception
	{
	 	   BufferedReader reader = new BufferedReader(new InputStreamReader(is,encoding));   
	       StringBuilder sb = new StringBuilder();   
	       String line = null;   
	           while ((line = reader.readLine()) != null)
	           {   
	               sb.append(line + "\n");   
	           }
			return sb.toString();   
	}

}
