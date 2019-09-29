package bbangtoken.tckkj.com.bbangtoken.Util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * kylin on 2018/1/19.
 */

public class ReadContactsUtil {

    public static final int PERMISSON_READ_CONTACT = 0x901;
    public static final int REQUEST_CONTACT = 0x902;

    public Activity activity;

    public ReadContactsUtil(Activity activity){
        this.activity = activity;
    }

    /*发起读取通讯录*/
    public void doReadContact(){
        if (PermissonUtil.checkPermissionAllGranted(activity,new String[]{
                Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS
        })){
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            activity.startActivityForResult(intent,REQUEST_CONTACT);
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS}, PERMISSON_READ_CONTACT);
        }
    }

    /*获取拾取的通讯录的姓名 和 联系方式*/
    public String[] parseContact(Uri uri){
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = activity.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            try{
                contact[0] = cursor.getString(nameFieldColumnIndex);
                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                if (phone != null) {
                    phone.moveToFirst();
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phone.close();
                cursor.close();
            }catch (CursorIndexOutOfBoundsException e){
                LoggerUtil.e("ReadContactsUtil","CursorIndexOutOfBoundsException");
                openAppDetails();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                LoggerUtil.e("ReadContactsUtil","finally");
                cursor.close();
            }
        } else {
            return null;
        }
        return contact;
    }

    /*提示 打开设置去授权*/
    private void openAppDetails(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("紧急联系人授权需要开启“读取联系人权限”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                activity.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}
