package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Fragment.InformFragment;
import bbangtoken.tckkj.com.bbangtoken.Fragment.SystermFragment;
import bbangtoken.tckkj.com.bbangtoken.R;
/*
*站内消息页面
*@Author:李迪迦
*@Date:
*/
public class MessagerieActivity extends BaseActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    public TextView moreRadio,meRadio;
    private Fragment systermFragment,informFragment;
    private ImageView zhuanzhang,xitong;
    public static MessagerieActivity instance;
    private LinearLayout tab_zhuanzhang,tab_xitong;



    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_messagerie);
        setTopTitle("站内消息");


        instance = this;
        radioGroup = findViewById(R.id.radioGroup);
        meRadio = findViewById(R.id.tab_me);
        moreRadio=findViewById(R.id.tab_more);
        zhuanzhang = findViewById(R.id.zhuanzhang);
        xitong = findViewById(R.id.xitong);
        tab_zhuanzhang = findViewById(R.id.tab_zhuanzhang);
        tab_xitong = findViewById(R.id.tab_xitong);


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//            }
//        });

        setSelect(1);
        moreRadio.setTextColor(getResources().getColor(R.color.button_color));
    }

    @Override
    protected void initEvent() {
        tab_xitong.setOnClickListener(this);
        tab_zhuanzhang.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_zhuanzhang:
                setSelect(1);
                moreRadio.setTextColor(getResources().getColor(R.color.button_color));
                meRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                zhuanzhang.setVisibility(View.VISIBLE);
                xitong.setVisibility(View.GONE);
                break;
            case R.id.tab_xitong:
                setSelect(2);
                meRadio.setTextColor(getResources().getColor(R.color.button_color));
                moreRadio.setTextColor(getResources().getColor(R.color.loan_text_gray));
                zhuanzhang.setVisibility(View.GONE);
                xitong.setVisibility(View.VISIBLE);

                break;

        }
    }



    public void setSelect(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);//先隐藏所有界面
        switch (i) {
            case 1:
                if (informFragment == null) {
                    informFragment = new InformFragment();
                    transaction.add(R.id.main_content1, informFragment);
                } else {
                    transaction.show(informFragment);
                }
                break;
            case 2:
                if (systermFragment == null) {
                    systermFragment = new SystermFragment();
                    transaction.add(R.id.main_content1, systermFragment);
                } else {
                    transaction.show(systermFragment);
                }
                break;

            default:
                break;
        }
        transaction.commit();
    }

    //用于隐藏界面
    private void hideFragment(FragmentTransaction transaction) {

        if (informFragment != null) {
            transaction.hide(informFragment);
        }
        if (systermFragment != null) {
            transaction.hide(systermFragment);
        }

    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exit() {

            finish();
        }



    @Override
    protected void onResume() {
        super.onResume();
//        if (!App.isLogined){
//            MainActivity.instance.setSelect(0);
//            MainActivity.instance.homeRadio.setChecked(true);
//        }else {
//
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
