/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: LoginTokenDto
 * Author:   Guoqiang
 * Date:     2018/11/23 下午2:24
 * Description: 登录token
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.dto.user;


/**
 * 登录token
 *
 * @author Guoqiang
 * @since 2018/11/23
 * @version 1.0.0
 */
public class LoginTokenDto extends LoginUserDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

