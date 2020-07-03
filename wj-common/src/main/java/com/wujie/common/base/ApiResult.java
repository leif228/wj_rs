package com.wujie.common.base;

import com.wujie.common.enums.ErrorEnum;

import java.util.HashMap;

/**
 * 接口返回结果封装
 * 
 * @author wujie
 */
public class ApiResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    public static final String RETURNCODE = "code";
    public static final String MESSAGE = "msg";
    public static final String CONTENT = "data";
    public static final String ENCODE = "encode";//接口是否加密

    public static final String SUCCESS = "0";
    public static final String FAILURE = "Failure";

    /**
     * 初始化一个新创建的 Message 对象
     */
    public ApiResult()
    {
    }

    /**
     * 返回错误消息
     * 
     * @return 错误消息
     */
    public static ApiResult error()
    {
        return error(FAILURE, "操作失败");
    }

    /**
     * 返回自定义错误消息
     * @param msg 自定义错误消息
     * @return 错误消息
     */
    public static ApiResult error(String msg)
    {
        return error(FAILURE,msg);
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static ApiResult error(String code,String msg)
    {
        ApiResult json = new ApiResult();
        json.put(RETURNCODE, code);
        json.put(MESSAGE, msg);
        json.put(CONTENT, "");
        json.put(ENCODE,"false");
        return json;
    }

    /**
     * 返回成功消息
     * 
     * @param msg 内容
     * @return 成功消息
     */
    public static ApiResult success(String msg)
    {
        ApiResult json = new ApiResult();
        json.put(RETURNCODE, SUCCESS);
        json.put(MESSAGE, msg);
        json.put(ENCODE,"false");
        return json;
    }

    /**
     * 返回成功消息
     * 
     * @param data 数据内容
     * @return 成功消息
     */
    public static ApiResult success(Object data)
    {
        return success("操作成功", data);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 内容
     * @param data 数据内容
     * @return 成功消息
     */
    public static ApiResult success(String msg, Object data)
    {
        ApiResult json = new ApiResult();
        json.put(RETURNCODE, SUCCESS);
        json.put(MESSAGE, msg);
        json.put(CONTENT, data);
        json.put(ENCODE,"false");
        return json;
    }

    /**
     * 返回加密后的成功结果
     * @param msg 返回消息
     * @param data 返回结果
     * @param encode 结果是否加密
     * @return
     */
    public static ApiResult success(String msg, Object data, String encode)
    {
        ApiResult json = new ApiResult();
        json.put(RETURNCODE, SUCCESS);
        json.put(MESSAGE, msg);
        json.put(CONTENT, data);
        json.put(ENCODE,encode);
        return json;
    }
    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static ApiResult success()
    {
        return ApiResult.success("操作成功");
    }

    public static ApiResult error(ErrorEnum errServiceNot) {
        ApiResult json = new ApiResult();
        json.put(RETURNCODE, FAILURE);
        json.put(MESSAGE, errServiceNot.getErrMsg());
        json.put(CONTENT, "");
        json.put(ENCODE,"");
        return json;
    }

    /**
     * 返回成功消息
     * 
     * @param key 键值
     * @param value 内容
     * @return 成功消息
     */
    @Override
    public ApiResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

}
