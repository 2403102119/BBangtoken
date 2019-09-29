package bbangtoken.tckkj.com.bbangtoken.Util;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestOkHttp {
    private HashMap<String, String> params;
    private static Context context;


    public static void main(String[] args) {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            filePath = Environment.getExternalStorageDirectory().getPath() + "/awangzhi/tmp.txt";
//            zipFilePath = Environment.getExternalStorageDirectory().getPath() + "/awangzhi/tmp.zip";
//        } else {
//            filePath = Environment.getRootDirectory() + "/awangzhi/tmp.txt";
//            zipFilePath = Environment.getRootDirectory() + "/awangzhi/tmp.zip";
//        }
    }

    public TestOkHttp(Context context) {
        this.context = context;
        String path = context.getFilesDir().getAbsolutePath();
        filePath = path + "/awangzhi/tmp.txt";
        zipFilePath = path + "/awangzhi/tmp.zip";
    }

    static String filePath;
    static String zipFilePath;

    /**
     * 第一步 准备数据
     * 你们的大Json
     */
    public static String prepareData(String data, String url) {
        //TODO 获取数据
        return saveToFile(data, url);
    }

    /**
     * 第二步 保存文件
     *
     * @param data
     */
    private static String saveToFile(String data, String url) {
        File file = new File(filePath);
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            out = new FileOutputStream(file, false);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
            writer.flush();
            return compressFile(url);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 第三步 压缩文件
     * @param url
     */
    private static String compressFile(String url) {
        ZipOutputStream zipOutputSteam = null;
        FileInputStream inputStream = null;
        try {
            File file = new File(filePath);
            File zipFile = new File(zipFilePath);
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
            zipOutputSteam = new ZipOutputStream(new FileOutputStream(zipFile));
            ZipEntry zipEntry = new ZipEntry(file.getName());
            inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
            return zipFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipOutputSteam != null) {
                    zipOutputSteam.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
