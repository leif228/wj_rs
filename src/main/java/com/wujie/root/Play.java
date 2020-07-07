package com.wujie.root;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class Play {
    public static void main(String[] args) {
        try {
            String filepath = "E:\\BaiduNetdiskDownload\\wj\\src\\main\\resources\\lib\\wj-register-center.jar";
//            System.out.println(Play.class.getResource(""));
//            System.out.println(Play.class.getResource(filepath));
//            filepath = "java -jar " + filepath;
//            Runtime.getRuntime().exec(filepath);

            File temp = new File(filepath);

            String softPath = temp.getAbsolutePath();
//
//            softPath = "file:"+softPath;
//            System.out.println(softPath);
//            // 2. 使用URLClassLoader 加载jar文件
//            URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL(softPath)},Thread.currentThread().getContextClassLoader());
//            //3.指名jar启动对应class的main的路径
//            Class demo = classLoader.loadClass("com.wujie.rc.RegisterApplication");
//            // 4.获取demo的这个class的对应方法，与他对应的参数
//            Method method = demo.getMethod("main", String[].class);
//            //  5.通过反射调用这个方法，给与他对应的参数  注意，这里转object的原因
//            method.invoke(null, (Object) new String[]{});

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
