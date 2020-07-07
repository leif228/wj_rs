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
            String filepath = "lib/wj-register-center_jar.jar";
            File temp = new File(filepath);
            System.out.println(temp.getAbsolutePath());
            //下面四种情况取编译后target\classes 目录下的文件
            // File 形式
            File file = new File(Play.class.getClassLoader().getResource(filepath).getFile());
            System.out.println(file.getAbsolutePath());
            // InputStream 形式
            InputStream inputStream = Play.class.getClassLoader().getResourceAsStream(filepath);
            System.out.println(inputStream);
            // URL 形式
            URL url = Play.class.getClassLoader().getResource(filepath);
            System.out.println(url);
            // URI 形式
            URI uri = Play.class.getClassLoader().getResource(filepath).toURI();
            File uriFile = new File(uri);
            System.out.println(uriFile.getAbsolutePath());

//            System.out.println(softPath);
//            // 2. 使用URLClassLoader 加载jar文件
//            URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL(softPath)});
//            //3.指名jar启动对应class的main的路径
//            Class demo = classLoader.loadClass("com.baobab.webcraw.GetRWZG");
//            // 4.获取demo的这个class的对应方法，与他对应的参数
//            Method method = demo.getMethod("main", String[].class);
//            //  5.通过反射调用这个方法，给与他对应的参数  注意，这里转object的原因
//            method.invoke(null, (Object) new String[]{});

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
