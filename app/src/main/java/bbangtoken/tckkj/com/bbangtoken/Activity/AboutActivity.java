package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*关于我们页面
*@Author:李迪迦
*@Date:
*/
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout language;
    private ImageView im_logo;
    private String imageUrl;
    private TextView tv_number,tv_about,tv_number_item;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_about);
        setTopTitle("關於我們");
        language = findViewById(R.id.language);
        im_logo = findViewById(R.id.im_logo);
        tv_number = findViewById(R.id.tv_number);
        tv_about = findViewById(R.id.tv_about);
        tv_number_item = findViewById(R.id.tv_number_item);
    }

    @Override
    protected void initEvent() {
        language.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.language:
                Intent intent = new Intent(AboutActivity.this,WebviewActivity.class);
                intent.putExtra("url","file:///android_asset/jiekuanxieyi.html");
                startActivity(intent);
                break;
        }
    }
    // 关于我们
    public void about(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.about( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.About about = JSON.parseObject(result,Define.About.class);
                            Define.about_item aboutItem = about.data;
                            imageUrl = aboutItem.project_logo;
                            Glide.with(AboutActivity.this).load(imageUrl).into(im_logo);
                            tv_number.setText("版本号 V"+aboutItem.version_information);
                            tv_about.setText(aboutItem.about_we);
                            tv_number_item.setText(aboutItem.version_information);

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
    protected void onResume() {
        super.onResume();
        about();
    }
}
