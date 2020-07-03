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


import java.io.Serializable;

/**
 * 短信发送定义
 *
 * @author Guoqiang
 * @since 2018/12/3
 * @version 1.0.0
 */
public class SmsReqMsg implements Serializable {
    private String mobile;
    private String content;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
