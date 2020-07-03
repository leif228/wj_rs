package com.wujie.common.utils;

import java.util.regex.Pattern;

/**
 * java 正则表达式校验工具类
 * @author Administrator
 *
 */
public class RegularUtil {

	//邮箱地址正则表达式
    private static Pattern patternEmail      = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

 	//固定电话正则表达式
    private static Pattern patternPhone      = Pattern.compile("^((\\+086\\-0[0-9]{2,3}\\-)|(0[0-9]{2,3}\\-))?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");

    //手机号码正则表达式
    private static Pattern patternMobile     = Pattern.compile("^(13[0-9]|15[012356789]|16[6]|19[89]]|17[01345678]|18[0-9]|14[579])[0-9]{8}$");
    
    //身份证号码正则表达式
    private static Pattern patternIdentityCard = Pattern.compile("^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$");

    //账号正则表达式(字母数字开头，允许6-12字节，允许字母数字下划线)
    private static Pattern patternAccount    = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9_]{5,11}$");

    //用户名正则表达式(字母数字，允许中文字符)
    private static Pattern patternName       = Pattern.compile("[a-zA-Z0-9\u4E00-\u9FA5][a-zA-Z0-9\u4E00-\u9FA5_]*");

    //密码正则表达式
    private static Pattern patternPassword   = Pattern.compile("^[0-9a-fA-F]{32}$");

    //6位数字密码正则表达式
    private static Pattern patternSixNumPassword   = Pattern.compile("\\d{6}");

    /**
     * 6位数字密码匹配
     * @param pwd
     * @return
     */
    public static boolean isSixNumPassword(String pwd) {
        if (StringUtils.isBlank(pwd)) {
            return false;
        }

        return patternSixNumPassword.matcher(pwd).matches();
    }
    /**
     * 邮箱匹配
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        return patternEmail.matcher(email).matches();
    }

    /**
     * 固定电话匹配
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }

        return patternPhone.matcher(phone).matches();
    }

    /**
     * 手机号码匹配
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }

        return patternMobile.matcher(mobile).matches();
    }

    /**
     * 身份证号码匹配
     * @param identityCard 身份证号
     * @return
     */
    public static boolean isIdentityCard(String identityCard) {
        if (StringUtils.isBlank(identityCard)) {
            return false;
        }

        return patternIdentityCard.matcher(identityCard).matches();
    }

    /**
     * 用户账号匹配
     * @param userAccount
     * @return
     */
    public static boolean isAccount(String userAccount) {
        if (StringUtils.isBlank(userAccount)) {
            return false;
        }

        return patternAccount.matcher(userAccount).matches();
    }

    /**
     * 用户名匹配
     * @param userName
     * @return
     */
    public static boolean isUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }

        return patternName.matcher(userName).matches();
    }

    /**
     * 密码匹配
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        return patternPassword.matcher(password).matches();
    }


    public static void main(String[] args) {    	
        System.out.println(isMobile("15478189350"));
        
        System.out.println(isEmail("89@qq.com"));
        System.out.println(isPhone("15478925863"));
        System.out.println(isIdentityCard("43042319770921385X"));

    }
}
