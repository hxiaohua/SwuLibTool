package com.example.swubook;

import android.os.Handler;
import android.os.Message;

public class ThBookBorrow extends Thread {

	public String LibCookie;
	public String BookUrl;
	private Handler handler;

	//构造函数，数据初始化
	ThBookBorrow(Handler handler,String BookUrl,String cookie)
	{
		this.BookUrl=BookUrl;
		this.handler=handler;
		this.LibCookie=cookie;
	}
	//获取bookUrl中的学号，获取bookcode
	//run方法，线程开始会执行的任务
	public void run()
	{		
		HttpHelp Http=new HttpHelp();	
		Message msg = new Message();
		String t="";
		String burl="http://202.202.121.3/netweb/CheckBespeak.aspx?"+BookUrl;
		try {
			t=Http.BorrowBook(burl,"utf-8",LibCookie);
			msg.obj=t;
		} catch (Exception e) {
			msg.obj="默认信息";
			e.printStackTrace();
			return;
		}
        handler.sendMessage(msg);		
	}
}
