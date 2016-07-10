package com.example.swubook;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	//����ؼ�
	private	Spinner spinner;
	private EditText Uid;
	private EditText Pwd;
	private String LibCookie="";
	int knum = 20;//���֧����Ŀ����
    Book[] book = new Book[20]; 
	String Temp="������������Ϣ��";
	ArrayList<String> BookList = new ArrayList<String>(); 
    //���򴴽�ʱ��ִ�еĴ���
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mytest);			
	}
	//��¼��ȡ��ť
	public void login(View view)
	{         
		spinner = (Spinner) findViewById(R.id.spinner1);
		Uid=(EditText) findViewById(R.id.et_username);
		Pwd=(EditText) findViewById(R.id.et_password);
		if((TextUtils.isEmpty(Uid.getText()))|(TextUtils.isEmpty(Pwd.getText())))				
		{					
			Toast.makeText(getApplicationContext(), "�������˺����룡",Toast.LENGTH_LONG).show();		
			return;
		}				
		String username=Uid.getText().toString();
		String password=Pwd.getText().toString();
		String ssid=String.format("TextBox1=%s&TextBox2=%s", username,password);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, BookList);
    	spinner .setAdapter(adapter);		        	
		adapter.clear();
		BookList.clear();
		Handler han= new Handler()
		{
			public void handleMessage(Message msg) 
			{			
				super.handleMessage(msg);		
				if(msg.obj.toString().isEmpty())
				{
					Toast.makeText(getApplicationContext(), "��¼ʧ��,��ȷ���˺�����",Toast.LENGTH_SHORT).show();
					return;
				}				
				LibCookie=msg.obj.toString();	
				Toast.makeText(getApplicationContext(), "��¼�ɹ�,��ʼ��ȡ���!",Toast.LENGTH_SHORT).show();	
	        //	 tv1.setText("��¼�ɹ�,��ʼ��ȡ���!");
	        	 Handler handler= new Handler()
	     		{
	     			public void handleMessage(Message msg) 
	     			{			
	     				super.handleMessage(msg);			
	     				Temp=msg.obj.toString();
	     				//�����̷߳��ص���ҳԴ������д���
	     				String tt=Temp;
	     			     tt=tt.replaceAll("<font color=\"Black\">", "");
	     		           tt=tt.replaceAll("</font></td><td>", "#");
	     				//ƥ���鼮��Ϣ
	     				//ƥ���鼮��Ϣ������ʾ
	     				String pbook="([A-Z]\\d{5,10})#(.*)#.*#.*#\\n.*\\n.*BookID=(\\d*)";
	     				Pattern r = Pattern.compile(pbook);
	     				Matcher m=r.matcher(tt);
	     		         //0.�������ʽ 1.Code 2.���� 3.ID����			
	     		         int k = 0;	      
	     				BookList.add("������ѡ������");
	     		         while (m.find())
	     		         {	
	     		             //��ȡƥ�����ڵķ�����Ϣ
	     		             book[k] = new Book();
	     		             book[k].bookurl = "BookID="+m.group(3)+"&BookCode="+m.group(1);
	     		             book[k].bookname = m.group(2);
	     		             BookList.add(book[k].bookname);
	     		             k++;
	     		         }
	     		         knum=k;
	     				Toast.makeText(getApplicationContext(), "����"+knum+"���飬������ѡ��!",Toast.LENGTH_LONG).show();	
	     	        	spinner .setAdapter(adapter);		        	
	     			}			 				
	     		};
	     		new ThLib(handler,LibCookie).start();
			}			 				
		};
		new LoginThread(ssid,han).start();
		
	}
	//�鿴��������
	public void borrow(View view)
	{
		if(BookList.size()<=1)
		{
			Toast.makeText(getApplicationContext(), "û�пɹ���ѯ����Ŀ���飡",Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(getApplicationContext(), "�����У����Ժ�...",Toast.LENGTH_SHORT).show();
		//�����2
		Intent intent=new Intent(this,BookActivity.class);
		//�������ݷ���
		Bundle bundle=new Bundle();
		bundle.putString("BookHtml", Temp);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	//���迪ʼ
	public void borrowBook(View view)
	{
		if(BookList.isEmpty())
		{
			Toast.makeText(getApplicationContext(), "���ȵ�¼��ȡ���ݣ�",Toast.LENGTH_SHORT).show();
			return;
		}
		if(knum==0)
		{
			Toast.makeText(getApplicationContext(), "�����û�н���Ŷ��",Toast.LENGTH_SHORT).show();
			return;
		}
		//�����������ز���
		//�õ���ѡ�����ֵ  
	  int pos = spinner.getSelectedItemPosition();
	  if(pos>knum)
	  {
			Toast.makeText(getApplicationContext(), "��������¼�˶�Σ����ٵ�¼һ�κ�ȴ���ȡ������",Toast.LENGTH_SHORT).show();
			return;
	  }	
	  //ȷ����ѡ��Ŀ�����ݲ���
	  if(pos==0)
	  {
			Toast.makeText(getApplicationContext(), "��������ѡ���鼮",Toast.LENGTH_SHORT).show();
			return;
	  }
	
	  Toast.makeText(getApplicationContext(), "��"+pos+"���������У����Ժ�",Toast.LENGTH_SHORT).show();
			//��Ӧ���飬pos-1�±�
		
	  //bookid,LibCookie
		
	  Handler handler= new Handler()
			{
				public void handleMessage(Message msg) 
				{			
					super.handleMessage(msg);			
					String ht=msg.obj.toString();
					String ss="����ɹ�";
     				Pattern r = Pattern.compile(ss);
     				Matcher m=r.matcher(ht);   
     		         if (m.find())
     		         {						
     		        	 Toast.makeText(getApplicationContext(), "��ϲ�㣬����ɹ���",Toast.LENGTH_SHORT).show();
     		         }    	
     		         else 
     		         {
     		        	 Toast.makeText(getApplicationContext(), "��Ǹ������ʧ�ܣ�",Toast.LENGTH_SHORT).show();
     		         }
				}
			};
			new ThBookBorrow(handler, book[pos-1].bookurl,LibCookie).start();
	  }	  
}

