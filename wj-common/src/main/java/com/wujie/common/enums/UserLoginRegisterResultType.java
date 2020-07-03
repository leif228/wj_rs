package com.wujie.common.enums;

public enum  UserLoginRegisterResultType {
    FAILURE("0", "失败"),

    SUCCESS("1", "成功"),

    LOGINNAME_NULL("2", "登录名为空"),

    PASSWD_NULL("3", "密码为空"),

    LOGINNAME_ISUSED("4", "手机号作为登录名已被注册"),

    EMAIL_ISUSED("5", "邮箱已被注册"),

    CAPTCHA_ERROR("6", "验证码输入错误"),

    USER_NOTEXIST("7", "用户不存在"),

    PASSWD_ERROR("8", "登录名或密码错误"),

    USER_INACTIVATE("9", "用户未激活"),

    USER_LOGON("10", "用户已经在别处登录"),

    CHANGEPASSWD_ERROR("11", "原密码错误"),

    REGISTER_PINGAN_ERROR("12", "注册平安账户失败");

    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private UserLoginRegisterResultType(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
