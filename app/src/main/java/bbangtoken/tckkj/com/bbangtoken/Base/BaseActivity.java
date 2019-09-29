package bbangtoken.tckkj.com.bbangtoken.Base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.HttpInterface;
import bbangtoken.tckkj.com.bbangtoken.Util.MUIToast;


/**
 *
 * kylin on 2017/11/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected int BASE_EVERY_ADD = 5;

    //"“¥"

    protected HttpInterface httpInterface;
    protected TextView topTitle,rightText;
    protected ImageView rightIcon;
    private FrameLayout container;
    public ImageView baseback;
    protected View baseTop;

    private View statusBar;
    protected boolean useThemestatusBarColor = true;//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useStatusBarColor = true;//是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        httpInterface = new HttpInterface(this);

        container = (FrameLayout) findViewById(R.id.container);
        baseTop = findViewById(R.id.top);
        topTitle = (TextView) baseTop.findViewById(R.id.baseTitle);
        rightText = (TextView) baseTop.findViewById(R.id.rightText);
        rightIcon = (ImageView)baseTop.findViewById(R.id.rightIcon);
        baseback = (ImageView) baseTop.findViewById(R.id.back);
        statusBar = baseTop.findViewById(R.id.statusBar);
        baseback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        statusBar.setVisibility(View.VISIBLE);
        initView(savedInstanceState);
        initEvent();
        initData();
        setStatusBar();
    }


    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.black));

            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    protected void setContainer(int layoutId){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId,container,true);
    }

    public static final int TOP_PRIMARY_COLOR = 0x66;
    public static final int TOP_PRIMARY_WHITE = 0x67;

    protected void setTopPrimaryColor(int style){
        if (style==TOP_PRIMARY_COLOR){
            //statusBar.setBackgroundColor(Color.parseColor("#ffffff"));
            baseTop.setBackgroundColor(getResources().getColor(R.color.zhuti_color));
            baseback.setImageResource(R.mipmap.nav_back_black);
            topTitle.setTextColor(Color.parseColor("#333333"));
            rightText.setTextColor(Color.parseColor("#ffffff"));
        }else{
            statusBar.setBackgroundColor(Color.parseColor("#111111"));
            baseTop.setBackgroundColor(Color.parseColor("#F0F1F5"));
            baseback.setImageResource(R.mipmap.nav_back_black);
            topTitle.setTextColor(Color.parseColor("#333333"));
            rightText.setTextColor(Color.parseColor("#479EFF"));
        }
    }

    protected void setTopTitle(String title){
        topTitle.setText(title);
    }

    public void showToast(final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //放在UI线程弹Toast
                        MUIToast.show(BaseActivity.this, msg);
                    }
                });
                //此处会发生异常
//          Toast.makeText(MainActivity.this, "toast in work thread", Toast.LENGTH_LONG).show();
            }
        }).start();
    }

    public void showToast(final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //放在UI线程弹Toast
                        MUIToast.toast(BaseActivity.this, id);
                    }
                });
                //此处会发生异常
//          Toast.makeText(MainActivity.this, "toast in work thread", Toast.LENGTH_LONG).show();
            }
        }).start();
    }

    public void hideInputMethod(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    // 初始化ui
    protected abstract void initView(Bundle savedInstanceState);
    // 添加监听器
    protected abstract void initEvent();
    // 初始化数据
    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();

    /*    try{
            if (App.appStatus==-1){
                Log.e("BaseActivity","-1");
                restartActivity();
            }else{
                Log.e("BaseActivity","0");
            }
        }catch (Exception e){
            e.printStackTrace();
            restartActivity();
        }*/


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void restartActivity(){
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getApplication().getPackageName());
        LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(LaunchIntent);
    }


}
