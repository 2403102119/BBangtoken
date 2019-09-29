package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*备份助记词二级页面
*@Author:李迪迦
*@Date:
*/
public class BackupsActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_enter,word_item,tv_select;
    public List<String> list = new ArrayList<>();
    private String a1,a2,a3,a4;
    private String s1 = "",s2 = "",s3 = "",s4 = "";
    private PopupWindow state2;
    private RelativeLayout state3;
    private LinearLayout state_xxx;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_backups);
        setTopTitle("備份助記詞");
        tv_enter = findViewById(R.id.tv_enter);
        tv_select = findViewById(R.id.tv_select);
        word_item = findViewById(R.id.word_item);
        word_item.setText(s1+s2+s3+s4);
    }

    @Override
    protected void initEvent() {
        tv_enter.setOnClickListener(this);
        tv_select.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        a1 = getIntent().getStringExtra("s");
        a2 = getIntent().getStringExtra("y");
        a3 = getIntent().getStringExtra("z");
        a4 = getIntent().getStringExtra("x");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_enter:

                Mnemonic_words(word_item.getText().toString());
                break;
            case R.id.tv_select:
                state();
                state_xxx.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                state2.showAtLocation(v, Gravity.BOTTOM,0,0);
                break;
        }
    }

    public  void  state(){
        state2=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.word,null);
        state_xxx= view.findViewById(R.id.state_xx);
        state2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        state2.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        state2.setBackgroundDrawable(new BitmapDrawable());
        state2.setFocusable(true);
        state2.setOutsideTouchable(true);
        state2.setContentView(view);
        state3=view.findViewById(R.id.state_xxx);
        TextView quxiao = view.findViewById(R.id.quxiao);
        final TextView zidong = view.findViewById(R.id.zidong);
        final   TextView zhuangtai = view.findViewById(R.id.zhuangtai);
        final    TextView xiaopaizhao = view.findViewById(R.id.xiaopaizhao);
        final   TextView xiangce = view.findViewById(R.id.xiangce);
        zidong.setText(a1);
        zhuangtai.setText(a2);
        xiaopaizhao.setText(a3);
        xiangce.setText(a4);
        state3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        zidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = zidong.getText().toString();
                word_item.setText(s1+s2+s3+s4);
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        zhuangtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s2 = zhuangtai.getText().toString();
                word_item.setText(s1+s2+s3+s4);
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        xiaopaizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s3 = xiaopaizhao.getText().toString();
                word_item.setText(s1+s2+s3+s4);
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s4 = xiangce.getText().toString();
                word_item.setText(s1+s2+s3+s4);
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });


    }
    //验证助记词
    public void Mnemonic_words(final String auxiliaries){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.mnemonic_words(auxiliaries, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Verify_mnemonic_words verifyMnemonicWords = JSON.parseObject(result,Define.Verify_mnemonic_words.class);
                            showToast(verifyMnemonicWords.message);
                            Define.Verify_mnemonic_words_item verifyMnemonicWordsItem = verifyMnemonicWords.data;

                            Intent intent = new Intent(BackupsActivity.this,LookActivity.class);
                            intent.putExtra("p_key",verifyMnemonicWordsItem.p_key);
                            startActivity(intent);

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
