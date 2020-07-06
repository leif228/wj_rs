package com.wujie.tc.netty.utils;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class FileUtils {
    /**
     * @param path 如 E:/config.properties
     */
    public static Properties readFile(String path) {
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));

            properties.load(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void writeToProperties(String path, boolean append, String key, String value) {
        //1.先实例化一个Properties对象
        Properties properties = new Properties();
        try {
            //2.创建一个输出流对象,选择正确的目标文件路径(注意:该配置文件放在src目录下)
            FileOutputStream fos = new FileOutputStream(path, append);//这里true表示追加,如果不设为true的话,会将原文件清空后,重新添加,切记!!!
            OutputStreamWriter opw = new OutputStreamWriter(fos, "utf-8");//引入Writer,可以明确该输出流的字符集,确保写入配置文件的中文编码正确

            //3.将需要写入的属性内容通过set方法,存入properties对象中
            properties.setProperty(key, value);

            //4.调用Properties对象的store(OutputStream out,String comments)方法保存，comments参数是注释；
            properties.store(opw, "测试用数据");

            //5.关闭资源
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean updatePropertiess(String path, Map<String, String> keyValueMap) {
        //1.先实例化一个Properties对象
        Properties properties = new Properties();
//        String path = PropertiesAction.class.getClassLoader().getResource("test2.properties").getPath();
//        System.out.println(path);//输出数据为:/E:/Idea_workspace/JFinal/TestSomething/out/production/TestSomething/test2.properties
        //注意:这里获取到的路径,为文件编译之后的路径,即class路径,如果用path来获取File文件的话,则修改的为classes中的文件,编译前的原文件是没有改变的,所以上服务器之后,应该使用path,才可以改变需要修改的文件

        //2.新建一个输入流和输出流,用来读取和写入文件
//        InputStreamReader reader = null;
        OutputStreamWriter writer = null;
        try {
            //3.读取到配置文件,并加载在Properties中
            //reader = new InputStreamReader(new FileInputStream(path),"utf-8"); 读取的为classes中的配置文件
//            reader = new InputStreamReader(new FileInputStream(path), "utf-8");
//            properties.load(reader);
//            System.out.println("testWrite:" + properties.get("testWrite"));

            //4.将需要修改的键值对,或者新增的键值对,写入到properties中(此处与直接追加操作相同)
            writer = new OutputStreamWriter(new FileOutputStream(path), "utf-8");
            for (String key : keyValueMap.keySet()) {
                properties.setProperty(key, keyValueMap.get(key));
            }
            //5.调用properties中的存储方法
            properties.store(writer, "测试数据");

            //6.关闭资源
//            reader.close();
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
//                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
