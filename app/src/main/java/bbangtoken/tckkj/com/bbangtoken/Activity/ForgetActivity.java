package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.MD5Utils;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import okhttp3.Call;

/*
*忘記密碼界面
*@Author:李迪迦
*@Date:
*/
public class ForgetActivity extends BaseActivity implements View.OnClickListener {
    private ImageView im_down;
    private PopupWindow state2,modify_popu_item,add_item;
    private RelativeLayout state3;
    private LinearLayout state_xxx,modify_item,modify_layout,linearLayout,add_item_lin;
    private TextView tv_login_password;
    private Button bt_next_step;
    private String be_asd;
    private String type = "1";
    private ClearEditText private_key;
    private String private_key_pas;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_forget);
        setTopTitle("忘記密碼");
        im_down = findViewById(R.id.im_down);
        tv_login_password = findViewById(R.id.tv_login_password);
        bt_next_step = findViewById(R.id.bt_next_step);
        private_key = findViewById(R.id.private_key);
    }

    @Override
    protected void initEvent() {
        im_down.setOnClickListener(this);
        bt_next_step.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_down:
                state();
                state_xxx.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                state2.showAtLocation(v, Gravity.BOTTOM,0,0);
                break;
            case R.id.bt_next_step:
                private_key_pas = private_key.getText().toString().trim();
                forget(MD5Utils.getHostIP(),private_key_pas,v);


                break;
        }
    }
    //選擇密鑰類型彈窗
    public  void  state(){
        state2=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.state_down,null);
        state_xxx= view.findViewById(R.id.state_xx);
        state2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        state2.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        state2.setBackgroundDrawable(new BitmapDrawable());
        state2.setFocusable(true);
        state2.setOutsideTouchable(true);
        state2.setContentView(view);
        state3=view.findViewById(R.id.state_xxx);
        final TextView tv_login = view.findViewById(R.id.tv_login);
        final TextView tv_deal = view.findViewById(R.id.tv_deal);
        final TextView quxiao = view.findViewById(R.id.quxiao);
        state3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "1";
                tv_login_password.setText(tv_login.getText().toString());
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        tv_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "2";
                tv_login_password.setText(tv_deal.getText().toString());
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });

    }
    public  void modify(){
        modify_popu_item=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.modify_popu,null);
        modify_item= view.findViewById(R.id.modify_item);
        modify_popu_item.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setBackgroundDrawable(new BitmapDrawable());
        modify_popu_item.setFocusable(true);
        modify_popu_item.setOutsideTouchable(true);
        modify_popu_item.setContentView(view);
        modify_layout= view.findViewById(R.id.modify_layout);
        final EditText be_as=view.findViewById(R.id.be_as);
        TextView modify_cancel=view.findViewById(R.id.modify_cancel);
        TextView modify_yes=view.findViewById(R.id.modify_yes);
//        modify_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                modify_popu_item.dismiss();
//                modify_item.clearAnimation();
//            }
//        });
        modify_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
            }
        });
        modify_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                be_asd = be_as.getText().toString().trim();
                verify(private_key_pas,be_asd,view);
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
//                if (StringUtil.isSpace(be_asd)){
//                    showToast("请填写验证码");
//                    return;
//                }






            }
        });
    }
    public void grouping() {
        add_item = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.activity_add_item, null);
        linearLayout = view.findViewById(R.id.add_popuo);
        add_item.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        add_item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        add_item.setBackgroundDrawable(new BitmapDrawable());
        add_item.setFocusable(true);
        add_item.setOutsideTouchable(true);
        add_item.setContentView(view);
        add_item_lin = view.findViewById(R.id.add_item_lin);
        final EditText mingzi = view.findViewById(R.id.mingzi);
        final EditText con_pass = view.findViewById(R.id.con_pass);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confirm = view.findViewById(R.id.confirm);
//        add_item_lin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                add_item.dismiss();
//                linearLayout.clearAnimation();
//            }
//        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_item.dismiss();
                linearLayout.clearAnimation();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_pass = mingzi.getText().toString().trim();
                String con_password = con_pass.getText().toString().trim();
                new_pass= MD5Utils.md5Password(new_pass);
                con_password= MD5Utils.md5Password(con_password);
                set_password(new_pass,con_password,private_key_pas,type,"user_forget",be_asd);
                add_item.dismiss();
                linearLayout.clearAnimation();
            }
        });
    }
   // 忘记密码发送短信
    public void forget(final String send_ip,final String private_key,final View view){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.slip( send_ip, private_key,new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {


                            modify();
                            modify_item.startAnimation(AnimationUtils.loadAnimation(ForgetActivity.this,R.anim.activity_translate_in));
                            modify_popu_item.showAtLocation(view, Gravity.CENTER,0,0);
//                            showToast("登录成功");


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

    // 验证验证码
    public void verify(final String secret_key,final String verify_code,final View view){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.verify(secret_key,verify_code, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("code", "onSuccess: "+result);
                            grouping();
                            linearLayout.startAnimation(AnimationUtils.loadAnimation(ForgetActivity.this, R.anim.activity_translate_in));
                            add_item.showAtLocation(view, Gravity.CENTER, 0, 0);
                        }

                        @Override
                        public void onFail(String response) {
                            showToast(response);
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
 // 设置新密码
    public void set_password(final String new_password,final String con_password,final String p_key,final String  send_type,final String send_class,final String verify_code ){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.set_password(new_password,con_password,p_key,send_type,send_class,verify_code, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("code", "onSuccess: "+result);
                            Intent intent = new Intent(ForgetActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFail(String response) {
                            showToast(response);
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




}
