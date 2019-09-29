package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;

import bbangtoken.tckkj.com.bbangtoken.App;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.MainActivity;
import bbangtoken.tckkj.com.bbangtoken.R;

/*
*启动页面
*@Author:李迪迦
*@Date:
*/
public class StartActivity extends BaseActivity {
    private Handler handler=new Handler();
    private boolean isIntent;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_start);
//        setTheme(R.style.AppTheme_Launcher);
        setTopTitle("");
//        isIntent = (boolean)SPUtil.getData(this,"isLogined",false);
        baseTop.setVisibility(View.GONE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (App.islogin) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                }
                finish();
            }
        },2000);


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
}
