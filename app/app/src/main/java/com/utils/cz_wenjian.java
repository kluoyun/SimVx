package com.utils;

import android.content.Intent;
import android.net.Uri;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

public final class cz_wenjian {
    static BufferedReader br;
    static BufferedWriter bw;
    static FileInputStream fin;
    static FileOutputStream fout;
    static InputStreamReader isr;
    static String line = "";
    static OutputStreamWriter osw;

    public static boolean 修改文件名(String oldname, String newname) {
        if (newname.equals(oldname)) {
            return true;
        }
        File oldfile = new File(oldname);
        if (!oldfile.exists()) {
            return false;
        }
        File newfile = new File(newname);
        if (newfile.exists()) {
            return false;
        }
        return oldfile.renameTo(newfile);
    }

    public static boolean 删除文件(String name) {
        File file = new File(name);
        if (file.exists() && !file.isDirectory()) {
            return file.delete();
        }
        return false;
    }

    public static boolean 创建目录(String path) {
        String[] dir = path.split("/");
        String dist = dir[0];
        boolean result = true;
        if (dir.length <= 0) {
            return false;
        }
        for (int i = 1; i < dir.length; i++) {
            dist = new StringBuilder(String.valueOf(dist)).append("/").append(dir[i]).toString();
            File mkdir = new File(dist);
            if (!mkdir.exists()) {
                result = mkdir.mkdir();
            }
        }
        return result;
    }

