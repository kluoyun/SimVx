package com.kluoyun.webvx;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.text.*;
import android.view.*;
import android.util.DisplayMetrics;  
import android.widget.TextView;
import android.graphics.*;
import android.util.*;
import android.content.DialogInterface;
import android.content.Context;
import android.view.View.*;
import com.githang.statusbar.*;
import com.utils.*;
import android.graphics.drawable.*;

public class MainActivity extends Activity 
{
	private ImageView ImageView_ewm;
	private TextView TextView_xx;
	private EditText EditText_1;
	private String uuid;
	private String cookie;
	private String zt;
	private String synckey;
	private String xx_key;
	private String username;
	private String nickname;
	private String qm;
	private String wxuin;
	private String wxsid;
	private String skey;
	private String ticket;
	
	private Bitmap ewmsj = null;
	private Thread th_二维码;
	private Thread th_二维码状态;
	private Thread th_初始化;
	private String 登录头像;
	private String 初始化数据;
	String 错误提示;
	String 好友数据;
	private String 通知返回;
	
	private Handler 多线程 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			int type = msg.what;
			
			switch(type){
				case 20://成功取得二维码
					ImageView_ewm.setImageBitmap(ewmsj);
					TextView_xx.setText("扫码登录");
					th_二维码状态 = new Thread(){
						@Override
						public void run(){
							int type = 检测二维码();
							多线程.sendEmptyMessage(type);
							return;
						}
					};
					th_二维码状态.start();
					return;
				case 408://二维码正常，继续请求
					th_二维码状态 = new Thread(){
						@Override
						public void run(){
							int type = 检测二维码();
							多线程.sendEmptyMessage(type);
							return;
						}
					};
					th_二维码状态.start();
					return;
				case 400://二维码失效，重新获取
					TextView_xx.setText("二维码失效，重新获取中");
					th_二维码 = new Thread(){
						@Override
						public void run(){
							ewmsj = 获取二维码();
							多线程.sendEmptyMessage(20);
						}
					};
					th_二维码.start();
					return;
				case 201://扫描成功，但未确认登录
					TextView_xx.setText("扫描成功，请确认登录");
					
					//复制文本(登录头像);
					ImageView_ewm.setImageBitmap(util.base64到图片(登录头像));
					
					th_二维码状态 = new Thread(){
						@Override
						public void run(){
							int type = 检测二维码();
							多线程.sendEmptyMessage(type);
							return;
						}
					};
					th_二维码状态.start();
					return;
				case 200://确认登录，开始初始化程序
					TextView_xx.setText("登录成功，正在处理数据");
					th_初始化 = new Thread(){
						@Override
						public void run(){
							int a = 微信初始化();
							if(a == 0){
								多线程.sendEmptyMessage(30);
							}else{
								多线程.sendEmptyMessage(40);
							}
						}
					};
					
					th_初始化.start();
					return;
				case 199://操作微信不允许登录web微信
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("提示")
						.setMessage(错误提示)
						.setIcon(R.drawable.image_back)
						.setPositiveButton("知道了", null)
						.show();
					new Thread(){
						@Override
						public void run(){
							ewmsj = 获取二维码();
							多线程.sendEmptyMessage(20);
						}
					}.start();
					return;
				case 30://初始化成功，开始跳转界面
					TextView_xx.setText("微信初始化成功");
					//EditText_1.setText(初始化数据);
					new Thread(){
						@Override
						public void run(){
							开启通知();
							
						}
					}.start();
					return;
				case 40://初始化失败，重新获取二维码
					TextView_xx.setText("微信初始化失败，请重新扫码登录");
					th_二维码 = new Thread(){
						@Override
						public void run(){
							ewmsj = 获取二维码();
							多线程.sendEmptyMessage(20);
						}
					};
					th_二维码.start();
					return;
				case 50:
					dialog("",通知返回,"","ucyx");
					new Thread(){
						public void run(){
					获取好友();
					}
					}.start();
					return;
				case 60:
					dialog("",好友数据,"ydud","");
					return;
			}
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
		StatusBarCompat.setStatusBarColor(this, Color.parseColor("#FFFFFF"));
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		
		ImageView_ewm = (ImageView) findViewById(R.id.mainImageView1);
		TextView_xx = (TextView) findViewById(R.id.mainTextView1);
		th_二维码 = new Thread(){
			@Override
			public void run(){
				ewmsj = 获取二维码();
				多线程.sendEmptyMessage(20);
			}
		};
		th_二维码.start();
    }
	
	@Override
	public void onBackPressed() {//监听返回键
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
										back();//退出程序
				}})
				.show();
	}
	
	public void back(){//退出程序
		super.onBackPressed();
	}
	public void 复制文本(String str){
		ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		cm.setText(str);
	}
	
	public void help(View v){
		int id = v.getId();
		if(id == R.id.mainImageButton_help){
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("扫码登录帮助")
				.setMessage("由于手机微信限制以致于在扫码登录web微信时只能实时扫描，不能够通过相册中的二维码进行扫码登录否则会登录失败。给您带来的不便请谅解")
				.setIcon(R.drawable.image_help)
				//.setNeutralButton("abc",null)
				//.setView(dialogView)
				.setPositiveButton("我知道了",null)
				.show();
		}else if(id == R.id.mainImageButton_sm){
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("声明")
				.setMessage("WebVX的所有内容均来自互联网。WebVX基于web微信协议开发，请大家支持官方微信客户端，于下载安装后24小时内删除卸载")
				.setIcon(R.drawable.image_sm)
				//.setNeutralButton("abc",null)
				//.setView(dialogView)
				.setPositiveButton("我知道了",null)
				.show();
			}
	}
	
	public Bitmap 获取二维码(){
		String urls = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=" + util.get_time();
		String fhsj = util.发送GET(urls,"utf-8","",5000);
		if(cz_wenben.取指定文本2(fhsj,"window.QRLogin.code = ",";").equals("200")) {
			uuid = cz_wenben.取指定文本2(fhsj,"window.QRLogin.uuid = \"","\";");
			String url = "https://login.weixin.qq.com/qrcode/" + uuid;
			return util.取网络图片(url,"");
		}else{
			uuid = "";
			return null;
		}
	}
	
	public int 检测二维码(){
		String time = util.get_time();
		String url = "https://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + uuid + "&tip=0&r=-"+time.substring(0,10)+"&_=" + time;
		String fh = util.发送GET(url,"utf-8","",25000);
		String type = cz_wenben.取指定文本2(fh,"window.code=",";");
		//https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=A_U2QgboQRR-X6MDjZXsP_mX@qrticket_0&uuid=obk1HW25nA==&lang=zh_CN&scan=1509108874&fun=new&version=v2&lang=zh_CN
		//https://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=obk1HW25nA==&tip=0&r=-1575348250&_=1509108842677
		if(type.equals("408")){
			return 408;
		}else if(type.equals("201")){
			String tpsj = cz_wenben.取指定文本2(fh,"data:img/jpg;base64,","';");
			登录头像 = tpsj;
			return 201;
		}else if(type.equals("200")){
			String urls = cz_wenben.取指定文本2(fh,"redirect_uri=\"","\"") + "&fun=new&version=v2&lang=zh_CN";
			String xml = util.发送GET(urls,"utf-8","",5000);
			cookie = util.取回cookie();
			cz_shuju.保存数据(MainActivity.this,"cookie",cookie);
			int ret = Integer.parseInt(cz_wenben.取指定文本2(xml,"<ret>","</ret>").replaceAll(" ",""));
			if(ret == 0){
				skey = cz_wenben.取指定文本2(xml,"<skey>","</skey>");
				wxsid = cz_wenben.取指定文本2(xml,"<wxsid>","</wxsid>");
				wxuin = cz_wenben.取指定文本2(xml,"<wxuin>","</wxuin>");
				ticket = cz_wenben.取指定文本2(xml,"<pass_ticket>","</pass_ticket>");
				cz_shuju.保存数据(MainActivity.this,"skey",skey);
				cz_shuju.保存数据(MainActivity.this,"wxsid",wxsid);
				cz_shuju.保存数据(MainActivity.this,"wxuin",wxuin);
				cz_shuju.保存数据(MainActivity.this,"ticket",ticket);
				return 200;
			}else{
				错误提示 = cz_wenben.取指定文本2(xml,"<message>","</message>").replaceAll(" ","");
				return 199;
			}
			//xml=<error><ret>0</ret><message></message><skey>@crypt_26</skey><wxsid>7rc9faBkPUvyGS1G</wxsid><wxuin>2573488128</wxuin><pass_ticket>xYaqvF%2BfS6Go8w8wFQsgeZ74JuzgBVPNxUBdYZDBSY9AFRWTGRuUA4TM%2FNZmX9M9</pass_ticket><isgrayscale>1</isgrayscale></error>
			
		}else if(type.equals("400")){
			return 400;
		}else{
			return -1;
		}
	}
	
	public int 微信初始化(){
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=-"+util.get_time().substring(0,10)+"&lang=zh_CN&pass_ticket=" + cz_shuju.读取数据(MainActivity.this,"ticket");
		String sj = "{\"BaseRequest\":{\"Uin\":\""+cz_shuju.读取数据(MainActivity.this,"wxuin")+"\",\"Sid\":\""+cz_shuju.读取数据(MainActivity.this,"wxsid")+"\",\"Skey\":\""+cz_shuju.读取数据(MainActivity.this,"skey")+"\",\"DeviceID\":\"e66"+util.get_time()+"\"}}";
		
		String fh = util.发送POST(url,sj,"utf-8",5000,cookie);
		
		//去除返回数据中的空格换行制表符等符号
		fh = fh.replaceAll("\\s*","");
		//json解析开始
		synckey = cz_wenben.取指定文本2(cz_wenben.取指定文本2(fh,"\"SyncKey\"","\"User\""),"List\":[","]");
		
		String[] key = cz_wenben.取指定文本(synckey,"Key\":",",");
		String[] val = cz_wenben.取指定文本(synckey,"Val\":","}");
		if(!synckey.equals("")){
			//开始计算第一次心跳及读消息的synkey
			xx_key = "";
			for(int j = 0;j < key.length;j++){
				if(j == key.length-1){
					xx_key = xx_key + key[j] + "_" + val[j];
				}else{
					xx_key = xx_key + key[j] + "_" + val[j] + "|";
				}
			}
			//开始取出个人信息，username等
			String sctq = cz_wenben.取指定文本2(fh,"\"User\":{","}");
			username = cz_wenben.取指定文本2(sctq,"\"UserName\":\"","\"");
			nickname = cz_wenben.取指定文本2(sctq,"\"NickName\":\"","\"");
			String qm = cz_wenben.取指定文本2(sctq,"\"Signature\":\"","\"");
			cz_shuju.保存数据(MainActivity.this,"username",username);
			cz_shuju.保存数据(MainActivity.this,"nickname",nickname);
			cz_shuju.保存数据(MainActivity.this,"qm",qm);
			
			初始化数据 = fh;
			return 0;
		}else{
			return -1;
		}
	}
	//头像https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgeticon?seq=1499894523&username=@8721357b158fcc3c4bff9b996f7a5a050c0fe97d81912cfb2253fffb3ec1fe17&skey=@crypt_26575fbf_41fe48cb285306f25823d5ed0c5b0370
	public void 开启通知(){
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxstatusnotify?lang=zh_CN&pass_ticket="+ticket;
		String sj = "{\"BaseRequest\":{\"Uin\":"+wxuin+",\"Sid\":\""+wxsid+"\",\"Skey\":\""+skey+"\",\"DeviceID\":\"e63"+util.get_time()+"\"},\"Code\":3,\"FromUserName\":\""+username+"\",\"ToUserName\":\""+username+"\",\"ClientMsgId\":"+util.get_time()+"}";
		通知返回 = util.发送POST(url,sj,"utf-8",5000,cookie);
		
		多线程.sendEmptyMessage(50);
	}
	
	public void 获取好友(){
		//////////////https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket=Kbyd7YbPm9&r=1509340324034&seq=0&skey=@crypt_26
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket="+ticket+"&r="+util.get_time()+"&seq=0&skey="+skey;
		好友数据 = util.发送GET(url,"utf-8",null,5000);
		多线程.sendEmptyMessage(60);
		//dialog("账号信息","昵称:"+nickname+"\nusername:"+username,"ok","");
		/*Intent intent = new Intent();
		intent.setClass(MainActivity.this,Main_z.class);
		MainActivity.this.startActivity(intent);
		MainActivity.this.finish();*/
	}
	
	public Bitmap stringtoBitmap(String string){
//将字符串转换成Bitmap类型
		Bitmap bitmap=null;
		try {
			byte[]bitmapArray;
			bitmapArray=Base64.decode(string, Base64.DEFAULT);
			bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	public void dialog(String title,String msg,String button1,String button2) {
		//final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dh_2,null);
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
