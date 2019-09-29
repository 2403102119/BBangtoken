package bbangtoken.tckkj.com.bbangtoken.Util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2019/7/15.
 */

public class DateUtilsl {



        private static SimpleDateFormat mSimpleDateFormat = null;

        //获取系统时间
        public static String getCurrentDate() {
            Date d = new Date();
            mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒", Locale.getDefault());
            return mSimpleDateFormat.format(d);
        }

        /*时间戳转换成字符窜*/
        public static String getDateToString(long time) {
            Date d = new Date(time);
            mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时", Locale.getDefault());
            return mSimpleDateFormat.format(d);
        }

        /*将字符串转为时间戳*/
        public static long getStringToDate(String time) {
            mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒", Locale.getDefault());
            Date date = new Date();
            try {
                date = mSimpleDateFormat.parse(time);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return date.getTime();
        }
    /*
* 将时间戳转换为时间
*/
    public static String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }


}
