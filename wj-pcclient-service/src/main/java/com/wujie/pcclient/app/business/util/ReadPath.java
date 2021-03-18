package com.wujie.pcclient.app.business.util;

import java.io.File;

public class ReadPath {
    public static void main(String[] args) {
        getFiles("E:\\BaiduNetdiskDownload\\wj\\out\\artifacts\\wj_app_service_jar");
    }

    /**
     * 读取文件夹下的所有文件名
     * @param filepath
     */
    public static void getFiles(String filepath){
        //创建File对象
        File file = new File(filepath);

        String[] fileNameLists = file.list();
        File[] filePathLists = file.listFiles();

        //list 是获取该目录下的所有文件名
        for (int i = 0; i < filePathLists.length; i ++) {
            String s = "";
            s += "<archive location=\"";
            s += filePathLists[i];
            s += "\" failOnError=\"false\" />";
            System.out.println(s);
        }

        //listFiles 是获取该目录下所有文件的绝对路径
//        for(int i = 0; i < fileNameLists.length; i ++){
//            System.out.println(fileNameLists[i]);
//        }
        //获取文件夹中文件的数量
        int filesNum = file.list().length;
        System.out.println("该文件夹中的文件数="+filesNum);
    }
}
