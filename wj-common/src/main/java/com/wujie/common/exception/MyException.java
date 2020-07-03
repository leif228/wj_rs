/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: MyException
 * Author:   Guoqiang
 * Date:     2018/11/21 下午5:35
 * Description: 自定义异常
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.exception;

import com.wujie.common.base.ApiResult;

/**
 * 自定义异常
 *
 * @author Guoqiang
 * @since 2018/11/21
 * @version 1.0.0
 */
public class MyException extends RuntimeException {
    private Exception exception;
    private ApiResult apiResponse;

    public MyException(ApiResult apiResponse, Exception exception){
        this.apiResponse = apiResponse;
        this.exception = exception;
    }



    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public ApiResult getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(ApiResult apiResponse) {
        this.apiResponse = apiResponse;
    }

}
