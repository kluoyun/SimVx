package com.utils;


import java.net.URL;
import android.app.*;
import android.os.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.Map;
import java.util.List;
import java.io.PrintWriter;
//import com.klyun.mozilla.javascript.*;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.net.HttpURLConnection;
import java.io.InputStream;
import android.graphics.*;
import java.io.*;
import java.util.*;
import android.util.*;
import java.net.*;
import java.util.regex.*;

public class util {
	private static String fh_cookie;
	private static String 请求头;
	private static boolean 重定向;
	
	public static Bitmap base64到图片(String str){
		byte[] data = Base64.decode(str,Base64.DEFAULT);
		Bitmap bit = util.字节到图片(data);
		return bit;
	}
	
	public static Bitmap 字节到图片(byte[] b){
		if (b.length != 0) {
			Bitmap bit = BitmapFactory.decodeByteArray(b,0,b.length);
			return bit;
			} else {
			     return null;
			}
	}
	
	public static boolean 邮箱是否合法(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
	
	public static String url_encode(String url)   
	{   
		try {   
			String encodeURL=URLEncoder.encode( url, "UTF-8" );   
			return encodeURL;   
		} catch (UnsupportedEncodingException e) {   
			return "";
		}
	}
	public static String url_decode(String url){ 
		try { 
			String prevURL="";   
			String decodeURL=url;   
			while(!prevURL.equals(decodeURL)) 
			{ 
				prevURL=decodeURL;   
				decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );   
			}   
			return decodeURL;   
		} catch (UnsupportedEncodingException e) { 
			return "";   
		}   
	}
	
	public static String 取随机字符串(int length){  
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  

        for(int i = 0 ; i < length; ++i){  
            int number = random.nextInt(62);//[0,62)  

            sb.append(str.charAt(number));  
        }  
        return sb.toString().replaceAll("\\s*","");
    }
	
	public static String base64_encode(String str){
        String enUid = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
        return enUid.replaceAll(" ","");
    }
	public static String base64_decode(String base64){
		String result = new String(Base64.decode(base64.getBytes(),Base64.DEFAULT));
		return result.replaceAll(" ","");
	}
	
	public static String 取回cookie(){
		return fh_cookie;
	}
	public static Object[] 数组去重复(Object [] arr){
        //实例化一个set集合
        Set set = new HashSet();
        //遍历数组并存入集合,如果元素已存在则不会重复存入
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        //返回Set集合的数组形式
        return set.toArray();
    }
	
	public static void 设置请求头(String refer){
		请求头 = refer;
	}
	
	public static void 禁止重定向(boolean loca){
		重定向 = loca;
	}
	
	public static String 合并更新cookie(String cookies旧,String cookies新){
		/*String ck = cz_wenben.子文本替换(cookies旧+cookies新,";;",";");
		Object[] ck组 = 数组去重复(cz_wenben.分割文本(ck,";"));
		String xckz = "";
		for(int i = 0;i < ck组.length;i++){
			xckz = xckz + ";" + ck组[i];
		}
		return xckz;*/
		String[] 旧cookie组 = cz_wenben.分割文本(cz_wenben.子文本替换(cookies旧,";;",";"),";");
		String[] 新cookie组 = cz_wenben.分割文本(cz_wenben.子文本替换(cookies新,";;",";"),";");
		String returnck=cookies旧;
		for(int i=0;i < 旧cookie组.length;i++){
			String j_ck = cz_wenben.分割文本(旧cookie组[i],"=")[0];
			for(int j=0;j < 新cookie组.length;j++){
				String x_ck = cz_wenben.分割文本(新cookie组[j],"=")[0];
				if(j_ck.equals(x_ck)){
					if(!cz_wenben.分割文本(新cookie组[j],"=")[1].equals("")){
						returnck=cz_wenben.子文本替换(returnck,旧cookie组[i],新cookie组[j]);
					}
					
				}else{
					if(cz_wenben.寻找文本(returnck,新cookie组[j],0) == -1){
						returnck=returnck+";"+新cookie组[j];
					}
				}
			}
		}
		Object[] ck = cz_wenben.分割文本(cz_wenben.子文本替换(returnck,";;",";"),";");
   /*     Set set = new HashSet();
        //遍历数组并存入集合,如果元素已存在则不会重复存入
        for (int i = 0; i < ck.length; i++) {
            set.add(ck[i]);
        }
		ck = set.toArray();*/
		String cookie = "";
		for(int c = 0;c < ck.length;c++){
			if(c == ck.length-1){
				cookie = cookie + ck[c];
			}else{
				cookie = cookie + ck[c] + ";";
			}
		}
		return cookie+";";
	}
	
