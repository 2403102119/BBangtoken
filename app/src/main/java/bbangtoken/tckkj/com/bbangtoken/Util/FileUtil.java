package bbangtoken.tckkj.com.bbangtoken.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import bbangtoken.tckkj.com.bbangtoken.Activity.PersonalActivity;

/**
 * kylin on 2017/9/25.
 */

public class FileUtil {

    /**
     * 获取文件大小  单位是M
     * @param file  文件
     * @return
     */
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            return 0.0;
        }
    }


    /**
     * 获取资源文件流
     *
     * @param clazz
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public static InputStream getResourceAsStream(Class clazz, String name) {
        try {
            InputStream inputStream = clazz.getResourceAsStream(name);
            if(inputStream == null)
                inputStream = clazz.getClassLoader().getResourceAsStream(name);
            return inputStream;
        } catch (Exception e) {
//            if(log.isWarnEnabled())
//                log.warn("获取资源文件失败", e);
            return null;
        }
    }
    public static String separatorReplace(String path){
        return path.replace("\\","/");
    }


    /**
     * 通过路径获得文件，
     * 若不存在则抛异常，
     * 若存在则返回该文件。
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String path) throws FileNotFoundException {
        path = separatorReplace(path);
        File file = new File(path);
        if(!file.isFile()){
            throw new FileNotFoundException("file not found!");
        }
        return file;
    }





}
