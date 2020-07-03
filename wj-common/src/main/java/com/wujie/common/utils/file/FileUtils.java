package com.wujie.common.utils.file;

import org.springframework.util.ClassUtils;

import java.io.*;

/**
 * 文件处理工具类
 * 
 * @author wujie
 */
public class FileUtils
{
    /**
     * 输出指定文件的byte数组
     * 
     * @param filename 文件
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }

	/**
	 * Make file directory <b>PS : this method might create part directory even
	 * when return value is false</b>
	 * 
	 * @param file
	 * 
	 * @return true : create file directory with no error,false : during
	 *         creating file directory error occurred
	 * @since 0.1
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	public static boolean existDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.exists();
		}
		return false;
	}

	/**
	 * Make file directory <b>PS : this method might create part directory even
	 * when return value is false</b>
	 * 
	 * @param fileName
	 *            file directory name
	 * @return true : create file directory with no error,false : during
	 *         creating file directory error occurred
	 * @since 0.1
	 */
	public static boolean makeDirectory(String fileName) {
		File file = new File(fileName);
		return makeDirectory(file);
	}

	public static boolean existDirectory(String fileName) {
		File file = new File(fileName);
		return existDirectory(file);
	}

    /**
     * 读取resource目录下文件
     * @param enterpriseCerFilePath 等待读取文件
     * @return String 文件内容
     * @throws Exception
     */
    public static String getString(String enterpriseCerFilePath) throws Exception
    {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder buff = new StringBuilder();
        try {
            InputStream stream = ClassUtils.class.getClassLoader().getResourceAsStream(enterpriseCerFilePath);
            inputStreamReader = new InputStreamReader(stream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                buff.append(line);
            }
        }catch(Exception ioe){
            ioe.printStackTrace();
        }finally{
            if(null != bufferedReader) bufferedReader.close();
            if(null != inputStreamReader) inputStreamReader.close();
        }
        return buff.toString();
    }

    public static String readFromFile(String filePath) {
        StringBuffer content = new StringBuffer("");
        if (!new File(filePath).isFile() || !new File(filePath).exists()) {
            return "";
        }
        try {
            // BufferedReader bfReader = new BufferedReader(new FileReader(new
            // File(filePath)));
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String str = null;
            while ((str = bfReader.readLine()) != null)
                content.append(str).append("\n");

            bfReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return content.toString();
    }
}
