package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;

import java.util.List;

import bbangtoken.tckkj.com.bbangtoken.App;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.MainActivity;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.MD5Utils;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.PhoneUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.SharedPreferencesUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.UriUtil;
import okhttp3.Call;

/*
*登錄界面
*@Author:李迪迦
*@Date:2019.6.26
*/
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_go_register,ll_forget_password,ll_langlung;
    private Button bt_enter;
    private EditText phone,password;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_login);
        ll_go_register = findViewById(R.id.ll_go_register);
        baseTop.setVisibility(View.GONE);
        ll_forget_password = findViewById(R.id.ll_forget_password);
        bt_enter = findViewById(R.id.bt_enter);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        ll_langlung = findViewById(R.id.ll_langlung);
        //设置状态栏文字颜色及图标为深色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void initEvent() {
        ll_go_register.setOnClickListener(this);
        ll_forget_password.setOnClickListener(this);
        bt_enter.setOnClickListener(this);
        ll_langlung.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_go_register:
                Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_forget_password:
                Intent intent1 = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_enter:

                String phoneStr = phone.getText().toString().trim();
                String pwdStr = password.getText().toString().trim();

                if (!PhoneUtil.isPhone(phoneStr)){
                    showToast("请输入正确的手机号");
                    return;
                }
                if (StringUtil.isSpace(pwdStr)){
                    showToast("请输入密码");
                    return;
                }

                pwdStr = MD5Utils.md5Password(pwdStr);
                pwdStr = MD5Utils.md5Password(pwdStr+"qaz123");
//                SharedPreferencesUtil.saveData(getApplicationContext(), UriUtil.account,phoneStr);
                SharedPreferencesUtil.saveData(getApplicationContext(),UriUtil.pwd,pwdStr);
//                bt_enter.setClickable(false);
                login(phoneStr,pwdStr);

                break;
            case R.id.ll_langlung:
                Intent intent3=new Intent(LoginActivity.this, LanguageActivity.class);
                startActivity(intent3);
                break;
        }

    }
    public void login(final String phoneStr, final String pwdStr){
        if (NetUtil.isNetWorking(LoginActivity.this)){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.login(phoneStr, pwdStr, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {

                            try{
                                Define.LoginModel loginModels = JSON.parseObject(result,Define.LoginModel.class);
                                App.islogin = true;
                                showToast(loginModels.message);
                                List<Define.loginlist> dataList = loginModels.data;
                                for (int i = 0; i < dataList.size(); i++) {
                                    App.token = dataList.get(i).token;
                                    App.user_id = dataList.get(i).user_id;
                                }
                                App.phone = phoneStr;
                                SharedPreferencesUtil.saveData(getApplicationContext(), "phone",phoneStr);
                                SharedPreferencesUtil.saveData(getApplicationContext(),UriUtil.pwd,pwdStr);
                                SharedPreferencesUtil.saveData(getApplicationContext(), "token", App.token);
                                SharedPreferencesUtil.saveData(getApplicationContext(), "user_id", App.user_id);

                                SharedPreferencesUtil.saveData(getApplicationContext(), "islogin",App.islogin);
//                                setResult(RESULT_OK);
//                                MainActivity.instance.phoneStr = phoneStr;

                                Intent intent2=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent2);

                                finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFail(String response) {
                            StringUtil.showMessage(getApplicationContext(),response,"登录失败，请稍候再试");
                        }

                        @Override
                        public void onError(Call call, Exception exception) {
                             showToast("1111");
                        }

                        @Override
                        public void onFinish(String response) {
                            bt_enter.setClickable(true);
                        }
                    });
                }
            });
        }else{
//            bt_enter.setClickable(true);
        }
    }
}
