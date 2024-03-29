package bbangtoken.tckkj.com.bbangtoken.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.JPUSH.Logger;
import bbangtoken.tckkj.com.bbangtoken.R;
import cn.jpush.android.api.DefaultPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

				showNotifycation(context,bundle.getString(JPushInterface.EXTRA_MESSAGE));
				sendBroadcast(context);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

				DefaultPushNotificationBuilder builder = new DefaultPushNotificationBuilder();
				Map map = new HashMap();
				map.put("cn.jpush.android.ALERT", JPushInterface.EXTRA_ALERT);
				map.put("cn.jpush.android.NOTIFICATION_CONTENT_TITLE",context.getResources().getString(R.string.app_name));
				builder.buildNotification(map);
				JPushInterface.setDefaultPushNotificationBuilder(builder);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

				//TODO 打开自定义的Activity
//				Intent i = new Intent(context, MessageActivity.class);
//				i.putExtras(bundle);
//				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Logger.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Logger.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	private void showNotifycation(Context context,String string){
		NotificationManager mNotificationManager =
				(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle(context.getResources().getString(R.string.app_name))
				.setContentText(string)
				.setSmallIcon(R.mipmap.logo)
				.setContentIntent(getDefalutIntent(context, Notification.FLAG_AUTO_CANCEL))
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setAutoCancel(true);
		mNotificationManager.notify(0,mBuilder.build());
	}

	public PendingIntent getDefalutIntent(Context context, int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity
				(context, 1, new Intent(), flags);

		//TODO
//		PendingIntent pendingIntent= PendingIntent.getActivity
//				(context, 1, new Intent(context, MessageActivity.class), flags);
		return pendingIntent;
	}

	private void sendBroadcast(Context context){
		Intent intent = new Intent(context.getPackageName()+".LOCAL_BROADCAST");
		android.support.v4.content.LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}


}
