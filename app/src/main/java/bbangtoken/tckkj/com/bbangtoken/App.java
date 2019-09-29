package bbangtoken.tckkj.com.bbangtoken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;


import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import bbangtoken.tckkj.com.bbangtoken.Util.LocalManageUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.SPUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.SharedPreferencesUtil;

/**
 * kylin on 2018/5/4.
 */

public class App extends Application {
    public static App app;

    private static List<Activity> mActivitys = Collections.synchronizedList(new LinkedList<Activity>());
    public static String account="";

    public static String token;
    public static int user_id;
    public static String user_photo;

    public static boolean islogin;
    public static boolean isDebug = true;

    public static String strCall = "[]";
    public static String strApp = "[]";

    public static String ip = "";

    public static String uKey="";

    public static float balance;
    public static boolean isShowDailog = false;


    public static String phone ;
    public static String longitude = "";
    public static Application instance;
    public SocketListener socketListener;
    public static Application getInstance() {
        return instance;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        app = this;
        instance = this;
        token = SPUtil.getData(this, "token", "")+"";
        phone = SPUtil.getData(this, "phone", "")+"";
        islogin = (boolean) SPUtil.getData(this, "islogin", false);
        user_id = (int) SharedPreferencesUtil.getData(this,"user_id",0);
        account = SPUtil.getData(this, "account", "")+"";

        registerActivityListener();


        WebSocketSetting setting = new WebSocketSetting();
        //连接地址，必填，例如 wss://localhost:8080
        setting.setConnectUrl("ws://47.56.66.251:7578/ws");
//        setting.setConnectUrl("");

        //设置连接超时时间
        setting.setConnectTimeout(10 * 1000);

        //设置心跳间隔时间
        setting.setConnectionLostTimeout(5);
        //设置断开后的重连次数，可以设置的很大，不会有什么性能上的影响
        setting.setReconnectFrequency(40);
        //接收到数据后是否放入子线程处理，只有设置了 ResponseProcessDispatcher 才有意义
        setting.setProcessDataOnBackground(true);
        //网络状态发生变化后是否重连，
        //需要调用 WebSocketHandler.registerNetworkChangedReceiver(context) 方法注册网络监听广播
        setting.setReconnectWithNetworkChanged(true);

        //通过 init 方法初始化默认的 WebSocketManager 对象
        WebSocketManager manager = WebSocketHandler.init(setting);


        manager.addListener(socketListener);
        //启动连接
        manager.start();
//        manager.send("ping");

        manager.sendPing();
        initWebSocket1();
    }

    private void initWebSocket1(){
        WebSocketSetting setting = new WebSocketSetting();
        //连接地址，必填，例如 wss://echo.websocket.org
        WebSocketHandler.initGeneralWebSocket("111",setting);
//        setting.setConnectUrl("ws://192.168.0.101:7578/ws");
        setting.setConnectUrl("ws://47.56.66.251:7577/detail");
        //设置连接超时时间
        setting.setConnectTimeout(15 * 1000);

        //设置心跳间隔时间
        setting.setConnectionLostTimeout(60);

        //设置断开后的重连次数，可以设置的很大，不会有什么性能上的影响
        setting.setReconnectFrequency(5);

        //网络状态发生变化后是否重连，
        //需要调用 WebSocketHandler.registerNetworkChangedReceiver(context) 方法注册网络监听广播
        setting.setReconnectWithNetworkChanged(true);

        //通过 init 方法初始化默认的 WebSocketManager 对象
//        WebSocketManager manager = WebSocketHandler.init(setting);
        //启动连接
//        manager.start();

        //注意，需要在 AndroidManifest 中配置网络状态获取权限
        //注册网路连接状态变化广播
        WebSocketHandler.registerNetworkChangedReceiver(this);
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            if (!(activity.getClass().equals(MainActivity.class)))
                activity.finish();
        }
        mActivitys.clear();
    }

    public static void finishAllActivityTwo() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
//            if (!(activity.getClass().equals(MainActivity.class)))
            activity.finish();
        }
        mActivitys.clear();
    }

    @SuppressLint("ObsoleteSdkInt")
    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /*
                     *  监听到 Activity创建事件 将该 Activity 加入list,MainActivity主页面不加入
                     */
//                    if (!(activity.getClass().equals(MainActivity.class)))
                    pushActivity(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null==mActivitys&&mActivitys.isEmpty()){
                        return;
                    }
                    if (mActivitys.contains(activity)){
                        /*
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        popActivity(activity);
                    }
                }
            });
        }
    }

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        mActivitys.remove(activity);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }
    //TODO Andrid  9.0  弹窗问题
    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
