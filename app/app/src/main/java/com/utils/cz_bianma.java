package com.utils;

//import com.e4a.runtime.annotations.SimpleObject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class cz_bianma {
    private cz_bianma() {
    }

    
    public static String URL编码(String str, String charset) {
        String str2 = "";
        try {
            return URLEncoder.encode(str, charset);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

   
    public static String URL解码(String str, String charset) {
        String str2 = "";
        try {
            return URLDecoder.decode(str, charset);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    
    public static String 编码转换(String str, String oldCharset, String newCharset) {
        if (str == null) {
            return "";
        }
        try {
            return new String(str.getBytes(oldCharset), newCharset);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    
    public static String UCS2解码(String theString) {
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        int x = 0;
        while (x < len) {
            int x2 = x + 1;
            char aChar = theString.charAt(x);
            if (aChar == '\\') {
                x = x2 + 1;
                aChar = theString.charAt(x2);
                if (aChar == 'u') {
                    int value = 0;
                    int i = 0;
                    while (i < 4) {
                        x2 = x + 1;
                        aChar = theString.charAt(x);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = ((value << 4) + aChar) - 48;
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (((value << 4) + 10) + aChar) - 65;
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (((value << 4) + 10) + aChar) - 97;
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                        i++;
                        x = x2;
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
                x = x2;
            }
        }
        return outBuffer.toString();
    }

    
    public static String UCS2编码(String dataStr) {
        String str = "";
        for (int i = 0; i < dataStr.length(); i++) {
            String temp = Integer.toHexString(dataStr.charAt(i) & 65535);
            if (temp.length() == 2) {
                temp = "00" + temp;
            }
            str = str + "\\u" + temp;
        }
        return str;
    }
}
