package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.MD5Utils;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ActionDialog;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import bbangtoken.tckkj.com.bbangtoken.View.PayPasswordDialog;
import bbangtoken.tckkj.com.bbangtoken.View.PayPasswordView;
import okhttp3.Call;
/*
*轉出頁面
*@Author:李迪迦
*@Date:2019.6.26
*/

public class RoolActivity extends BaseActivity implements View.OnClickListener {
    private PopupWindow modify_popu_item;
    private LinearLayout modify_item,modify_layout;

    private ImageView pepo;
    private Button bt_conterl;
    private ActionDialog hintdialog;
    private String currency_id;
    private ClearEditText ce_moeney,ce_amount;
    private int REQUEST_CODE = 1;
    private static final int DO_REGIST = 0x10;
    private TextView tv_handing;
    private String result;
    private String moeney;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_rool);
        setTopTitle("BTC轉出");
        rightIcon.setVisibility(View.VISIBLE);
        rightIcon.setImageResource(R.mipmap.saoma);
        pepo = findViewById(R.id.pepo);
        bt_conterl = findViewById(R.id.bt_conterl);
        ce_moeney = findViewById(R.id.ce_moeney);
        ce_amount = findViewById(R.id.ce_amount);
        tv_handing = findViewById(R.id.tv_handing);

        hintdialog = new ActionDialog(RoolActivity.this,"轉出會有一定時間的延遲，請耐心等待","确定",false);
        hintdialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                //取消

            }

            @Override
            public void onRightClick() {

                hintdialog.dismiss();

            }
        });
    }

    @Override
    protected void initEvent() {
        pepo.setOnClickListener(this);
        bt_conterl.setOnClickListener(this);
        rightIcon.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        currency_id = getIntent().getStringExtra("currency_id");
        result = getIntent().getStringExtra("result");
        moeney = getIntent().getStringExtra("moeney");
        ce_moeney.setText(result);
        ce_amount.setText(moeney);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case DO_REGIST:
                    try{
                        String key = data.getStringExtra("key");
                        if (StringUtil.isSpace(key)){

                        }else{
                            ce_moeney.setText(key);
                        }
                    }catch (Exception e){

                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pepo:
                Intent intent_2 = new Intent(RoolActivity.this,SiteActivity.class);
                startActivityForResult(intent_2,DO_REGIST);
                break;
            case R.id.bt_conterl:
//                final PayPasswordDialog dialog=new PayPasswordDialog(RoolActivity.this,R.style.mydialog);
//                dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
//                    @Override
//                    public void doConfirm(String password) {
//                        dialog.dismiss();
//                        Toast.makeText(RoolActivity.this,password,Toast.LENGTH_LONG).show();
//                    }
//                });

                modify();
                modify_item.startAnimation(AnimationUtils.loadAnimation(RoolActivity.this,R.anim.activity_translate_in));
                modify_popu_item.showAtLocation(v, Gravity.CENTER,0,0);
                break;

            case R.id.rightIcon:
                Intent intent1 = new Intent(RoolActivity.this, CaptureActivity.class);
                startActivityForResult(intent1, REQUEST_CODE);
                break;


        }
    }


    public  void modify(){
        modify_popu_item=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.transaction_password,null);
        modify_item= view.findViewById(R.id.modify_item);
        modify_popu_item.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setBackgroundDrawable(new BitmapDrawable());
        modify_popu_item.setFocusable(true);
        modify_popu_item.setOutsideTouchable(true);
        modify_popu_item.setContentView(view);
        modify_layout= view.findViewById(R.id.modify_layout);
        final EditText be_as=view.findViewById(R.id.be_as);
        ImageView modify_cancel=view.findViewById(R.id.modify_cancel);
        TextView modify_yes=view.findViewById(R.id.modify_yes);

        PayPasswordView payPassword = view.findViewById(R.id.pay_password);
        modify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                modify_popu_item.dismiss();
//                modify_item.clearAnimation();
            }
        });
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

                modify_popu_item.dismiss();
                modify_item.clearAnimation();
                Intent intent = new Intent(RoolActivity.this,ForgetActivity.class);
                startActivity(intent);


            }
        });

        payPassword.setPayPasswordEndListener(new PayPasswordView.PayEndListener() {
            @Override
            public void doEnd(String password) {
                password = MD5Utils.md5Password(password);
                password = MD5Utils.md5Password(password+"qaz123");
                rool_out(ce_moeney.getText().toString(),ce_amount.getText().toString(),currency_id,password,tv_handing.getText().toString());
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
                hintdialog.show();

            }
        });
    }
    // 转出
    public void rool_out(final String address,final String amount,final String currency_id,final String t_password,final String service_money){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.rool_out(address,amount,currency_id,t_password, service_money,new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            JSONObject jb = JSONObject.parseObject(result);
                            String message = jb.getString("message");
                            showToast(message);

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


}
