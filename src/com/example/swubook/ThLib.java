package com.example.swubook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

public class ThLib extends Thread {
	
	//执行登录，获取书籍信息的操作
	public String Data;
	private Handler handler;
	//构造函数
	public ThLib(Handler handler,String Data)
	{
		this.handler=handler;
		this.Data=Data;
	}

	//run方法，线程开始会执行的任务
	public void run ()
	{
		HttpHelp Http=new HttpHelp();	
		Message msg = new Message();
		String t="";
		try {
			t=Http.ReadBookInfo(Data);
			msg.obj=t;
		} catch (Exception e) {
			msg.obj="默认信息";
			e.printStackTrace();
			return;
		}
        handler.sendMessage(msg);
	}
}
