package bbangtoken.tckkj.com.bbangtoken;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;


import org.json.JSONObject;

import java.util.List;

import bbangtoken.tckkj.com.bbangtoken.Activity.LoginActivity;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.Fragment.HomeFragment;
import bbangtoken.tckkj.com.bbangtoken.Fragment.MeFragment;
import bbangtoken.tckkj.com.bbangtoken.Fragment.MoreFragment;
import bbangtoken.tckkj.com.bbangtoken.Fragment.PriceFragment;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.LoggerUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.MACUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.MD5Utils;
import bbangtoken.tckkj.com.bbangtoken.Util.MUIToast;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.PakageUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.UriUtil;
import okhttp3.Call;

public class MainActivity extends BaseActivity {

    private RadioGroup radioGroup;
    public RadioButton homeRadio,moreRadio,meRadio,authRadio;
    private Fragment homeFragment,meFragment,moreFragment,priceFragment;
    public static MainActivity instance;
    private long mExitTime;//退出时的时间
    public String phoneStr = "";
    /*提示更新操作步骤的弹窗*/
    private String updatePath;
    private boolean hasCreate = false;
    public String kefuStr = "",tousuStr = "";
    public String str;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        baseTop.setVisibility(View.VISIBLE);
        instance = this;
        hasCreate = false;
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        homeRadio = findViewById(R.id.tab_home);
        meRadio = findViewById(R.id.tab_me);
        moreRadio=findViewById(R.id.tab_more);
        authRadio = findViewById(R.id.tab_auth);
        WebSocketHandler.getDefault().sendPing();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab_home:
                        setSelect(0);
                        homeRadio.setTextColor(getResources().getColor(R.color.button_color));
                        meRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        moreRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        authRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        //设置状态栏文字颜色及图标为深色
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                        break;
                    case R.id.tab_more:
//                        if (App.isLogined){
                        setSelect(1);
                        homeRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        moreRadio.setTextColor(getResources().getColor(R.color.button_color));
                        meRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        authRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        //设置状态栏文字颜色及图标为深色
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                        }else{
//                            homeRadio.setChecked(true);
//                            if (hasCreate){
//                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                            }
//                        }
                        break;
                    case R.id.tab_me:
//                        if (App.isLogined){
                        setSelect(2);
                        homeRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        meRadio.setTextColor(getResources().getColor(R.color.button_color));
                        moreRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        authRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        //设置状态栏文字颜色及图标为浅色
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//                        }else{
//                            homeRadio.setChecked(true);
//                            if (hasCreate){
//                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                            }
//                        }
                        break;
                    case R.id.tab_auth:
//                        if (App.isLogined){
                        setSelect(3);
                        homeRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        authRadio.setTextColor(getResources().getColor(R.color.button_color));
                        moreRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        meRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                        //设置状态栏文字颜色及图标为深色
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                        }else{
//                            homeRadio.setChecked(true);
//                        if (hasCreate){
//                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                            }
//                        }
                        break;
                }
            }
        });

        setSelect(0);
        homeRadio.setTextColor(getResources().getColor(R.color.button_color));


        Acp.getInstance(this).request(new AcpOptions.Builder()
                .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA)
                .build(), new AcpListener() {
            @Override
            public void onGranted() {
//                rb_index.setChecked(true);
                setSelect(0);
            }

            @Override
            public void onDenied(List<String> permissions) {
                MUIToast.show(MainActivity.this, permissions.toString() + "权限拒绝");
            }
        });


    }

    @Override
    protected void initEvent() {


    }

    @Override
    protected void initData() {

        String phone_uuid = MACUtil.getNewMac();
        String timestamp = System.currentTimeMillis()+"";
        String md5_secret_key = MD5Utils.md5Password(phone_uuid + UriUtil.host_ip + timestamp);
        md5_secret_key = MD5Utils.md5Password(md5_secret_key+"qaz123");

//        UV(phone_uuid, UriUtil.host_ip, Build.BRAND,Build.VERSION.RELEASE,timestamp,md5_secret_key);
//        getUpdateMsg();//应用更新
//        getContact();

    }

    public void setSelect(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);//先隐藏所有界面
        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.main_content, moreFragment);
                } else {
                    transaction.show(moreFragment);
                }
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.main_content, meFragment);
                } else {
                    transaction.show(meFragment);
                }
                break;
            case 3:
                if (priceFragment == null) {
                    priceFragment = new PriceFragment();
                    transaction.add(R.id.main_content, priceFragment);
                } else {
                    transaction.show(priceFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    //用于隐藏界面
    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        if (priceFragment != null) {
            transaction.hide(priceFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment){//解决fragment重叠问题
        if (homeFragment == null && fragment instanceof HomeFragment)
            homeFragment = fragment;
        if (moreFragment == null && fragment instanceof MoreFragment)
            moreFragment=  fragment;
        if (meFragment == null && fragment instanceof MeFragment)
            meFragment=  fragment;
        if (priceFragment == null && fragment instanceof PriceFragment)
            priceFragment=  fragment;

    }


//
//    /*获取应用更新信息*/
//    private void getUpdateMsg(){
//        if (NetUtil.isNetWorking(getApplicationContext())){
//            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
//                @Override
//                public void run() {
//                    httpInterface.updateMsg(new MApiResultCallback() {
//                        @Override
//                        public void onSuccess(String result) {
//                            try{
//                                JSONObject model = new JSONObject(result).optJSONObject("data");
//                                String edition = model.optString("version").trim();
//                                String toUpdate = model.optString("force_update");//1 强制更新
//                                updatePath = model.optString("apkurl");
//                                String  localEdition = PakageUtil.getVersionName(MainActivity.this);
//                                if (!localEdition.equals(edition)){//与最新版本号不一致
//                                    if ("1".equals(toUpdate)){//强制更新
//                                        updateDialog.setShowCancle(false);
//                                        updateDialog.show();
//                                    }else{
//                                        updateDialog.setShowCancle(true);
//                                        updateDialog.show();
//                                    }
//                                }
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFail(String response) {
//                        }
//
//                        @Override
//                        public void onError(Call call, Exception exception) {
//
//                        }
//
//                        @Override
//                        public void onFinish(String response) {
//
//                        }
//                    });
//                }
//            });
//        }else{
//
//        }
//    }



    @Override
    protected void onResume() {
        super.onResume();
        hasCreate = true;
//        if (!App.isLogined){
//            MainActivity.instance.setSelect(0);
//            MainActivity.instance.homeRadio.setChecked(true);
//        }else {
//            getAuthState(App.user_id+"");
//            HomeFragment.instance.judgeRepayment(App.user_id+"");
//        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}
