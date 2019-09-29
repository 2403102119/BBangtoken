package bbangtoken.tckkj.com.bbangtoken.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话工具类
 */
public class PhoneUtil {

    /**
     * 判断是否是一个合法的手机号
     * @param number
     * @return
     */
    public static boolean isPhone(String number){
        String pattern = "^1(3[0-9]|4[5,7]|5[0-9]|6[0-9]|7[0,1,2,3,4,5,6,7,8,9]|8[0-9]|9[0-9])\\d{8}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(number);
        return  m.matches();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
	   /*
	    * 可接受的电话格式有：
	    */
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
	   /*
	    * 可接受的电话格式有：
	    */
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if(matcher.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;
    }
    /**
     * 将手机号码中间四位变成*号
     * @param phone
     * @return
     */
    public static String phoneToStar(String phone) {
        String ss = phone
                .substring(0, phone.length() - (phone.substring(3)).length())
                + "****" + phone.substring(7);
        return ss;
    }

}
