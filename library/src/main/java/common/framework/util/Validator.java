package common.framework.util;

/**
 * 校验器：利用正则表达式校验邮箱、手机号等
 * @author Rays 2015年12月10日
 *
 */
public class Validator {
	/**
     * 正则表达式：验证用户名（以字母开头，长度在6~18之间，只能包含字母、数字和下划线）
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
 
    /**
     * 正则表达式：验证密码（长度在6~16之间，只能包含字母、数字）
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
 
    /**
     * 正则表达式：验证国内手机号 （长度为11，可以有+86或86前缀）
     */
    public static final String REGEX_MOBILE = "^(\\+86|86)?1[34578]\\d{9}$";

    /**
     * 正则表达式：验证国内电话号码
     */
    public static final String REGEX_PHONE = "^(\\d{3}-\\d{8})|(\\d{4}-\\d{7})|(\\d{11})$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "[\u4e00-\u9fa5]+";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{17}[\\dxX]$)|(^\\d{15}$)";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "^http://([\\w-]+\\.)+[\\w-]+(/[-\\w./?%&=]*)?$";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";

    /**
     * 正则表达式：验证验证码
     */
    public static final String REGEX_CAPTCHA = "^\\d{6}$";

    /**
     * 正则表达式：验证是否为数字
     */
    public static final String REGEX_NUMBER = "\\d+";
 
    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return username != null && username.matches(REGEX_USERNAME);
    }
 
    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return password != null && password.matches(REGEX_PASSWORD);
    }
 
    /**
     * 校验国内手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return mobile != null && mobile.matches(REGEX_MOBILE);
    }

    /**
     * 校验国内电话号码
     *
     * @param phone
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPhone(String phone) {
        return phone != null && phone.matches(REGEX_PHONE);
    }

    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return email != null && email.matches(REGEX_EMAIL);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return chinese != null && chinese.matches(REGEX_CHINESE);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return idCard != null && idCard.matches(REGEX_ID_CARD);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return url != null && url.matches(REGEX_URL);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return ipAddr != null && ipAddr.matches(REGEX_IP_ADDR);
    }
    
    /**
     * 校验验证码
     * 
     * @param captcha
     * @return
     */
    public static boolean isCaptcha(String captcha) {
    	return captcha != null && captcha.matches(REGEX_CAPTCHA);
    }
    
    /**
     * 验证是否为数字
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
    	return number != null && number.matches(REGEX_NUMBER);
    }
}
