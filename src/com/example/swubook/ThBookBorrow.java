package com.example.swubook;

import android.os.Handler;
import android.os.Message;

public class ThBookBorrow extends Thread {

	public String LibCookie;
	public String BookUrl;
	private Handler handler;

	//���캯�������ݳ�ʼ��
	ThBookBorrow(Handler handler,String BookUrl,String cookie)
	{
		this.BookUrl=BookUrl;
		this.handler=handler;
		this.LibCookie=cookie;
	}
	//��ȡbookUrl�е�ѧ�ţ���ȡbookcode
	//run�������߳̿�ʼ��ִ�е�����
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
			msg.obj="Ĭ����Ϣ";
			e.printStackTrace();
			return;
		}
        handler.sendMessage(msg);		
	}
}