	public static String 发送GET(String 网址,String 编码,String 传入cookie,int 超时) {
		String result = "";
		fh_cookie = "";
		try {
            int i;
            HttpURLConnection conn = (HttpURLConnection) new URL(网址).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(超时);
            conn.setReadTimeout(超时);
			if (传入cookie != null && !传入cookie.equals("")) {
                conn.setRequestProperty("Referer", 请求头);
            }else{
				conn.setRequestProperty("Referer", 网址);
			}
            conn.setInstanceFollowRedirects(重定向);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.29 Safari/525.13");
            if (传入cookie != null && !传入cookie.equals("")) {
                conn.setRequestProperty("Cookie", 传入cookie);
            }
        	
            InputStream inStream = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                i = inStream.read(buffer);
                if (i == -1) {
                    break;
                }
                outStream.write(buffer, 0, i);
            }
            String str = new String(outStream.toByteArray(), 编码);
            String key = "";
            if (conn != null) {
                i = 0;
                while (true) {
                    key = conn.getHeaderFieldKey(i);
                    if (key == null) {
                        break;
                    }
                    if (key.equalsIgnoreCase("Set-Cookie")) {
                        fh_cookie = conn.getHeaderField(key) + ";" + fh_cookie;
                    }
                    i++;
                }
            }
            outStream.close();
            inStream.close();
			请求头="";
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
	}
	
