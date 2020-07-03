package com.wujie.common.base;

/**
 * 单项目内调用返回对象封装
 * 
 * @author wujie
 */
public class TransformResult<T>
{

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";
    public static final String SUCCESS_MSG = "请求成功";
    private T data;
    private String returnCode;
    private String message;

    public TransformResult(T data, String returnCode, String message) {
        this.data = data;
        this.returnCode = returnCode;
        this.message = message;
    }

    public TransformResult(String returnCode, String message) {
        this.returnCode = returnCode;
        this.message = message;
        this.data = null;
    }

    public TransformResult(T data) {
        this.data = data;
        this.message = SUCCESS_MSG;
        this.returnCode = SUCCESS;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