    public static boolean 删除目录(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            return deleteDir(dir);
        }
        return false;
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String file : children) {
                if (!deleteDir(new File(dir, file))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean 是否为目录(String name) {
        File file = new File(name);
        if (file.exists()) {
            return file.isDirectory();
        }
        return false;
    }

    public static boolean 是否为隐藏文件(String name) {
        File file = new File(name);
        if (file.exists()) {
            return file.isHidden();
        }
        return false;
    }

    public static boolean 文件是否存在(String name) {
        return new File(name).exists();
    }

    public static String 取文件编码(String filename) {
        String charset = "";
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(filename)));
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);
            in.reset();
            if (first3bytes[0] == (byte) -17 && first3bytes[1] == (byte) -69 && first3bytes[2] == (byte) -65) {
                return "utf-8";
            }
            if (first3bytes[0] == (byte) -1 && first3bytes[1] == (byte) -2) {
                return "unicode";
            }
            if (first3bytes[0] == (byte) -2 && first3bytes[1] == (byte) -1) {
                return "utf-16be";
            }
            if (first3bytes[0] == (byte) -1 && first3bytes[1] == (byte) -1) {
                return "utf-16le";
            }
            return "GBK";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return charset;
        } catch (IOException e2) {
            e2.printStackTrace();
            return charset;
        }
    }

    public static String 读入文本文件(String filename, String charset) {
        Exception e;
        String res = "";
        if (!new File(filename).exists()) {
            return res;
        }
        try {
            FileInputStream fin = new FileInputStream(filename);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            String res2 = new String(buffer, 0, length, charset);
            try {
                fin.close();
                return res2;
            } catch (Exception e2) {
                e = e2;
                res = res2;
                e.printStackTrace();
                return res;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return res;
        }
    }

    public static boolean 写出文本文件(String filename, String message, String charset) {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            fout.write(message.getBytes(charset));
            fout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] 读入字节文件(String filename) {
        byte[] buffer = null;
        if (!new File(filename).exists()) {
            return null;
        }
        try {
            FileInputStream fin = new FileInputStream(filename);
            buffer = new byte[fin.available()];
            fin.read(buffer);
            fin.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return buffer;
        }
    }

    public static boolean 写出字节文件(String filename, byte[] bytes) {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            fout.write(bytes);
            fout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long 取文件尺寸(String filePath) {
        File file = new File(filePath);
        try {
            if (file.isDirectory()) {
                return getFileSizes(file);
            }
            return getFileSize(file);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static long getFileSize(File file) throws Exception {
        if (file.exists()) {
            return (long) new FileInputStream(file).available();
        }
        file.createNewFile();
        return 0;
    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File[] flist = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size += getFileSizes(flist[i]);
            } else {
                size += getFileSize(flist[i]);
            }
        }
        return size;
    }

    private static boolean writeStreamToFile(InputStream stream, File file) {
        Exception e1;
        Throwable th;
        OutputStream output = null;
        try {
            OutputStream output2 = new FileOutputStream(file);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int read = stream.read(buffer);
                    if (read == -1) {
                        output2.flush();
                        try {
                            output2.close();
                            stream.close();
                            output = output2;
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            output = output2;
                            return false;
                        }
                    }
                    output2.write(buffer, 0, read);
                }
            } catch (Exception e2) {
                e1 = e2;
                output = output2;
            } catch (Throwable th2) {
                th = th2;
                output = output2;
            }
        } catch (Exception e3) {
            e1 = e3;
            try {
                e1.printStackTrace();
                try {
                    output.close();
                    stream.close();
                    return true;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return false;
                }
            } catch (Throwable th3) {
                th = th3;
                try {
                    output.close();
                    stream.close();
                    //throw th;
					
                } catch (IOException e42) {
                    e42.printStackTrace();
                    return false;
                }
            }
        }
		return false;
    }

    public static String 寻找文件关键词(String Path, String keyword) {
        String result = "";
        for (File f : new File(Path).listFiles()) {
            if (f.getName().indexOf(keyword) >= 0) {
                result = f.getPath() + "\n" + result;
            }
        }
        return result;
    }

    public static String 寻找文件后缀名(String Path, String Extension) {
        String result = "";
        for (File f : new File(Path).listFiles()) {
            if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension) && !f.isDirectory()) {
                result = f.getPath() + "\n" + result;
            }
        }
        return result;
    }

    public static boolean 复制文件(String sourcePath, String targetPath) {
        if (!new File(sourcePath).exists()) {
            return false;
        }
        int bytesum = 0;
        try {
            InputStream inStream = new FileInputStream(sourcePath);
            FileOutputStream fs = new FileOutputStream(targetPath);
            byte[] buffer = new byte[1444];
            while (true) {
                int byteread = inStream.read(buffer);
                if (byteread == -1) {
                    inStream.close();
                    return true;
                }
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean 创建文件(String path) {
        boolean result = false;
        File f = new File(path);
        if (f.exists()) {
            return true;
        }
        try {
            return f.createNewFile();
        } catch (IOException e) {
            return result;
        }
    }

    public static boolean 打开文本文件_读(String filename, String charset) {
        if (!new File(filename).exists()) {
            return false;
        }
        try {
            fin = new FileInputStream(filename);
            isr = new InputStreamReader(fin, charset);
            br = new BufferedReader(isr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean 关闭读() {
        try {
            br.close();
            fin.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String 读一行() {
        try {
            String readLine = br.readLine();
            line = readLine;
            if (readLine != null) {
                return line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean 打开文本文件_写(String filename, String charset) {
        if (!new File(filename).exists()) {
            return false;
        }
        try {
            fout = new FileOutputStream(filename);
            osw = new OutputStreamWriter(fout, charset);
            bw = new BufferedWriter(osw);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean 关闭写() {
        try {
            bw.close();
            fout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean 写一行(String message) {
        try {
            bw.newLine();
            bw.write(message);
            bw.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String 取子目录(String dir) {
        String pa = "";
        File[] ff = new File(dir).listFiles();
        for (int i = 0; i < ff.length; i++) {
            if (ff[i].isDirectory()) {
                pa = new StringBuilder(String.valueOf(pa)).append(ff[i].getAbsolutePath()).append("|").toString();
            }
        }
        return pa;
    }

    public static String 取文件修改时间(String path) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new File(path).lastModified()));
    }

    private static Intent openFile(String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(filePath);
        }
        if (end.equals("3gp") || end.equals("mp4")) {
            return getAudioFileIntent(filePath);
        }
        if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath);
        }
        if (end.equals("apk")) {
            return getApkFileIntent(filePath);
        }
        if (end.equals("ppt")) {
            return getPptFileIntent(filePath);
        }
        if (end.equals("xls")) {
            return getExcelFileIntent(filePath);
        }
        if (end.equals("doc")) {
            return getWordFileIntent(filePath);
        }
        if (end.equals("pdf")) {
            return getPdfFileIntent(filePath);
        }
        if (end.equals("chm")) {
            return getChmFileIntent(filePath);
        }
        if (end.equals("txt")) {
            return getTextFileIntent(filePath, false);
        }
        return getAllIntent(filePath);
    }

    private static Intent getAllIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(0x10000000);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(param)), "*/*");
        return intent;
    }

    private static Intent getApkFileIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(0x10000000);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(param)), "application/vnd.android.package-archive");
        return intent;
    }

    private static Intent getVideoFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(0x04000000);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(Uri.fromFile(new File(param)), "video/*");
        return intent;
    }

    private static Intent getAudioFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(0x04000000);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(Uri.fromFile(new File(param)), "audio/*");
        return intent;
    }

    private static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    private static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        intent.setDataAndType(Uri.fromFile(new File(param)), "image/*");
        return intent;
    }

    private static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        intent.setDataAndType(Uri.fromFile(new File(param)), "application/vnd.ms-powerpoint");
        return intent;
    }

    private static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        intent.setDataAndType(Uri.fromFile(new File(param)), "application/vnd.ms-excel");
        return intent;
    }

    private static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        intent.setDataAndType(Uri.fromFile(new File(param)), "application/msword");
        return intent;
    }

    private static Intent getChmFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        intent.setDataAndType(Uri.fromFile(new File(param)), "application/x-chm");
        return intent;
    }

    private static Intent getTextFileIntent(String param, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        if (paramBoolean) {
            intent.setDataAndType(Uri.parse(param), "text/plain");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(param)), "text/plain");
        }
        return intent;
    }

    private static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        intent.setDataAndType(Uri.fromFile(new File(param)), "application/pdf");
        return intent;
    }
}
