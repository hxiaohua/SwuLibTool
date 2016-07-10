package com.example.swubook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class BookActivity extends Activity {

	private WebView webview;
	//显示书籍信息的次要活动
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookdetail);		
		webview=(WebView)findViewById(R.id.webView1);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setUseWideViewPort(true);
		String s="<h2>没有读取到数据！，请先登录读取！</h2>";
		Bundle b=this.getIntent().getExtras();
		if(b!=null)
		{
	           s=b.getString("BookHtml");
	           String p="<table cellspacing=\"0\" cellpadding=\"4\"[\\s\\S]*BookCode.*\\n.*\\n.*\\n";
			   // 创建 Pattern 对象
		      Pattern r = Pattern.compile(p);
		      // 现在创建 matcher 对象
		      Matcher m = r.matcher(s);	     
		      if (m.find()) 
		      {
		    	  s=m.group(0);
		      }
		      s=s+"</tr></table></td>";
		}
		webview.loadData(s,"text/html; charset=UTF-8", "utf-8");
	}
	public void ReturnIndex(View view)
	{
		finish();		
	}
	
}
