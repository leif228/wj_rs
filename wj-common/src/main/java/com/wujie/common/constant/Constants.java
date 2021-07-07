package com.wujie.common.constant;

/**
 * 通用常量信息
 * 
 * @author wujie
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 自动去除表前缀
     */
    public static String AUTO_REOMVE_PRE = "true";

    /**
     * 当前记录起始索引
     */
    public static String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static String IS_ASC = "isAsc";
    /**
     * 请求协议
     */
    public static String HTTP_PRODOCOL = "http";
    /**
     * 文件服务器地址
     */
    public static String FS_HOST = "http://192.168.4.15";
    /**
     * 文件服务器地址端口
     */
    public static int FS_PORT = 22122;
    /**
     * demo文件服务器地址
     */
    public static String FS_DEV_HOST = "http://192.168.4.15";
    /**
     * 文件服务器地址
     */
    public static int FS_DEV_PORT = 9899;

    /**
     * 消息队列相关配置
     */
    //支付消息队列常量
    public static final String PAYMENT_EXCHANGE = "payment_exchange";
    //个人支付消息队列
    public static final String PAYMENT_QUEUE_PERSONAL = "payment_queue_personal";
    //对公支付消息队列
    public static final String PAYMENT_QUEUE_COMPANY = "payment_queue_company";
    //个人支付路由键
    public static final String PAYMENT_ROUTE_PERSONAL = "pay.personal";
    //对公支付路由键
    public static final String PAYMENT_ROUTE_COMPANY = "pay.company";

	//任务延时处理队列
	public static final String TASK_EXCHANGE = "task_exchange";
	//TTL QUEUE
    public static final String TASK_DL_QUEUE = "task_dead_letter_queue";
	//TTL 路由
	public static final String TASK_DL_ROUTE = "task.dead.letter";
    //DLX repeat QUEUE 死信转发队列
    public static final String TASK_RT_QUEUE = "task_repeat_trade_queue";
	//DLX repeat QUEUE 死信转发队列 路由
	public static final String TASK_RT_ROUTE = "task.repeat.trade";
	
}
