package com.utils;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build.VERSION;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
/*import com.e4a.runtime.android.mainActivity;
import com.e4a.runtime.annotations.SimpleFunction;
import com.e4a.runtime.annotations.SimpleObject;
import com.e4a.runtime.annotations.UsesPermissions;
import com.e4a.runtime.collections.哈希表;*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.client.*;
import android.graphics.*;


public final class cz_wangluo {
    public static final int NETWORKTYPE_2G = 2;
    public static final int NETWORKTYPE_3G = 3;
    public static final int NETWORKTYPE_4G = 5;
    public static final int NETWORKTYPE_INVALID = 0;
    public static final int NETWORKTYPE_UNKNOWN = 6;
    public static final int NETWORKTYPE_WAP = 1;
    public static final int NETWORKTYPE_WIFI = 4;
    private static DefaultHttpClient client = new DefaultHttpClient();
    private static String cookies_get = "";
    private static String cookies_set = null;
    private static Header[] reqHeaders = null;
    private static Header[] responseHeaders = null;
    private static String 协议头 = null;
    private static String 待清除协议头 = null;
    private static boolean 永久清除协议头 = false;
	public static final String ewmck = "";

    private cz_wangluo() {
    }
	

  /*
    public static void 禁止重定向(boolean value) {
        client.setRedirectHandler(new RedirectHandler.(value));
    }
*/
   
    public static String 同步cookies(String url) {
        CookieSyncManager.createInstance(uptext.取全局上下文());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        List<Cookie> cookies = client.getCookieStore().getCookies();
        String cookieString = "";
        if (!cookies.isEmpty()) {
            for (Cookie cookie : cookies) {
                cookieString = cookie.getName() + "=" + cookie.getValue() + ";" + cookieString;
            }
            cookieString = "Set-Cookie:" + cookieString + ";domain=http://bbs.e4asoft.com" + ";path=/";
        }
        cookieManager.setCookie(url, cookieString);
        CookieSyncManager.getInstance().sync();
        return cookieString;
    }

    
    public static void 清空cookie() {
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(uptext.取全局上下文());
        CookieManager.getInstance().removeAllCookie();
    }

   
    public static String 取请求头() {
        if (reqHeaders == null) {
            return "";
        }
        String result = "";
        for (int i = 0; i < reqHeaders.length; i++) {
            String name = reqHeaders[i].getName();
            String value = reqHeaders[i].getValue();
            if (result.equals("")) {
                result = name + ":" + value;
            } else {
                result = result + "\n" + name + ":" + value;
            }
        }
        return result;
    }

    
    public static String 取响应头() {
        if (responseHeaders == null) {
            return "";
        }
        String result = "";
        for (int i = 0; i < responseHeaders.length; i++) {
            String name = responseHeaders[i].getName();
            String value = responseHeaders[i].getValue();
            if (result.equals("")) {
                result = name + ":" + value;
            } else {
                result = result + "\n" + name + ":" + value;
            }
        }
        return result;
    }

   
    public static String 发送网络数据(String loginUrl, String paramdata, String Charset, int timeout) {
        try {
            String[] keyPair;
            String param;
            HttpPost httpPost = new HttpPost(loginUrl);
            List<NameValuePair> params = new ArrayList();
            String name = "";
            String value = "";
            if (!"".equals(paramdata)) {
                if (paramdata.indexOf("&", 0) > 0) {
                    for (String param2 : paramdata.split("\\Q&\\E")) {
                        keyPair = param2.split("\\Q=\\E");
                        params.add(new BasicNameValuePair(keyPair[0], keyPair.length > 1 ? keyPair[1].trim() : ""));
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(params, Charset));
                } else if (paramdata.indexOf("=", 0) > 0) {
                    keyPair = paramdata.split("\\Q=\\E");
                    params.add(new BasicNameValuePair(keyPair[0], keyPair.length > 1 ? keyPair[1].trim() : ""));
                    httpPost.setEntity(new UrlEncodedFormEntity(params, Charset));
                } else {
                    StringEntity entity = new StringEntity(paramdata, Charset);
                    entity.setContentType("application/x-www-form-urlencoded");
                    httpPost.setEntity(entity);
                }
            }
            if (!(协议头 == null || 协议头.equals(""))) {
                for (String param22 : 协议头.split("\\Q\n\\E")) {
                    param22 = cz_wenben.删首尾空(param22);
                    name = cz_wenben.取文本左边(param22, cz_wenben.寻找文本(param22, ":", 0));
                    httpPost.addHeader(name, cz_wenben.取文本右边(param22, (cz_wenben.取文本长度(param22) - cz_wenben.取文本长度(name)) - 1));
                }
            }
            if (待清除协议头 != null) {
                httpPost.removeHeaders(待清除协议头);
                if (!永久清除协议头) {
                    待清除协议头 = null;
                }
            }
            if (!(cookies_set == null || cookies_set.equals(""))) {
                httpPost.addHeader("Cookie", cookies_set);
            }
            reqHeaders = httpPost.getAllHeaders();
            HttpParams httpparam = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpparam, timeout);
            HttpConnectionParams.setSoTimeout(httpparam, timeout);
            HttpResponse httpResponse = client.execute(httpPost);
            responseHeaders = httpResponse.getAllHeaders();
            cookies_get = "";
            Header[] headers = httpResponse.getHeaders("Set-Cookie");
            if (headers != null) {
                for (Header value2 : headers) {
                    String[] cookievalues = value2.getValue().split(";");
                    for (String split : cookievalues) {
                        keyPair = split.split("=");
                        String key = keyPair[0].trim();
                        String value22 = keyPair.length > 1 ? keyPair[1].trim() : "";
                        if (cookies_get.equals("")) {
                            cookies_get = key + "=" + value22;
                        } else {
                            cookies_get += ";" + key + "=" + value22;
                        }
                    }
                }
            }
            String result = EntityUtils.toString(httpResponse.getEntity(), Charset);
            if (result == null) {
                return "";
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e2) {
            e2.printStackTrace();
            return "";
        }
    }

   
    public static String 发送网络数据2(String IP, int PORT, String CONTENT, String CHARSET, int TIMEOUT) {
        String str1 = "";
        try {
            Socket localSocket = new Socket(IP, PORT);
            localSocket.setSoTimeout(TIMEOUT);
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream(), CHARSET));
            PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream(), CHARSET)), true);
            localPrintWriter.println(CONTENT);
            while (true) {
                String str2 = localBufferedReader.readLine();
                if (str2 == null) {
                    localSocket.close();
                    localBufferedReader.close();
                    localPrintWriter.close();
                    return str1;
                }
                str1 = str1 + str2 + "\n";
            }
        } catch (IOException e) {
            return "";
        }
    }
