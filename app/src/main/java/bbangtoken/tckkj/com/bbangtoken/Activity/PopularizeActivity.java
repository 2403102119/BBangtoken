package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.GlideUtil.GlideCatchUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*推广邀请界面
*@Author:李迪迦
*@Date:
*/
public class PopularizeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ge,im_touxiang,im_code;
    private String imageUrl;
    private TextView tv_name,tv_yonghu,tv_code,words,iv_index_qiehuan;
    private String imurl,imurl_title;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_popularize);
        baseTop.setVisibility(View.GONE);
        ge = findViewById(R.id.ge);
        im_touxiang = findViewById(R.id.im_touxiang);
        tv_name = findViewById(R.id.tv_name);
        tv_yonghu = findViewById(R.id.tv_yonghu);
        im_code = findViewById(R.id.im_code);
        tv_code = findViewById(R.id.tv_code);
        words = findViewById(R.id.words);
        iv_index_qiehuan = findViewById(R.id.iv_index_qiehuan);

    }

    @Override
    protected void initEvent() {
        ge.setOnClickListener(this);
        iv_index_qiehuan.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        extension();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ge:
                finish();
                break;
            case R.id.iv_index_qiehuan:
                ClipboardManager copy = (ClipboardManager) PopularizeActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(words.getText().toString());
                showToast("复制成功");
                break;
        }
    }
    //推广邀请
    public void extension(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.extension( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            //清除内存缓存
                            GlideCatchUtil.getInstance().clearCacheMemory();
                            // Glide自带删除磁盘缓存方法在线程中执行
                            GlideCatchUtil.getInstance().clearCacheDiskSelf();
                            // 删除文件夹方法在主线程执行
                            GlideCatchUtil.getInstance().cleanCatchDisk();
                            Define.generalize_item generalize = JSON.parseObject(result,Define.generalize.class).data;
                            imurl = generalize.url;
                            imurl_title = generalize.u_header;
                            Glide.with(PopularizeActivity.this).load(imurl).into(im_code);
                            Glide.with(PopularizeActivity.this).load(imurl_title).into(im_touxiang);
                            tv_yonghu.setText(generalize.u_account);
                            tv_name.setText(generalize.nick_name);
                            tv_code.setText("邀請碼: "+generalize.code);
                            words.setText("鏈接: "+generalize.qrcode);

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
    }
}
