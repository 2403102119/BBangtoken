package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import bbangtoken.tckkj.com.bbangtoken.View.HeadPickerWindow;
import okhttp3.Call;

/*
*註冊一級頁面
*@Author:李迪迦
*@Date:2019.6.26
*/
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_next_step;
    private LinearLayout ce_email,import_phone,ll_phone_choose,ll_tos;
    private TextView tv_phone_registered,tv_email_registr,tv_phone_choose;
    private String type = "1";
    private ClearEditText cet_phone;
    private String phone;

    List<Map<String,Object>> list=new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_register);
        setTopTitle("註冊");
        bt_next_step = findViewById(R.id.bt_next_step);
        tv_phone_registered = findViewById(R.id.tv_phone_registered);//手機按鈕
        tv_email_registr = findViewById(R.id.tv_email_registr);//郵箱按鈕
        ce_email = findViewById(R.id.ce_email);//郵箱
        import_phone = findViewById(R.id.import_phone);//手機
        ll_phone_choose = findViewById(R.id.ll_phone_choose);//選擇區號
        ll_tos = findViewById(R.id.ll_tos);//選擇區號
        cet_phone = findViewById(R.id.cet_phone);
        tv_phone_choose = findViewById(R.id.tv_phone_choose);

        float size1=tv_phone_registered.getTextSize();
        tv_phone_registered.setTextSize(TypedValue.COMPLEX_UNIT_PX,size1+7);
        tv_phone_registered.getPaint().setFakeBoldText(true);
    }

    @Override
    protected void initEvent() {
        bt_next_step.setOnClickListener(this);
        tv_phone_registered.setOnClickListener(this);
        ll_phone_choose.setOnClickListener(this);
        tv_email_registr.setOnClickListener(this);
        tv_phone_registered.setOnClickListener(this);
        ll_tos.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        select_code();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next_step:
                phone = cet_phone.getText().toString();
                 if (StringUtil.isSpace(phone)){
                     showToast("请输入需要注册的手机号/邮箱");
                     return;
                 }

                Intent intent = new Intent(RegisterActivity.this,Registration_detailsActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("phone",phone);
                startActivity(intent);
                break;
            case R.id.tv_phone_registered://手機註冊
                import_phone.setVisibility(View.VISIBLE);
                ce_email.setVisibility(View.GONE);
                tv_phone_registered.getPaint().setFakeBoldText(true);
                tv_email_registr.getPaint().setFakeBoldText(false);
                float size2=tv_phone_registered.getTextSize();
                tv_phone_registered.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2+7);
                float siz3=tv_email_registr.getTextSize();
                tv_email_registr.setTextSize(TypedValue.COMPLEX_UNIT_PX,siz3-7);

                type = "1";
                break;
            case R.id.tv_email_registr://郵箱註冊
                tv_email_registr.getPaint().setFakeBoldText(true);
                tv_phone_registered.getPaint().setFakeBoldText(false);

                import_phone.setVisibility(View.GONE);
                ce_email.setVisibility(View.VISIBLE);
                float size=tv_email_registr.getTextSize();
                tv_email_registr.setTextSize(TypedValue.COMPLEX_UNIT_PX,size+7);
                float size1=tv_phone_registered.getTextSize();
                tv_phone_registered.setTextSize(TypedValue.COMPLEX_UNIT_PX,size1-7);

                type = "2";
                break;
            case R.id.ll_phone_choose://選擇區號
                choose(tv_phone_choose,list,"選擇區號");
                Log.i("choose", "onClick: "+list.size());
                break;
            case R.id.ll_tos://服务条款
                Intent intent1 = new Intent(RegisterActivity.this,WebviewActivity.class);
                intent1.putExtra("url","file:///android_asset/jiekuanxieyi.html");
                startActivity(intent1);
                break;
        }
    }
    private void choose(final TextView textView, List<Map<String,Object>> data, String titleStr){
        hideInputMethod();
        HeadPickerWindow dialog = new HeadPickerWindow(RegisterActivity.this,
                data,titleStr, 18);
        dialog.setPickListener(new HeadPickerWindow.PickListener() {
            @Override
            public void pick(String str) {
                textView.setText(str);
            }
        });
        dialog.show();

    }

    // 选择区号
    public void select_code(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.select_code( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.SelectModel selectModel = JSON.parseObject(result,Define.SelectModel.class);
                            showToast(selectModel.message);
                            try {
                                List<Define.selectlistmodel> selectlistmodelList = selectModel.data;
                                for (int i = 0; i <selectlistmodelList.size() ; i++) {
                                    String name = selectlistmodelList.get(i).name;
                                    String code = selectlistmodelList.get(i).code;
                                    Map<String,Object>map = new HashMap<>();
                                    map.put("name",name);
                                    map.put("code",code);
                                    list.add(map);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

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
