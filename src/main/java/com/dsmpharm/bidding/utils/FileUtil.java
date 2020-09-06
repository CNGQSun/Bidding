package com.dsmpharm.bidding.utils;

import com.dsmpharm.bidding.controller.BiddingProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件拷贝工具类
 * @author:Grant
 * @date:2020/09/01
 */
public class FileUtil {
    private static Logger log = LoggerFactory.getLogger(FileUtil.class);
    //文件夹拷贝    不管是路径还是File对象都可直接使用
    //拷贝文件夹方法
    // String 对象
    public static void copyDir(String srcPath,String destPath){
        File src=new File(srcPath);
        File dest=new File(destPath);
        copyDir(src,dest);
    }
    //拷贝文件夹方法
    //File 对象
    public static void copyDir(File src,File dest){
        if(src.isDirectory()){//文件夹
            dest=new File(dest,src.getName());
        }copyDirDetail(src,dest);
    }


    //拷贝文件夹细节
    public static void copyDirDetail(File src,File dest){
        if(src.isFile()){   //文件直接复制
            try {
                FileUtil.copyFile(src,dest);
            } catch (IOException e) {
                log.error(e.toString(), e);
            }
        }else if(src.isDirectory()){//目录
            //确保文件夹存在
            dest.mkdirs();
            //获取下一级目录|文件
            for (File sub:src.listFiles()){
                copyDirDetail(sub,new File(dest,sub.getName()));
            }
        }
    }

    //文件拷贝
    public static void copyFile(String srcPath, String destPath) throws IOException {
        File src=new File(srcPath);
        File dest=new File(destPath);
        copyFile(src,dest);
    }
    public static void copyFile(File srcPath, File destPath) throws IOException {

        //选择流
        InputStream is = new FileInputStream(srcPath);
        OutputStream os = new FileOutputStream(destPath);
        //拷贝  循环+读取+写出
        byte[] flush = new byte[1024];
        int len = 0;
        //读取
        while (-1 != (len = is.read(flush))) {
            //写出
            os.write(flush, 0, len);
        }
        os.flush();//强制刷出
        //关闭流  先打开后关闭
        os.close();
        is.close();
    }
    //测试
    //public static void main(String args[]) {
    //    //源目录
    //    String srcPath = "E:\\project\\file_add\\20200630 待确认细节需求(1).xlsx";
    //    //目标文件
    //    String destPath = "E:/project/750355202313424896/文件解读/20200630 待确认细节需求(1).xlsx";
    //    FileUtil.copyDir(srcPath,destPath);
    //}

}

