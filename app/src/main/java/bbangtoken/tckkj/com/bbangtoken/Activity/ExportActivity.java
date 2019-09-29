package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import okhttp3.Call;
/*
*导出私钥界面
*@Author:李迪迦
*@Date:
*/
public class ExportActivity extends BaseActivity implements View.OnClickListener {

    private Button bt_enter;
    private ClearEditText ce_iput;
    private String s;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_export);
        setTopTitle("導出私鑰");
        bt_enter = findViewById(R.id.bt_enter);
        ce_iput = findViewById(R.id.ce_iput);
    }

    @Override
    protected void initEvent() {
        bt_enter.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    // 导出私钥
    public void derive(final String t_password){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.derive(t_password, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Verify_mnemonic_words verifyMnemonicWords = JSON.parseObject(result,Define.Verify_mnemonic_words.class);
                            Define.Verify_mnemonic_words_item verifyMnemonicWordsItem = verifyMnemonicWords.data;
                            Intent intent = new Intent(ExportActivity.this,LookActivity.class);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_enter:
                s = ce_iput.getText().toString().trim();
                derive(s);
                break;
        }
    }
}
