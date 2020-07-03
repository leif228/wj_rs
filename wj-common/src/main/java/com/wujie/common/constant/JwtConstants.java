/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: JwtConstants
 * Author:   Guoqiang
 * Date:     2018/11/22 下午5:10
 * Description: Jwt常量定义
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.constant;

/**
 * Jwt常量定义
 *
 * @author Guoqiang
 * @since 2018/11/22
 * @version 1.0.0
 */
public class JwtConstants {
    public final static String JWT_TOKEN_NAME = "Authorization";
    public final static int JWT_TOKEN_TIMEOUT = 30;
    public final static String JWT_SECRET = "leaf";
    public final static String TOKEN_DECODE_ERROR = "登录信息解密异常";
    public final static String TOKEN_EXPIRE_ERROR = "登录信息过期,请重新登录";
    public final static String TOKEN_INVALID_ERROR = "登录信息错误";
    public final static String LOGIN_HASH = "loginHashStr";
}
