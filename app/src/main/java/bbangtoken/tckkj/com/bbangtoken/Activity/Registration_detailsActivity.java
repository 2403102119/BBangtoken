package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import java.util.List;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.MD5Utils;
import bbangtoken.tckkj.com.bbangtoken.Util.MUIToast;
import bbangtoken.tckkj.com.bbangtoken.Util.MyCountDownTimer;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.UriUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import okhttp3.Call;

/*
*註冊二級頁面
*@Author:李迪迦
*@Date:2019.6.26
*/
public class Registration_detailsActivity extends BaseActivity implements View.OnClickListener {
    private Button registeron;
    private String type;
    private String iphone;
    private Button gc_code;

    private String send_class;
    private String secret_key;
    private String send_ip;
    private EditText ed_password,notarize_pwd,invit_code;
    private ClearEditText yzm,deal_paw,seal_deal;
    private String auxiliaries;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_registration_details);
        setTitle("註冊");
        registeron = findViewById(R.id.registeron);
        gc_code = findViewById(R.id.gc_code);
        yzm = findViewById(R.id.yzm);
        ed_password = findViewById(R.id.ed_password);
        notarize_pwd = findViewById(R.id.notarize_pwd);
        deal_paw = findViewById(R.id.deal_paw);
        seal_deal = findViewById(R.id.seal_deal);
        invit_code = findViewById(R.id.invit_code);

    }

    @Override
    protected void initEvent() {
        registeron.setOnClickListener(this);
        gc_code.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        iphone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registeron:
//                Intent intent2 = new Intent(Registration_detailsActivity.this,MnemonicActivity.class);
//                startActivity(intent2);
                String codeStr = yzm.getText().toString().trim();
                String pwdStr = ed_password.getText().toString().trim();
                String no_pwd = notarize_pwd.getText().toString().trim();
                String dea_pa = deal_paw.getText().toString().trim();
                String sea_dea = seal_deal.getText().toString().trim();
                String inv_code = invit_code.getText().toString().trim();
                if (StringUtil.isSpace(codeStr)){
                    showToast("请输入验证码");
                    return;
                }else if (StringUtil.isSpace(pwdStr)){
                    showToast("请输入您要设置的密码");
                    return;
                }else if (StringUtil.isSpace(no_pwd)){
                    showToast("请确认登录密码");
                    return;
                }else if (StringUtil.isSpace(dea_pa)){
                    showToast("请设置6位纯数字交易密码");
                    return;
                }else if (StringUtil.isSpace(sea_dea)){
                    showToast("请确认交易密码");
                    return;
                }else if (StringUtil.isSpace(inv_code)){
                    showToast("请输入邀请码");
                    return;
                }


                no_pwd = MD5Utils.md5Password(no_pwd);
                sea_dea = MD5Utils.md5Password(sea_dea);

                registeron.setClickable(false);
                regist(iphone,no_pwd,codeStr,sea_dea,inv_code);
                break;
            case R.id.gc_code:
                send_ip= UriUtil.host_ip;
                send_class="user_register";
                secret_key= MD5Utils.md5Password(iphone+send_class);
                secret_key= MD5Utils.md5Password(secret_key+"qaz123");
                getCode(iphone,type,secret_key,send_ip);
                break;
        }
    }

    // 获取验证码
    public void getCode(final String iphone,final String type, final String secret_key,final String send_ip){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.getCode(iphone, type,secret_key,send_ip, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("code", "onSuccess: "+result);
                            JSONObject jb = JSONObject.parseObject(result);
                            String message = jb.getString("message");
                            showToast(message);
                            //验证码倒计时
                            MyCountDownTimer timer = new MyCountDownTimer(Registration_detailsActivity.this,
                                    gc_code,60*1000,1000);
                            timer.start();
                        }

                        @Override
                        public void onFail(String response) {
                            JSONObject jb = JSONObject.parseObject(response);
                            String message = jb.getString("message");
                            MUIToast.show(Registration_detailsActivity.this,message);
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
            gc_code.setClickable(true);
        }
    }



    //注册
    public void regist(final String u_account, final String u_password, final String verify_code,final String t_password,final String code){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.registir(u_account, u_password, verify_code, t_password,code, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {

                            Define.RegistModel registModel = JSONObject.parseObject(result,Define.RegistModel.class);
                            MUIToast.show(Registration_detailsActivity.this,registModel.message);
                            try {
                                List<Define.registlistmodel> registlistmodelList = registModel.data;
                                for (int i = 0; i <registlistmodelList.size() ; i++) {
                                    auxiliaries = registlistmodelList.get(i).auxiliaries;
                                }
                                Intent intent1 = new Intent(Registration_detailsActivity.this,MnemonicActivity.class);
                                intent1.putExtra("auxiliaries",auxiliaries);
                                startActivity(intent1);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFail(String response) {
                            showToast("注册失败");
                        }

                        @Override
                        public void onError(Call call, Exception exception) {

                        }

                        @Override
                        public void onFinish(String response) {
                            registeron.setClickable(true);
                        }
                    });
                }
            });
        }else{
            registeron.setClickable(true);
        }
    }
}
