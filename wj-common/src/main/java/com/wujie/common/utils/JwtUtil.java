/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: JwtUtil
 * Author:   Guoqiang
 * Date:     2018/11/22 下午5:06
 * Description: JWT算法工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wujie.common.constant.JwtConstants;
import com.wujie.common.dto.user.LoginUserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT(Json Web Token)是一种基于json实现的用户合法性校验的协议。基本思路就是用户提供用户名和密码给认证服务器，
 * 服务器验证用户提交信息信息的合法性；如果验证成功，会产生并返回一个Token（令牌），用户可以使用这个token访问服务器上受保护的资源。
 *
 * JWT算法工具类
 *
 * @author Guoqiang
 * @since 2018/11/22
 * @version 1.0.0
 */
public class JwtUtil {




    /**
     * 创建token
     *
     * @return
     * @throws Exception
     */
    public static String generateToken(LoginUserDto loginUserDto) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, JwtConstants.JWT_TOKEN_TIMEOUT);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>(10);
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = null;
        try {
            token = JWT.create()
                    .withHeader(map)
                    .withClaim("id", loginUserDto.getUid())
                    .withClaim("name", loginUserDto.getUserName())
                    .withClaim("role", loginUserDto.getRole())
                    .withClaim("client", loginUserDto.getClient())
                    .withClaim("logo", loginUserDto.getLogo())
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(JwtConstants.JWT_SECRET));
        } catch (UnsupportedEncodingException e) {
            //throw new MyException(JwtConstants.TOKEN_DECODE_ERROR);
        }
        return token;
    }

    /**
     * 验证token，并返回参数解析结果
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String,  String> verifyToken(String token) {
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(Algorithm.HMAC256(JwtConstants.JWT_SECRET))
                    .build();
        } catch (UnsupportedEncodingException e) {
            //throw new MyException(JwtConstants.TOKEN_DECODE_ERROR);
        }

        DecodedJWT jwt;
        Map<String, String> resultMap = new HashMap<>();
        try {
            jwt = verifier.verify(token);
            Map<String, Claim> map = jwt.getClaims();
            map.forEach((k,v) -> resultMap.put(k, v.asString()));
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            //throw new MyException(JwtConstants.TOKEN_EXPIRE_ERROR);
        } catch (Exception e1) {
            //throw new MyException(ErrorStatus.TOKEN_INVALID_ERROR);
        }
        return resultMap;
    }

    /**
     * 从request中解析token参数
     *
     * @param request
     * @return
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        // 先检测header是否存在
        String token = request.getHeader(JwtConstants.JWT_TOKEN_NAME);

        // 然后解析cookie中是否存在
        Cookie[] cookies = request.getCookies();
        boolean flag = (token == null || token.isEmpty()) && cookies != null;
        if (flag) {
            for (Cookie cookie : cookies) {
                if (JwtConstants.JWT_TOKEN_NAME.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 最后检测参数是否传递
        if (token == null || token.isEmpty()) {
            token = request.getParameter(JwtConstants.JWT_TOKEN_NAME);
        }

        return token;
    }

}