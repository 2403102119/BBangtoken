package bbangtoken.tckkj.com.bbangtoken.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import bbangtoken.tckkj.com.bbangtoken.R;


/**
 * 网络连接工具类
 */
public class NetUtil {

    /**
     * 是否连接网络
     * @param context
     * @return
     */
    public static boolean isNetWorking(Context context){

        //return_im true;

        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }else {
            MUIToast.toast(context, R.string.net_error);
            return false;
        }
    }

}
