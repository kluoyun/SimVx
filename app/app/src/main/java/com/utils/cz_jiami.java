package com.utils;

//import com.e4a.runtime.annotations.SimpleFunction;
//import com.e4a.runtime.annotations.SimpleObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class cz_jiami {
    private cz_jiami() {
    }
    private static int j=2;
    public static String 取MD5值(byte[] bytes) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(bytes);
            char[] str = new char[(j * 2)];
            int k = 0;
            for (byte byte0 : mdInst.digest()) {
                int i = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = i + 1;
                str[i] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

   
    public static String RC4加密(String data, String key) {
        if (data == null || key == null) {
            return "";
        }
        try {
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            char[] str = new char[(j * 2)];
            int k = 0;
            for (byte byte0 : RC4Base(data.getBytes("GBK"), key)) {
                int i = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = i + 1;
                str[i] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String RC4解密(String data, String key) {
        if (data == null || key == null) {
            return "";
        }
        try {
            return new String(RC4Base(HexString2Bytes(data), key), "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

   
    public static byte[] RC4加密2(byte[] data, String key) {
        if (data == null || key == null) {
            return new byte[0];
        }
        try {
            byte[] hexDigits = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70};
            byte[] str = new byte[(j * 2)];
            int k = 0;
            for (byte byte0 : RC4Base(data, key)) {
                int i = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = i + 1;
                str[i] = hexDigits[byte0 & 15];
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    public static byte[] RC4解密2(byte[] data, String key) {
        int i = 0;
        if (data == null || key == null) {
            return new byte[i];
        }
        try {
            return RC4Base(data, key);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[i];
        }
    }

    private static byte[] HexString2Bytes(String src) {
        try {
            int size = src.length();
            byte[] bArr = new byte[(size / 2)];
            byte[] tmp = src.getBytes("GBK");
            for (int i = 0; i < size / 2; i++) {
                bArr[i] = uniteBytes(tmp[i * 2], tmp[(i * 2) + 1]);
            }
            return bArr;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static byte uniteBytes(byte src0, byte src1) {
        return (byte) (((char) (((char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue()) << 4)) ^ ((char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue()));
    }

    private static byte[] RC4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte[] key = initKey(mKkey);
        byte[] result = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 255;
            y = ((key[x] & 255) + y) & 255;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            result[i] = (byte) (input[i] ^ key[((key[x] & 255) + (key[y] & 255)) & 255]);
        }
        return result;
    }

    private static byte[] initKey(String aKey) {
        try {
            int i;
            byte[] b_key = aKey.getBytes("GBK");
            byte[] state = new byte[256];
            for (i = 0; i < 256; i++) {
                state[i] = (byte) i;
            }
            int index1 = 0;
            int index2 = 0;
            if (b_key == null || b_key.length == 0) {
                return null;
            }
            for (i = 0; i < 256; i++) {
                index2 = (((b_key[index1] & 255) + (state[i] & 255)) + index2) & 255;
                byte tmp = state[i];
                state[i] = state[index2];
                state[index2] = tmp;
                index1 = (index1 + 1) % b_key.length;
            }
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }}
