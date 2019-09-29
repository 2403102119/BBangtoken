package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.LocalManageUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    CheckBox chinese,english;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_language);
        setTopTitle("語言版本");
        chinese = findViewById(R.id.chinese);
        english = findViewById(R.id.english);
        Log.i("111", "getSystemLocale: " + Locale.getDefault().getLanguage());
        Log.i("111", "getSystemLocale: " + Locale.getDefault().toString());
        if ("zh".equals(Locale.getDefault().getLanguage())){
            chinese.setChecked(true);
        }else {
            english.setChecked(true);
        }
        setClick();
    }

    @Override
    protected void initEvent() {
        chinese.setOnClickListener(this);
        english.setOnClickListener(this);
    }
    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(this, select);

        //Message_set_itemActivity.reStart(this);
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chinese:
                chinese.setChecked(true);
                english.setChecked(false);
                language("1");
                break;
            case R.id.english:
                english.setChecked(true);
                chinese.setChecked(false);
                language("2");
                break;
        }
    }
    private void setClick() {

        //简体中文
        findViewById(R.id.chinese).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage(1);
            }
        });

        //English
        findViewById(R.id.english).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage(3);
            }
        });
    }
    //语言切换
    public void language(final String type){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.language(type,new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
//                            Define.Sort sort = JSON.parseObject(result,Define.Sort.class);
//                            showToast(sort.message);
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
