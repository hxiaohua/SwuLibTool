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

	//定义控件
	private	Spinner spinner;
	private EditText Uid;
	private EditText Pwd;
	private String LibCookie="";
	int knum = 20;//最多支持书目数量
    Book[] book = new Book[20]; 
	String Temp="暂无无续借信息！";
	ArrayList<String> BookList = new ArrayList<String>(); 
    //程序创建时候执行的代码
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mytest);			
	}
	//登录读取按钮
	public void login(View view)
	{         
		spinner = (Spinner) findViewById(R.id.spinner1);
		Uid=(EditText) findViewById(R.id.et_username);
		Pwd=(EditText) findViewById(R.id.et_password);
		if((TextUtils.isEmpty(Uid.getText()))|(TextUtils.isEmpty(Pwd.getText())))				
		{					
			Toast.makeText(getApplicationContext(), "请输入账号密码！",Toast.LENGTH_LONG).show();		
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
					Toast.makeText(getApplicationContext(), "登录失败,请确认账号密码",Toast.LENGTH_SHORT).show();
					return;
				}				
				LibCookie=msg.obj.toString();	
				Toast.makeText(getApplicationContext(), "登录成功,开始读取书架!",Toast.LENGTH_SHORT).show();	
	        //	 tv1.setText("登录成功,开始读取书架!");
	        	 Handler handler= new Handler()
	     		{
	     			public void handleMessage(Message msg) 
	     			{			
	     				super.handleMessage(msg);			
	     				Temp=msg.obj.toString();
	     				//从子线程返回的网页源代码进行处理
	     				String tt=Temp;
	     			     tt=tt.replaceAll("<font color=\"Black\">", "");
	     		           tt=tt.replaceAll("</font></td><td>", "#");
	     				//匹配书籍信息
	     				//匹配书籍信息，并显示
	     				String pbook="([A-Z]\\d{5,10})#(.*)#.*#.*#\\n.*\\n.*BookID=(\\d*)";
	     				Pattern r = Pattern.compile(pbook);
	     				Matcher m=r.matcher(tt);
	     		         //0.整个表达式 1.Code 2.书名 3.ID数字			
	     		         int k = 0;	      
	     				BookList.add("请下拉选择书名");
	     		         while (m.find())
	     		         {	
	     		             //提取匹配项内的分组信息
	     		             book[k] = new Book();
	     		             book[k].bookurl = "BookID="+m.group(3)+"&BookCode="+m.group(1);
	     		             book[k].bookname = m.group(2);
	     		             BookList.add(book[k].bookname);
	     		             k++;
	     		         }
	     		         knum=k;
	     				Toast.makeText(getApplicationContext(), "读到"+knum+"本书，请下拉选择！!",Toast.LENGTH_LONG).show();	
	     	        	spinner .setAdapter(adapter);		        	
	     			}			 				
	     		};
	     		new ThLib(handler,LibCookie).start();
			}			 				
		};
		new LoginThread(ssid,han).start();
		
	}
	//查看借书详情
	public void borrow(View view)
	{
		if(BookList.size()<=1)
		{
			Toast.makeText(getApplicationContext(), "没有可供查询的书目详情！",Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(getApplicationContext(), "处理中，请稍候...",Toast.LENGTH_SHORT).show();
		//启动活动2
		Intent intent=new Intent(this,BookActivity.class);
		//传递数据方法
		Bundle bundle=new Bundle();
		bundle.putString("BookHtml", Temp);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	//续借开始
	public void borrowBook(View view)
	{
		if(BookList.isEmpty())
		{
			Toast.makeText(getApplicationContext(), "请先登录读取数据！",Toast.LENGTH_SHORT).show();
			return;
		}
		if(knum==0)
		{
			Toast.makeText(getApplicationContext(), "你好像没有借书哦！",Toast.LENGTH_SHORT).show();
			return;
		}
		//进行续借的相关操作
		//拿到被选择项的值  
	  int pos = spinner.getSelectedItemPosition();
	  if(pos>knum)
	  {
			Toast.makeText(getApplicationContext(), "你连续登录了多次，请再登录一次后等待读取结束！",Toast.LENGTH_SHORT).show();
			return;
	  }	
	  //确定所选书目，传递参数
	  if(pos==0)
	  {
			Toast.makeText(getApplicationContext(), "请先下拉选择书籍",Toast.LENGTH_SHORT).show();
			return;
	  }
	
	  Toast.makeText(getApplicationContext(), "第"+pos+"本书续借中，请稍候！",Toast.LENGTH_SHORT).show();
			//对应数组，pos-1下标
		
	  //bookid,LibCookie
		
	  Handler handler= new Handler()
			{
				public void handleMessage(Message msg) 
				{			
					super.handleMessage(msg);			
					String ht=msg.obj.toString();
					String ss="续借成功";
     				Pattern r = Pattern.compile(ss);
     				Matcher m=r.matcher(ht);   
     		         if (m.find())
     		         {						
     		        	 Toast.makeText(getApplicationContext(), "恭喜你，续借成功！",Toast.LENGTH_SHORT).show();
     		         }    	
     		         else 
     		         {
     		        	 Toast.makeText(getApplicationContext(), "抱歉，续借失败！",Toast.LENGTH_SHORT).show();
     		         }
				}
			};
			new ThBookBorrow(handler, book[pos-1].bookurl,LibCookie).start();
	  }	  
}

