package com.wujie.common.config;

import com.wujie.common.utils.SpringContextUtil;
import com.wujie.common.utils.StringUtils;
import com.wujie.common.utils.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 * 
 * @author wujie
 */
public class Global
{
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    private static String NAME = "application.yml";

    /**
     * 当前对象实例
     */
    private static Global global = null;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();
    
    /**
     * 当前使用的配置文件active
     */
    private static String active = "dev";
    
    static {
    	try {
        	active = SpringContextUtil.getActiveProfile();
		} catch (Exception e) {
            log.error("获取全局配置异常");
		}
    }

    private Global()
    {
    }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */

    public static synchronized Global getInstance()
    {
        if (global == null)
        {
            synchronized (Global.class)
            {
                if (global == null)
                {
                    global = new Global();
                }
            }
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key)
    {
    	return getConfig(key, NAME);
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key, String fileName)
    {
        String value = map.get(key);
        if (value == null)
        {
            Map<?, ?> yamlMap = null;
            try
            {
                yamlMap = YamlUtil.loadYaml(fileName);
                value = String.valueOf(YamlUtil.getProperty(yamlMap, key));
                map.put(key, value != null ? value : StringUtils.EMPTY);
            }
            catch (FileNotFoundException e)
            {
                log.error("获取{}全局配置异常 {}", fileName, key);
            }
        }
        return value;
    }

    /**
     * 获取项目名称
     */
    public static String getName()
    {
        return StringUtils.nvl(getConfig("wujie.name"), "wujie");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion()
    {
        return StringUtils.nvl(getConfig("wujie.version"), "3.0.0");
    }

    /**
     * 获取版权年份
     */
    public static String getCopyrightYear()
    {
        return StringUtils.nvl(getConfig("wujie.copyrightYear"), "2018");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled()
    {
        return Boolean.valueOf(getConfig("wujie.addressEnabled", "application-" + active + ".yml"));
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile()
    {
    	return getConfig("wujie.profile", "application-" + active + ".yml");
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "avatar/";
    }

    /**
     * 获取文件上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "upload/";
    }

    /**
     * 获取下载上传路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "download/";
    }

    /**
     * 获取作者
     */
    public static String getAuthor()
    {
        return StringUtils.nvl(getConfig("gen.author"), "wujie");
    }

    /**
     * 生成包路径
     */
    public static String getPackageName()
    {
        return StringUtils.nvl(getConfig("gen.packageName"), "com.wujie.project.module");
    }

    /**
     * 是否自动去除表前缀
     */
    public static String getAutoRemovePre()
    {
        return StringUtils.nvl(getConfig("gen.autoRemovePre"), "true");
    }

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String getTablePrefix()
    {
        return StringUtils.nvl(getConfig("gen.tablePrefix"), "sys_");
    }
}
