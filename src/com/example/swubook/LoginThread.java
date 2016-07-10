package com.example.swubook;

import android.os.Handler;
import android.os.Message;

public class LoginThread extends Thread {

	private String data;
	private Handler handler; 
	LoginThread(String data,Handler h)
	{
		this.data=data;
		this.handler=h;
	}
	//run�������߳̿�ʼ��ִ�е�����
		public void run ()
		{
			HttpHelp Http=new HttpHelp();	
			Message msg = new Message();
			String t="";
			try {
				t=Http.LoginLib(data);
				msg.obj=t;
			} catch (Exception e) {
				msg.obj="Ĭ����Ϣ";
				e.printStackTrace();
				return;
			}
	        handler.sendMessage(msg);
		}
}
