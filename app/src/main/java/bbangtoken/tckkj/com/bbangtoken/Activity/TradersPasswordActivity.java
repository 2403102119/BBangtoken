package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import bbangtoken.tckkj.com.bbangtoken.App;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.MD5Utils;
import bbangtoken.tckkj.com.bbangtoken.Util.MyCountDownTimer;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.UriUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ActionDialog;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import okhttp3.Call;

/*
*修改交易密码页面
*@Author:李迪迦
*@Date:
*/
public class TradersPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_ok,bt_code;

    private TextView tv_phone;
    private ActionDialog updateDialog;
    private ClearEditText ce_code,ce_new_password,ce_affim;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_traders_password);
        setTopTitle("修改交易密碼");
        bt_ok = findViewById(R.id.bt_ok);
        bt_code = findViewById(R.id.bt_code);
        tv_phone = findViewById(R.id.tv_phone);
        ce_code = findViewById(R.id.ce_code);
        ce_affim = findViewById(R.id.ce_affim);
        ce_new_password = findViewById(R.id.ce_new_password);
        tv_phone.setText(App.phone);
        updateDialog = new ActionDialog(TradersPasswordActivity.this,"密碼修改成功，請返回重新登錄","确定",false);
        updateDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                //取消

            }

            @Override
            public void onRightClick() {

                Intent intent = new Intent(TradersPasswordActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void initEvent() {
        bt_ok.setOnClickListener(this);
        bt_code.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_ok:
                String ce_cod = ce_code.getText().toString().trim();
                String ce_new_pass = ce_new_password.getText().toString().trim();
                String ce_affi = ce_affim.getText().toString().trim();
                ce_new_pass = MD5Utils.md5Password(ce_new_pass);
                ce_affi = MD5Utils.md5Password(ce_affi);
                change_password(App.phone,ce_cod,"2",ce_new_pass,ce_affi);
                break;
            case R.id.bt_code:
                Amend(App.phone, UriUtil.host_ip);
                break;

        }
    }
    //修改密码发送验证码
    public void Amend(final String account,final String send_ip){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.amend(account,send_ip, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Sort sort = JSON.parseObject(result,Define.Sort.class);
                            showToast(sort.message);
                            //验证码倒计时
                            MyCountDownTimer timer = new MyCountDownTimer(TradersPasswordActivity.this,
                                    bt_code,2*60*1000,1000);
                            timer.start();
                        }

                        @Override
                        public void onFail(String response) {
                        }

                        @Override
                        public void onError(Call call, Exception exception) {

                        }

                        @Override
                        public void onFinish(String response) {
                        }
                    });
                }
            });
        }else{
        }
    }
    //修改密码
    public void change_password(final String account,final String verify_code,final String send_type,final String new_password,final String con_password){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.change_password(account,verify_code,send_type,new_password,con_password, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            updateDialog.show();
                        }

                        @Override
                        public void onFail(String response) {
                            showToast("修改失败，请重试");
                        }

                        @Override
                        public void onError(Call call, Exception exception) {
                            showToast("修改失败，请重试");
                        }

                        @Override
                        public void onFinish(String response) {

                        }
                    });
                }
            });
        }else{
        }
    }
}