	public static String 发送POST(String 网址, String 数据,String 编码,int 超时,String 传入cookie) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		fh_cookie = "";
		Exception e;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(网址).openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(超时);
            urlConnection.setConnectTimeout(超时);
            urlConnection.setRequestProperty("Connection", "keep-alive");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(数据.getBytes().length));
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            
            if (传入cookie != null && !传入cookie.equals("")) {
                urlConnection.setRequestProperty("Cookie", 传入cookie);
            } else if (!传入cookie.equals("")) {
                urlConnection.setRequestProperty("Cookie", 传入cookie);
            }
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
			
            OutputStream os = urlConnection.getOutputStream();
            os.write(数据.getBytes(编码));
            os.flush();
            InputStream is = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                baos.write(buffer, 0, len);
            }
            is.close();
            baos.close();
            String str = new String(baos.toByteArray(), 编码);
            try {
                String key = "";
                if (urlConnection != null) {
                    int i = 1;
                    while (true) {
                        key = urlConnection.getHeaderFieldKey(i);
                        if (key == null) {
                            break;
                        }
                        if (key.equalsIgnoreCase("Set-Cookie")) {
                            fh_cookie = urlConnection.getHeaderField(key) + ";" + fh_cookie;
                        }
                        i++;
                    }
                }
                return str;
            } catch (Exception e2) {
                e = e2;
                result = str;
                e.printStackTrace();
                return result;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return result;
        }
	}
	
	public String 读取文本文件(String 路径,String 编码){
        //设置默认编码
        if(编码 == null){
            编码 = "UTF-8";
        }
		File file = new File(路径);
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, 编码);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    sb.append(text);
                }
                return sb.toString();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return null;
	}
	
	public boolean 写出文本文件(String 路径,String 写出文本){
		
		try {
			
			File file = new File(路径);
			//文件不存在时候，主动穿件文件。
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file,false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(写出文本);
			bw.close(); fw.close();
			return true;

		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
	}
	/*
	public static String 执行js(String js代码, String 函数名, Object[] 传入参数) {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();
            ScriptableObject.putProperty(scope, "javaContext", Context.javaToJS(MainActivity.LAUNCHER_APPS_SERVICE, scope));
            ScriptableObject.putProperty(scope, "javaLoader", Context.javaToJS(MainActivity.class.getClassLoader(), scope));

            rhino.evaluateString(scope, js代码, "MainActivity", 1, null);

            Function function = (Function) scope.get(函数名, scope);

            Object result = function.call(rhino, scope, scope, 传入参数);
            if (result instanceof String) {
                return (String) result;
            } else if (result instanceof NativeJavaObject) {
                return (String) ((NativeJavaObject) result).getDefaultValue(String.class);
            } else if (result instanceof NativeObject) {
                return (String) ((NativeObject) result).getDefaultValue(String.class);
            }
            return result.toString();//(String) function.call(rhino, scope, scope, functionParams);
        } finally {
            Context.exit();
        }
    }
	*/
	public static Bitmap 取网络图片(String src,String 传入cookie) {
		fh_cookie = "";
		try {
			//Log.d("FileUtil", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			if (传入cookie != null){
				connection.setRequestProperty("Cookie",传入cookie);
			}
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			String key;
			String head = "";
			//for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++){
			//	head = head + "\n" + key+":";
			//	head = head + connection.getHeaderField(key);
			//}
			if (connection != null) {
               int i = 1;
                while (true) {
                    key = connection.getHeaderFieldKey(i);
                    if (key == null) {
                        break;
                    }
                    if (key.equalsIgnoreCase("Set-Cookie")) {
                        fh_cookie = connection.getHeaderField(key) + ";" + fh_cookie;
                    }
                    i++;
                }
            }
			//设置固定大小
			//需要的大小
			float newWidth = 165f;
			float newHeigth = 165f;

			//图片大小
			int width = myBitmap.getWidth();
			int height = myBitmap.getHeight();

			//缩放比例
			float scaleWidth = newWidth / width;
			float scaleHeigth = newHeigth / height;
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeigth);

			Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
			return bitmap;
		} catch (IOException e) {
			// Log exception
			return null;
		}
	}
	
	public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }
	
	public static boolean 文件是否存在(String path){
		File file = new File(path);
		return file.exists();
	}
	
	public static String rc4_code(String aInput,String aKey)
	{
		int[] iS = new int[256];
		byte[] iK = new byte[256];
		for (int i=0;i<256;i++)
			iS[i]=i;
		int j = 1;
		for (short i= 0;i<256;i++)
		{
			iK[i]=(byte)aKey.charAt((i % aKey.length()));
		}
		j=0;
		for (int i=0;i<255;i++)
		{
			j=(j+iS[i]+iK[i]) % 256;
			int temp = iS[i];
			iS[i]=iS[j];
			iS[j]=temp;
		}
		int i=0;
		j=0;
		char[] iInputChar = aInput.toCharArray();
		char[] iOutputChar = new char[iInputChar.length];
		for(short x = 0;x<iInputChar.length;x++)
		{
			i = (i+1) % 256;
			j = (j+iS[i]) % 256;
			int temp = iS[i];   
			iS[i]=iS[j];
			iS[j]=temp;
			int t = (iS[i]+(iS[j] % 256)) % 256;
			int iY = iS[t];
			char iCY = (char)iY;
			iOutputChar[x] =(char)( iInputChar[x] ^ iCY);
		}
		return new String(iOutputChar);
	}
	
	public static String get_time()
	{
		Long lon;
		String lstime;
		lon = System.currentTimeMillis();
		lstime = lon.toString();
		return lstime;
	}
	
	
}
