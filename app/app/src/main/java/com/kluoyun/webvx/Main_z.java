package com.kluoyun.webvx;

import android.os.*;
import android.app.*;
import com.githang.statusbar.*;
import android.view.*;
import android.graphics.*;
import android.content.*;
import android.widget.*;
import java.util.*;
import android.widget.AdapterView.*;
import android.content.Context;
import java.io.*;
import com.bumptech.glide.*;
import java.security.*;
import java.math.*;

import com.utils.*;

public class Main_z extends ListActivity
{
	private String[] mListTitle;
    private String[] mListStr;
	private String[] imageurl;
	private String cookie;
	private String username;
	private String nickname;
	private String qm;
	private String wxuin;
	private String wxsid;
	private String skey;
	private String ticket;
	private String 好友数据;
	
	private EditText EditText1;
	
    ListView mListView = null;
    ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();;
	private Handler 多线程 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			int type = msg.what;
			if(type == 0){
				SimpleAdapter adapter = new SimpleAdapter(Main_z.this,mData,R.layout.list_temo,new String[]{"image","title","text"},new int[]{R.id.image,R.id.title,R.id.text});
				ImageView a=(ImageView) findViewById(R.id.image);
				//Glide.with(Main_z.this).load("").error(R.drawable.ic_launcher).into(a);
				setListAdapter(adapter);
				mListView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int position,
												long id) {
							Toast.makeText(Main_z.this,"您选择了标题：" + mListTitle[position] + "内容："+mListStr[position], Toast.LENGTH_LONG).show();
							if(position == 2){
								new Thread(){
									@Override
									public void run(){
										获取好友();
										多线程.sendEmptyMessage(20);
									}}.start();
							}
						}
					});
			}else if(type == 20){
				dialog("t",好友数据,"","ok");
			}
		}};
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setContentView(R.layout.main_z);
		cookie = cz_shuju.读取数据(Main_z.this,"cookie");
		username = cz_shuju.读取数据(Main_z.this,"username");
		nickname = cz_shuju.读取数据(Main_z.this,"nickname");
		qm = cz_shuju.读取数据(Main_z.this,"qm");
		wxuin = cz_shuju.读取数据(Main_z.this,"wxuin");
		wxsid = cz_shuju.读取数据(Main_z.this,"wxsid");
		skey = cz_shuju.读取数据(Main_z.this,"skey");
		ticket = cz_shuju.读取数据(Main_z.this,"ticket");
		dialog(ticket,skey+cookie,wxuin,wxsid);
		EditText1 = (EditText) findViewById(R.id.mainzEditText1);
		mListView = getListView();
		/*Thread csh = new Thread(){
			@Override
			public void run(){
				加载();
				多线程.sendEmptyMessage(0);
				}};
		csh.start();
		*/
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		new Thread(){
			@Override
			public void run(){
				获取好友();
				多线程.sendEmptyMessage(20);
			}}.start();
	}
	
	public void 获取好友(){
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket="+ticket+"&r="+util.get_time()+"&seq=0&skey="+skey;
		好友数据 = util.发送GET(url,"utf-8","",5000);
	}
	
	public void 加载(){
		String dszb = util.发送GET("http://www.klyun.top/dszb/jk.txt","gbk","",5000);
		mListTitle = cz_wenben.取指定文本(dszb,"name=\"","\"");
		mListStr = cz_wenben.取指定文本(dszb,"url=\"","\"");
		imageurl = cz_wenben.取指定文本(dszb,"tb=\"","\"");
		int lengh = mListTitle.length;
		for(int i =0; i < 4; i++) {
			Map<String,Object> item = new HashMap<String,Object>();
			
			if(!util.文件是否存在(util.getMD5(imageurl[i])+"_.jpg")){
				try{
					saveFile(util.取网络图片(imageurl[i],""),util.getMD5(imageurl[i])+"_.jpg");
					item.put("image","/sdcard/revoeye/"+util.getMD5(imageurl[i])+"_.jpg" );
				}catch(IOException e){
				}
			}else{
				item.put("image","/sdcard/revoeye/"+util.getMD5(imageurl[i])+"_.jpg" );
			}
			
			item.put("title", mListTitle[i]);
			item.put("text", mListStr[i]);
			mData.add(item);
		}
	}
	
	public void saveFile(Bitmap bm, String fileName) throws IOException { 
		String path = "sdcard/revoeye/";     
        File dirFile = new File(path);  
        if(!dirFile.exists()){  
            dirFile.mkdir();  
        }  
        File myCaptureFile = new File(path + fileName);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
    }
	public void onBackPressed() {//监听返回键
		AlertDialog.Builder builder = new AlertDialog.Builder(Main_z.this);
		builder.setTitle("提示")
			.setMessage("您真的要退出WebVX吗？要不再玩玩。")
			.setIcon(R.drawable.image_back)
			//.setNeutralButton("",null)
			//.setView(dialogView)
			.setPositiveButton("再玩玩", null)
			.setNegativeButton("不玩了", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, 
									int which) {
					//Intent intent = new Intent();//切换界面
					//intent.setClass(Main_z.this,MainActivity.class);
					//Main_z.this.startActivity(intent);
					//Main_z.this.finish();
					back();//退出程序
				}})
			.show();
	}
	public void back(){//退出程序
		super.onBackPressed();
	}
	public void dialog(String title,String msg,String button1,String button2) {
		//final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dh_2,null);
		AlertDialog.Builder builder = new AlertDialog.Builder(Main_z.this);
		builder.setTitle(title)
			.setMessage(msg)
			.setIcon(R.drawable.ic_launcher)
			//.setNeutralButton("abc",null)
			//.setView(dialogView)
			.setPositiveButton(button1, null)
			.setNegativeButton(button2, null)
			.show();
	}
}
