package com.example.swubook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

public class ThLib extends Thread {
	
	//ִ�е�¼����ȡ�鼮��Ϣ�Ĳ���
	public String Data;
	private Handler handler;
	//���캯��
	public ThLib(Handler handler,String Data)
	{
		this.handler=handler;
		this.Data=Data;
	}

	//run�������߳̿�ʼ��ִ�е�����
	public void run ()
	{
		HttpHelp Http=new HttpHelp();	
		Message msg = new Message();
		String t="";
		try {
			t=Http.ReadBookInfo(Data);
			msg.obj=t;
		} catch (Exception e) {
			msg.obj="Ĭ����Ϣ";
			e.printStackTrace();
			return;
		}
        handler.sendMessage(msg);
	}
}
