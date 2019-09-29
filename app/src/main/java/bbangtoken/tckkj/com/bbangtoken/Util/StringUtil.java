package bbangtoken.tckkj.com.bbangtoken.Util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bbangtoken.tckkj.com.bbangtoken.Activity.PopularizeActivity;
import bbangtoken.tckkj.com.bbangtoken.App;

import static android.content.Context.CLIPBOARD_SERVICE;

public class StringUtil {
    /**
     * 验证银卡卡号
     *
     * @param cardNo
     * @return
     */
    public static boolean isBankCard(String cardNo) {
        Pattern p = Pattern.compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$");
        Matcher m = p.matcher(cardNo);

        return m.matches();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isSpace(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 根据身份证号输出年龄
     */
    public static int IdNOToAge(String IdNO) {
        int leh = IdNO.length();
        String dates = "";
        if (leh == 18) {
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return u;
        } else {
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }
    }

    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber 电话
     * @param message     内容
     */
    public static void doSendSMSTo(Activity activity, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            activity.startActivity(intent);
        }
    }

    /*调起系统拨打电话功能*/
    public static void doCallTo(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    /**
     * 返回不为空的字符串, 去首尾空格
     *
     * @param str
     * @return
     */
    public static String noSpace(String str) {
        if (isSpace(str))
            return "";
        else
            return str.trim();
    }

    /**
     * 转化 String类型,
     * 包括 “"” “\r\n” “\”
     *
     * @param value
     * @return
     */
    public static String conver2JS(String value) {
        if (null == value) {
            return null;
        } else {
            return value.toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
        }
    }

    /**
     * 转换成MD5
     */
    public static String toMD5(String str) {
        MessageDigest md;
        try {
            // 生成一个MD5加密计算摘要
            md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private final static int[] li_SecPosValue = {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
    private final static String[] lc_FirstLetter = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};

    public static String toFirstLetter(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        for (char c : str.toCharArray()) {
            String chinese = new String(new String(new char[]{c}).getBytes("GBK"), "iso8859-1");
            if (chinese.length() > 1) {
                int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
                int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
                li_SectorCode = li_SectorCode - 160;
                li_PositionCode = li_PositionCode - 160;
                int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
                if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                    for (int i = 0; i < 23; i++) {
                        if (li_SecPosCode >= li_SecPosValue[i] && li_SecPosCode < li_SecPosValue[i + 1]) {
                            chinese = lc_FirstLetter[i];
                            break;
                        }
                    }
                } else {  // 非汉字字符,如图形符号或ASCII码
                    chinese = new String(chinese.getBytes("iso8859-1"), "GBK");
                    chinese = chinese.substring(0, 1);
                }
            }
            sb.append(chinese);
        }
        return sb.toString();
    }

    /**
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /*public static void main(String[] a) throws UnsupportedEncodingException{
        String s="是否23的能d就看d上";
		System.out.println(s);
		System.out.println(toFirstLetter(s));
	}*/


    /*String 解析出错
    * toValue   想要给谁赋值
    * getValue  解析中欲得到的值*/
    public static String getRightString(String toValue, String getValue) {
        toValue = "";
        try {
            toValue = getValue;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isSpace(toValue)) {
            toValue = "--";
        }

        return toValue;
    }

    /*把银行卡每隔4位用空格隔开*/
    public static String transCardNumber(String string, String pd) {
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            result = result + string.charAt(i);
            if (i % 4 == 3) {
                result = result + pd;
            }
        }
        return result;
    }

    /*改变银行卡的显示  ****1234   */
    public static String transCardNumberToPwd(String cardNumber) {
        if (cardNumber == null || cardNumber.equals("")) {

        } else {
            return "****" + cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
        }
        return "";
    }

    /*用于现实金额，中间加入逗号 支持小数*/
    public static String transMoney(String money) {
        Log.e("transMoney", money + "^");
        try {
            String result = "";
            int k = 0;
            String[] a = new String[2];
            if (money.contains(".")) {
                a[0] = money.substring(0, money.indexOf("."));
                a[1] = money.substring(money.indexOf("."), money.length());
            } else {
                a[0] = money;
                a[1] = "";
            }
            Log.e("a[0]", a.length + "");
            for (int i = a[0].length() - 1; i > -1; i--) {
                k++;
                if (k > 3 && (i > 0)) {
                    result = money.charAt(i) + "," + result;
                    k = 1;
                } else {
                    result = money.charAt(i) + result;
                }
            }
            Log.e("result", result + a[1]);
            return result + a[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return money;
    }

    public static String transMoney(Double d) {
        if (d == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(true);
        return (nf.format(d));
    }

    public static String transNumber(int i) {
        try{
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(true);
            return (nf.format(i));
        }catch (Exception e){
            return i+"";
        }

    }

    /*解析json串 取出message*/
    public static void showMessage(Context context, String jsonStr, String replace){
        try{
            JSONObject jsonObject = new JSONObject(jsonStr);
            String message = jsonObject.optString("message");
            if (isSpace(message)){
                MUIToast.show(context.getApplicationContext(),replace);
            }else{
                MUIToast.show(context.getApplicationContext(),message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String subDesinedString(String orignalString,int start,int end,String mask,int masklength){
        try{
            if (isSpace(orignalString)){
                return "";
            }else{
                String maskStr = "";
                for (int i = 0; i<masklength; i++){
                    maskStr = maskStr+mask;
                }
                return  orignalString.substring(0,start)+maskStr
                        +orignalString.substring(orignalString.length()-end,orignalString.length());
            }
        }catch (Exception e){
            e.printStackTrace();
            return orignalString;
        }
    }


    public static void setClipBoard(Context context,String content) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", content);
            clipboard.setPrimaryClip(clip);
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(content);
        }
    }

    /** * 纯数字
     * @param str
     * @return */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c))
                return true;// 有一个中文字符就返回
        }
        return false;
    }

    public static String encodeBase64File(String path) throws Exception {
        File  file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer,Base64.DEFAULT);
    }


    public static void decoderBase64File(String base64Code,String savePath) throws Exception {
//byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer =Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }


    /*
    * 键值对转换成json字符串
    * */
    public static String map2Json(Map<String, Object> map){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String keepTwoDecimalPlaces(double number) {
        String result = new DecimalFormat("0.00").format(number);
        Log.w("tag", result);
        return result;
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static  String keepTwoDecimalPlaces(long number) {
        String result = new DecimalFormat("0.00").format(number);
        Log.w("tag", result);
        return result;
    }




}
