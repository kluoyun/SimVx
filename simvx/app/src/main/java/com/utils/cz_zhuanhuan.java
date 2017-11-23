package com.utils;

//import com.e4a.runtime.annotations.SimpleObject;
import java.io.UnsupportedEncodingException;


public final class cz_zhuanhuan {
    private cz_zhuanhuan() {
    }

    
    public static int 字符转代码(String str) {
        try {
            return str.charAt(0);
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    
    public static String 代码转字符(int value) {
        try {
            return Character.toString((char) value);
        } catch (Exception e) {
            return "";
        }
    }

    
    public static String 到十六进制(long v) {
        try {
            String str = Long.toHexString(v);
            if (str.length() < 2) {
                return "0" + str;
            }
            return str;
        } catch (Exception e) {
            return "";
        }
    }

    
    public static long 到十进制(String v) {
        long j = 0;
        try {
            if (!"".equals(v)) {
                j = Long.valueOf(v, 16).longValue();
            }
        } catch (Exception e) {
        }
        return j;
    }

   
    public static String 到二进制(int v) {
        try {
            return Integer.toBinaryString(v);
        } catch (Exception e) {
            return "";
        }
    }

    
    public static String 文本到二进制(String str) {
        try {
            String result = "";
            for (char toBinaryString : str.toCharArray()) {
                result = result + Integer.toBinaryString(toBinaryString) + " ";
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }

  
    public static String 到文本(double v) {
        try {
            return String.valueOf(v);
        } catch (Exception e) {
            return "";
        }
    }

    
    public static double 到数值(String v) {
        double d = 0.0d;
        try {
            if (!"".equals(v)) {
                d = Double.parseDouble(v);
            }
        } catch (Exception e) {
        }
        return d;
    }

   
    public static String 字节到文本(byte[] v, String encoding) {
        try {
            return new String(v, encoding);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

   
    public static byte[] 文本到字节(String v, String encoding) {
        try {
            return v.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }

    
    public static int 到整数(String v) {
        int i = 0;
        try {
            if (!"".equals(v)) {
                i = Integer.parseInt(v);
            }
        } catch (Exception e) {
        }
        return i;
    }

   
    public static String 整数到文本(int v) {
        try {
            return Integer.toString(v);
        } catch (Exception e) {
            return "";
        }
    }

  
    public static int 字节到整数(byte[] res) {
        try {
            return (((res[0] & 255) | ((res[1] << 8) & 65280)) | ((res[2] << 24) >>> 8)) | (res[3] << 24);
        } catch (Exception e) {
            return 0;
        }
    }

   
    public static int 字节到整数2(byte 字节) {
        return 字节;
    }

  
    public static byte[] 整数到字节(int res) {
        try {
            return new byte[]{(byte) (res & 255), (byte) ((res >> 8) & 255), (byte) ((res >> 16) & 255), (byte) (res >>> 24)};
        } catch (Exception e) {
            return new byte[0];
        }
    }

   
    public static byte 整数到字节2(int 整数) {
        return (byte) 整数;
    }

    
    public static byte[] 长整数到字节(long x) {
        try {
            return new byte[]{(byte) ((int) (x >> 56)), (byte) ((int) (x >> 48)), (byte) ((int) (x >> 40)), (byte) ((int) (x >> 32)), (byte) ((int) (x >> 24)), (byte) ((int) (x >> 16)), (byte) ((int) (x >> 8)), (byte) ((int) (x >> 0))};
        } catch (Exception e) {
            return new byte[0];
        }
    }
/*
   
    public static long 字节到长整数(byte[] bb) {
        try {
            return ((((((((((long) bb[0]) & 255) << 56) | ((((long) bb[1]) & 255) << 48)) | ((((long) bb[2]) & 255) << 40)) | ((((long) bb[3]) & 255) << 32)) | ((((long) bb[4]) & 255) << 24)) | ((((long) bb[5]) & 255) << 16)) | ((((long) bb[6]) & 255) << 8)) | ((((long) bb[7]) & 255) << null);
        } catch (Exception e) {
            return 0;
        }
    }

   */
    public static byte[] 小数到字节(double a) {
        try {
            long v = Double.doubleToLongBits(a);
            return new byte[]{(byte) ((int) (v >>> 56)), (byte) ((int) (v >>> 48)), (byte) ((int) (v >>> 40)), (byte) ((int) (v >>> 32)), (byte) ((int) (v >>> 24)), (byte) ((int) (v >>> 16)), (byte) ((int) (v >>> 8)), (byte) ((int) (v >>> 0))};
        } catch (Exception e) {
            return new byte[0];
        }
    }

    
    public static double 字节到小数(byte[] readBuffer) {
        try {
            return Double.longBitsToDouble((((((((((long) readBuffer[0]) << 56) + (((long) (readBuffer[1] & 255)) << 48)) + (((long) (readBuffer[2] & 255)) << 40)) + (((long) (readBuffer[3] & 255)) << 32)) + (((long) (readBuffer[4] & 255)) << 24)) + ((long) ((readBuffer[5] & 255) << 16))) + ((long) ((readBuffer[6] & 255) << 8))) + ((long) ((readBuffer[7] & 255) << 0)));
        } catch (Exception e) {
            return 0.0d;
        }
    }

    
    public static long 到长整数(String v) {
        long j = 0;
        try {
            if (!"".equals(v)) {
                j = Long.parseLong(v);
            }
        } catch (Exception e) {
        }
        return j;
    }

 
    public static String 长整数到文本(long v) {
        try {
            return Long.toString(v);
        } catch (Exception e) {
            return "";
        }
    }

    
    public static String 数值到金额(double amount) {
        if (amount > 1.0E18d || amount < -1.0E18d) {
            try {
                return "";
            } catch (Exception e) {
                return "";
            }
        }
        String[] chineseDigits = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        boolean negative = false;
        if (amount < 0.0d) {
            negative = true;
            amount *= -1.0d;
        }
        long temp = Math.round(100.0d * amount);
        int numFen = (int) (temp % 10);
        temp /= 10;
        int numJiao = (int) (temp % 10);
        temp /= 10;
        int[] parts = new int[20];
        int numParts = 0;
        int i = 0;
        while (temp != 0) {
            parts[i] = (int) (temp % 10000);
            numParts++;
            temp /= 10000;
            i++;
        }
        boolean beforeWanIsZero = true;
        String chineseStr = "";
        i = 0;
        while (i < numParts) {
            String partChinese = partTranslate(parts[i]);
            if (i % 2 == 0) {
                if ("".equals(partChinese)) {
                    beforeWanIsZero = true;
                } else {
                    beforeWanIsZero = false;
                }
            }
            if (i != 0) {
                if (i % 2 == 0) {
                    chineseStr = "亿" + chineseStr;
                } else if (!"".equals(partChinese) || beforeWanIsZero) {
                    if (parts[i - 1] < 1000 && parts[i - 1] > 0) {
                        chineseStr = "零" + chineseStr;
                    }
                    chineseStr = "万" + chineseStr;
                } else {
                    chineseStr = "零" + chineseStr;
                }
            }
            chineseStr = partChinese + chineseStr;
            i++;
        }
        if ("".equals(chineseStr)) {
            chineseStr = chineseDigits[0];
        } else if (negative) {
            chineseStr = "负" + chineseStr;
        }
        chineseStr = chineseStr + "元";
        if (numFen == 0 && numJiao == 0) {
            return chineseStr + "整";
        }
        if (numFen == 0) {
            return chineseStr + chineseDigits[numJiao] + "角";
        }
        if (numJiao == 0) {
            return chineseStr + "零" + chineseDigits[numFen] + "分";
        }
        return chineseStr + chineseDigits[numJiao] + "角" + chineseDigits[numFen] + "分";
    }

    private static String partTranslate(int amountPart) {
        if (amountPart < 0 || amountPart > 10000) {
            return "";
        }
        String[] chineseDigits = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] units = new String[]{"", "拾", "佰", "仟"};
        int temp = amountPart;
        int amountStrLength = new Integer(amountPart).toString().length();
        boolean lastIsZero = true;
        String chineseStr = "";
        for (int i = 0; i < amountStrLength && temp != 0; i++) {
            int digit = temp % 10;
            if (digit == 0) {
                if (!lastIsZero) {
                    chineseStr = "零" + chineseStr;
                }
                lastIsZero = true;
            } else {
                chineseStr = chineseDigits[digit] + units[i] + chineseStr;
                lastIsZero = false;
            }
            temp /= 10;
        }
        return chineseStr;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return ((c - 97) + 10) & 15;
        }
        if (c >= 'A') {
            return ((c - 65) + 10) & 15;
        }
        return (c - 48) & 15;
    }

    
    public static String 字节集到十六进制(byte[] b) {
        if (b == null) {
            try {
                return "";
            } catch (Exception e) {
                return "";
            }
        } else if (b.length == 0) {
            return "";
        } else {
            byte[] hex = "0123456789ABCDEF".getBytes();
            byte[] buff = new byte[(b.length * 2)];
            for (int i = 0; i < b.length; i++) {
                buff[i * 2] = hex[(b[i] >> 4) & 15];
                buff[(i * 2) + 1] = hex[b[i] & 15];
            }
            return new String(buff);
        }
    }

    
    public static byte[] 十六进制到字节集(String hexstr) {
        if (hexstr == null) {
            try {
                return new byte[0];
            } catch (Exception e) {
                return new byte[0];
            }
        } else if (hexstr.equals("")) {
            return new byte[0];
        } else {
            byte[] b = new byte[(hexstr.length() / 2)];
            int j = 0;
            for (int i = 0; i < b.length; i++) {
                int j2 = j + 1;
                char c0 = hexstr.charAt(j);
                j = j2 + 1;
                b[i] = (byte) ((parse(c0) << 4) | parse(hexstr.charAt(j2)));
            }
            return b;
        }
    }
}
