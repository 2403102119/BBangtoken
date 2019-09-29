package bbangtoken.tckkj.com.bbangtoken.Util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * kylin on 2017/12/14.
 */

public class PermissonUtil {

    /*检查权限是否授予*/
    public static final boolean checkPermissionAllGranted(Context context,String[] permissins){
        for (String permission:permissins){
            if (ContextCompat.checkSelfPermission(context,permission)!=
                    PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}
