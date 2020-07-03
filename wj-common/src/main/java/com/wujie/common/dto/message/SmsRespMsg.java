/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: SmsReqMsg
 * Author:   Guoqiang
 * Date:     2018/12/3 上午10:45
 * Description: 短信发送定义
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.dto.message;

import com.wujie.common.dto.BaseData;

/**
 * 短信发送定义
 *
 * @author Guoqiang
 * @since 2018/12/3
 * @version 1.0.0
 */
public class SmsRespMsg extends BaseData {
    private String status;// 请求状态
    private String errorCode;// 错误代码

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