/*
   
    public static String 发送网络数据3(String loginUrl, 哈希表 paramHashmap, String Charset, int timeout) {
        try {
            HttpPost httpPost = new HttpPost(loginUrl);
            List<NameValuePair> params = new ArrayList();
            String name = "";
            while (paramHashmap.是否有下一个()) {
                name = paramHashmap.下一个();
                params.add(new BasicNameValuePair(name, paramHashmap.取项目(name).getString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, Charset));
            if (!(协议头 == null || 协议头.equals(""))) {
                for (String param : 协议头.split("\\Q\n\\E")) {
                    String param2 = cz_wenben.删首尾空(param2);
                    name = cz_wenben.取文本左边(param2, cz_wenben.寻找文本(param2, ":", 0));
                    httpPost.addHeader(name, cz_wenben.取文本右边(param2, (cz_wenben.取文本长度(param2) - cz_wenben.取文本长度(name)) - 1));
                }
            }
            if (待清除协议头 != null) {
                httpPost.removeHeaders(待清除协议头);
                if (!永久清除协议头) {
                    待清除协议头 = null;
                }
            }
            if (!(cookies_set == null || cookies_set.equals(""))) {
                httpPost.addHeader("Cookie", cookies_set);
            }
            reqHeaders = httpPost.getAllHeaders();
            HttpParams httpparam = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpparam, timeout);
            HttpConnectionParams.setSoTimeout(httpparam, timeout);
            HttpResponse httpResponse = client.execute(httpPost);
            responseHeaders = httpResponse.getAllHeaders();
            cookies_get = "";
            Header[] headers = httpResponse.getHeaders("Set-Cookie");
            if (headers != null) {
                for (Header value : headers) {
                    String[] cookievalues = value.getValue().split(";");
                    for (String split : cookievalues) {
                        String[] keyPair = split.split("=");
                        String key = keyPair[0].trim();
                        String value2 = keyPair.length > 1 ? keyPair[1].trim() : "";
                        if (cookies_get.equals("")) {
                            cookies_get = key + "=" + value2;
                        } else {
                            cookies_get += ";" + key + "=" + value2;
                        }
                    }
                }
            }
            String result = EntityUtils.toString(httpResponse.getEntity(), Charset);
            if (result == null) {
                return "";
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e2) {
            e2.printStackTrace();
            return "";
        }
    }
*/
    
    public static void 置cookies(String cookies) {
        cookies_set = cookies;
    }

    
    public static String 取cookies() {
        return cookies_get;
    }

    
    public static void 置附加协议头(String header) {
        协议头 = header;
    }

    
    public static void 清除协议头(String header, boolean 永久清除) {
        待清除协议头 = header;
    }
	
	public static Bitmap 取网络图片(String src) {
		try {
			//Log.d("FileUtil", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			//ewmck = connection.getHeaderField("cookie");
			//设置固定大小
			//需要的大小
			float newWidth = 200f;
			float newHeigth = 200f;

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

	
    public static byte[] 取网络文件(String Url, int timeout) {
        try {
            HttpGet httprequest = new HttpGet(Url);
            if (!(协议头 == null || 协议头.equals(""))) {
                String name = "";
                String value = "";
                for (String param : 协议头.split("\\Q\n\\E")) {
                    String param2 = cz_wenben.删首尾空(param);
                    name = cz_wenben.取文本左边(param2, cz_wenben.寻找文本(param2, ":", 0));
                    httprequest.addHeader(name, cz_wenben.取文本右边(param2, (cz_wenben.取文本长度(param2) - cz_wenben.取文本长度(name)) - 1));
                }
            }
            if (待清除协议头 != null) {
                httprequest.removeHeaders(待清除协议头);
                if (!永久清除协议头) {
                    待清除协议头 = null;
                }
            }
            if (!(cookies_set == null || cookies_set.equals(""))) {
                httprequest.addHeader("Cookie", cookies_set);
            }
            reqHeaders = httprequest.getAllHeaders();
            HttpParams httpparam = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpparam, timeout);
            HttpConnectionParams.setSoTimeout(httpparam, timeout);
            HttpResponse httpResponse = client.execute(httprequest);
            responseHeaders = httpResponse.getAllHeaders();
            cookies_get = "";
            Header[] headers = httpResponse.getHeaders("Set-Cookie");
            if (headers != null) {
                for (Header value2 : headers) {
                    String[] cookievalues = value2.getValue().split(";");
                    for (String split : cookievalues) {
                        String[] keyPair = split.split("=");
                        String key = keyPair[0].trim();
                        String value22 = keyPair.length > 1 ? keyPair[1].trim() : "";
                        if (cookies_get.equals("")) {
                            cookies_get = key + "=" + value22;
                        } else {
                            cookies_get += ";" + key + "=" + value22;
                        }
                    }
                }
            }
            byte[] result = EntityUtils.toByteArray(httpResponse.getEntity());
            if (result == null) {
                return new byte[0];
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return new byte[0];
        } catch (IOException e2) {
            e2.printStackTrace();
            return new byte[0];
        }
    }

  
    public static String 取网页源码(String Url, String Charset, int timeout) {
        try {
            HttpGet httprequest = new HttpGet(Url);
            if (!(协议头 == null || 协议头.equals(""))) {
                String name = "";
                String value = "";
                for (String param : 协议头.split("\\Q\n\\E")) {
                    String param2 = cz_wenben.删首尾空(param);
                    name = cz_wenben.取文本左边(param2, cz_wenben.寻找文本(param2, ":", 0));
                    httprequest.addHeader(name, cz_wenben.取文本右边(param2, (cz_wenben.取文本长度(param2) - cz_wenben.取文本长度(name)) - 1));
                }
            }
            if (待清除协议头 != null) {
                httprequest.removeHeaders(待清除协议头);
                if (!永久清除协议头) {
                    待清除协议头 = null;
                }
            }
            if (!(cookies_set == null || cookies_set.equals(""))) {
                httprequest.addHeader("Cookie", cookies_set);
            }
            reqHeaders = httprequest.getAllHeaders();
            HttpParams httpparam = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpparam, timeout);
            HttpConnectionParams.setSoTimeout(httpparam, timeout);
            HttpResponse httpResponse = client.execute(httprequest);
            responseHeaders = httpResponse.getAllHeaders();
            HttpEntity he = httpResponse.getEntity();
            if (he == null) {
                return "";
            }
            cookies_get = "";
            Header[] headers = httpResponse.getHeaders("Set-Cookie");
            if (headers != null) {
                for (Header value2 : headers) {
                    String[] cookievalues = value2.getValue().split(";");
                    for (String split : cookievalues) {
                        String[] keyPair = split.split("=");
                        String key = keyPair[0].trim();
                        String value22 = keyPair.length > 1 ? keyPair[1].trim() : "";
                        if (cookies_get.equals("")) {
                            cookies_get = key + "=" + value22;
                        } else {
                            cookies_get += ";" + key + "=" + value22;
                        }
                    }
                }
            }
            String result = EntityUtils.toString(he, Charset);
            if (result == null) {
                return "";
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    
    public static String 取网页源码2(String url, String charset, int timeout) {
        try {
            int i;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setRequestProperty("Referer", url);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN)");
            if (cookies_set != null && !cookies_set.equals("")) {
                conn.setRequestProperty("Cookie", cookies_set);
            } else if (!cookies_get.equals("")) {
                conn.setRequestProperty("Cookie", cookies_get);
            }
            if (!(协议头 == null || 协议头.equals(""))) {
                String name = "";
                String value = "";
                for (String param : 协议头.split("\\Q\n\\E")) {
                    String param2 = cz_wenben.删首尾空(param);
                    name = cz_wenben.取文本左边(param2, cz_wenben.寻找文本(param2, ":", 0));
                    conn.setRequestProperty(name, cz_wenben.取文本右边(param2, (cz_wenben.取文本长度(param2) - cz_wenben.取文本长度(name)) - 1));
                }
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
            String str = new String(outStream.toByteArray(), charset);
            String key = "";
            if (conn != null) {
                i = 1;
                while (true) {
                    key = conn.getHeaderFieldKey(i);
                    if (key == null) {
                        break;
                    }
                    if (key.equalsIgnoreCase("Set-Cookie")) {
                        cookies_get = conn.getHeaderField(key) + ";" + cookies_get;
                    }
                    i++;
                }
            }
            outStream.close();
            inStream.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    
    public static String 发送网络数据4(String 网址, String 数据, String 编码, int 超时) {
        Exception e;
        String result = "";
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(网址).openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(超时);
            urlConnection.setConnectTimeout(超时);
            urlConnection.setRequestProperty("Connection", "keep-alive");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(数据.getBytes().length));
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            if (!(协议头 == null || 协议头.equals(""))) {
                String name = "";
                String value = "";
                for (String param : 协议头.split("\\Q\n\\E")) {
                    String param2 = cz_wenben.删首尾空(param);
                    name = cz_wenben.取文本左边(param2, cz_wenben.寻找文本(param2, ":", 0));
                    urlConnection.setRequestProperty(name, cz_wenben.取文本右边(param2, (cz_wenben.取文本长度(param2) - cz_wenben.取文本长度(name)) - 1));
                }
            }
            if (cookies_set != null && !cookies_set.equals("")) {
                urlConnection.setRequestProperty("Cookie", cookies_set);
            } else if (!cookies_get.equals("")) {
                urlConnection.setRequestProperty("Cookie", cookies_get);
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
                            cookies_get = urlConnection.getHeaderField(key) + ";" + cookies_get;
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

    
    public static int 取网络文件大小(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Accept-Encoding", "identity");
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    
    public static boolean 取网络状态() {
        ConnectivityManager conMan = (ConnectivityManager) uptext.取全局上下文().getSystemService("connectivity");
        State mobile = conMan.getNetworkInfo(0).getState();
        State wifi = conMan.getNetworkInfo(1).getState();
        if (mobile == State.CONNECTED || wifi == State.CONNECTED) {
            return true;
        }
        return false;
    }

   
    public static int 取网络类型() {
        NetworkInfo ni = ((ConnectivityManager) uptext.取全局上下文().getSystemService("connectivity")).getActiveNetworkInfo();
        if (ni == null || !ni.isConnectedOrConnecting()) {
            return 0;
        }
        if (VERSION.SDK_INT >= 14) {
            switch (ni.getType()) {
                case NETWORKTYPE_INVALID /*0*/:
                    switch (ni.getSubtype()) {
                        case NETWORKTYPE_WAP /*1*/:
                        case NETWORKTYPE_2G /*2*/:
                        case NETWORKTYPE_WIFI /*4*/:
                        case 7:
                        case 11:
                            return 2;
                        case NETWORKTYPE_3G /*3*/:
                        case NETWORKTYPE_4G /*5*/:
                        case NETWORKTYPE_UNKNOWN /*6*/:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            return 3;
                        case 13:
                            return 5;
                        default:
                            return 6;
                    }
                case NETWORKTYPE_WAP /*1*/:
                    return 4;
                default:
                    return 6;
            }
        }
        switch (ni.getType()) {
            case NETWORKTYPE_INVALID /*0*/:
                switch (ni.getSubtype()) {
                    case NETWORKTYPE_WAP /*1*/:
                    case NETWORKTYPE_2G /*2*/:
                    case NETWORKTYPE_WIFI /*4*/:
                    case 7:
                    case 11:
                        return 2;
                    case NETWORKTYPE_3G /*3*/:
                    case NETWORKTYPE_4G /*5*/:
                    case NETWORKTYPE_UNKNOWN /*6*/:
                    case 8:
                    case 9:
                    case 10:
                        return 3;
                    default:
                        return 6;
                }
            case NETWORKTYPE_WAP /*1*/:
                return 4;
            default:
                return 6;
        }
    }

    /*
    public static void 打开网络设置() {
        mainActivity.getContext().startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
    }

    
    public static void 打开指定网址(String url) {
        mainActivity.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }
*/
   
    public static String JSON解析(String json, String item, String name, int type) {
        String value = "";
        switch (type) {
            case NETWORKTYPE_WAP /*1*/:
                try {
                    value = new JSONObject(json).getString(name);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            case NETWORKTYPE_2G /*2*/:
                try {
                    value = new JSONObject(json).getJSONObject(item).getString(name);
                    break;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return "";
                }
            case NETWORKTYPE_3G /*3*/:
                try {
                    JSONArray numberList = new JSONObject(json).getJSONArray(item);
                    for (int i = 0; i < numberList.length(); i++) {
                        if (i == 0) {
                            value = numberList.getJSONObject(i).getString(name);
                        } else {
                            value = value + "\n" + numberList.getJSONObject(i).getString(name);
                        }
                    }
                    break;
                } catch (Exception e22) {
                    e22.printStackTrace();
                    return "";
                }
        }
        return value;
    }

    
    public static String 取外网IP() {
        String result = "";
        String html = 取网页源码2("http://www.123cha.com/", "UTF-8", 5000);
        if (html.equals("")) {
            return result;
        }
        String[] html2 = zhenze.正则匹配(html, "(?<=\\Q您的ip:[\\E).*?(?=\\Q</a>]\\E)");
        if (html2.length <= 0) {
            return result;
        }
        String[] html3 = html2[0].split("\\Q_blank>\\E");
        if (html3.length > 1) {
            return html3[1];
        }
        return result;
    }

    
    public static String 取手机所在地区() {
        String result = "";
        String html = 取网页源码2("http://www.123cha.com/", "UTF-8", 5000);
        if (html.equals("")) {
            return result;
        }
        String[] html2 = zhenze.正则匹配(html, "(?<=\\Q来自:&nbsp;\\E).*?(?=\\Q&nbsp;++\\E)");
        if (html2.length <= 0) {
            return result;
        }
        String[] html3 = html2[0].split("\\Q&nbsp;\\E");
        if (html3.length > 1) {
            return html3[0] + html3[1];
        }
        return result;
    }

    
    public static String 取内网IP() {
        try {
            Enumeration<NetworkInterface> mEnumeration = NetworkInterface.getNetworkInterfaces();
            while (mEnumeration.hasMoreElements()) {
                Enumeration<InetAddress> enumIPAddr = ((NetworkInterface) mEnumeration.nextElement()).getInetAddresses();
                while (enumIPAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIPAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
            return "";
        } catch (SocketException ex) {
            ex.printStackTrace();
            return "";
        }
    }

   
    public static String 域名取IP(String host) {
        try {
            String[] temp2 = ("" + InetAddress.getByName(host)).split("/");
            if (temp2.length == 2) {
                return temp2[1];
            }
            return "";
        } catch (UnknownHostException e) {
            return "";
        }
    }

   
    public static String 百度翻译(String appid, String token, String text, int from, int to) {
        String from2 = "";
        String to2 = "";
        switch (from) {
            case NETWORKTYPE_INVALID /*0*/:
                from2 = "auto";
                break;
            case NETWORKTYPE_WAP /*1*/:
                from2 = "zh";
                break;
            case NETWORKTYPE_2G /*2*/:
                from2 = "en";
                break;
            case NETWORKTYPE_3G /*3*/:
                from2 = "jp";
                break;
            case NETWORKTYPE_WIFI /*4*/:
                from2 = "kor";
                break;
            case NETWORKTYPE_4G /*5*/:
                from2 = "spa";
                break;
            case NETWORKTYPE_UNKNOWN /*6*/:
                from2 = "fra";
                break;
            case 7:
                from2 = "th";
                break;
            case 8:
                from2 = "ara";
                break;
            case 9:
                from2 = "ru";
                break;
            case 10:
                from2 = "pt";
                break;
            case 11:
                from2 = "yue";
                break;
            case 12:
                from2 = "wyw";
                break;
            case 13:
                from2 = "de";
                break;
            case 14:
                from2 = "it";
                break;
            default:
                from2 = "auto";
                break;
        }
        switch (to) {
            case NETWORKTYPE_INVALID /*0*/:
                to2 = "auto";
                break;
            case NETWORKTYPE_WAP /*1*/:
                to2 = "zh";
                break;
            case NETWORKTYPE_2G /*2*/:
                to2 = "en";
                break;
            case NETWORKTYPE_3G /*3*/:
                to2 = "jp";
                break;
            case NETWORKTYPE_WIFI /*4*/:
                to2 = "kor";
                break;
            case NETWORKTYPE_4G /*5*/:
                to2 = "spa";
                break;
            case NETWORKTYPE_UNKNOWN /*6*/:
                to2 = "fra";
                break;
            case 7:
                to2 = "th";
                break;
            case 8:
                to2 = "ara";
                break;
            case 9:
                to2 = "ru";
                break;
            case 10:
                to2 = "pt";
                break;
            case 11:
                to2 = "yue";
                break;
            case 12:
                to2 = "wyw";
                break;
            case 13:
                to2 = "de";
                break;
            case 14:
                to2 = "it";
                break;
        }
        to2 = "auto";
        int salt = new Random().nextInt(10000);
        String temp = cz_zhuanhuan.字节到文本(取网络文件("http://api.fanyi.baidu.com/api/trans/vip/translate?q=" + cz_bianma.URL编码(text, "UTF-8") + "&from=" + from2 + "&to=" + to2 + "&appid=" + appid + "&salt=" + salt + "&sign=" + cz_wenben.到小写(cz_jiami.取MD5值(cz_zhuanhuan.文本到字节(appid + text + salt + token, "UTF-8"))), 5000), "UTF-8");
        if (cz_wenben.取文本长度(temp) <= 5 || cz_wenben.寻找文本(temp, "error", 0) != -1) {
            return "";
        }
        String[] shuzu1 = cz_wenben.分割文本(temp, "dst\":\"");
        if (shuzu1.length < 2) {
            return "";
        }
        String[] shuzu2 = cz_wenben.分割文本(shuzu1[1], "\"");
        if (shuzu2.length >= 2) {
            return cz_bianma.UCS2解码(shuzu2[0]);
        }
        return "";
    }
}
