package bbangtoken.tckkj.com.bbangtoken.Util;

import android.util.Log;

import bbangtoken.tckkj.com.bbangtoken.App;


/**
 * kylin on 2018/5/18.
 */

public class LoggerUtil {

    public static void e(String str){
        if (App.isDebug){
            Log.e("DEBUG",str);
        }
    }

    public static void e(String str1,String str2){
        if (App.isDebug){
            Log.e(str1+"",str2+"");
        }
    }

}
