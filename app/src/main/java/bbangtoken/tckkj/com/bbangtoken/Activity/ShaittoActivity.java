package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
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
*转入界面
*@Author:李迪迦
*@Date:
*/
public class ShaittoActivity extends BaseActivity implements View.OnClickListener {

    private TextView set_monmey,tv_name,tv_address;
    private LinearLayout modify_item,modify_layout;
    PopupWindow modify_popu_item;
    private String currency_id,currency_name;
    private ImageView im_twocode;
    private  String imurl;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_shaitto);
        setTopTitle("轉入");
        set_monmey = findViewById(R.id.set_monmey);
        tv_name = findViewById(R.id.tv_name);
        im_twocode = findViewById(R.id.im_twocode);
        tv_address = findViewById(R.id.tv_address);
    }

    @Override
    protected void initEvent() {
        set_monmey.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        currency_id = getIntent().getStringExtra("currency_id");
        currency_name = getIntent().getStringExtra("currency_name");
        shift_to(currency_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_monmey:
                set_money();
                modify_item.startAnimation(AnimationUtils.loadAnimation(ShaittoActivity.this,R.anim.activity_translate_in));
                modify_popu_item.showAtLocation(v, Gravity.CENTER,0,0);
                break;
        }
    }
    public  void set_money(){
        modify_popu_item=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.refundlayout,null);
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
        modify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
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
                String bea = be_as.getText().toString();
                qrcode(bea);
                modify_popu_item.dismiss();
                modify_item.clearAnimation();

            }
        });
    }

    // 二维码
    public void shift_to(final String currency_id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.shift_to(currency_id, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                             //清除内存缓存
                            GlideCatchUtil.getInstance().clearCacheMemory();
                            // Glide自带删除磁盘缓存方法在线程中执行
                            GlideCatchUtil.getInstance().clearCacheDiskSelf();
                            // 删除文件夹方法在主线程执行
                            GlideCatchUtil.getInstance().cleanCatchDisk();

                            Define.Twocode twocode = JSON.parseObject(result,Define.Twocode.class);
                            List<Define.Twocode_item> twocodeItems = twocode.data;
                            for (int i = 0; i <twocodeItems.size() ; i++) {
                                imurl = twocodeItems.get(i).url;
                                tv_name.setText(currency_name + "("+twocodeItems.get(i).balance+")");
                                tv_address.setText(twocodeItems.get(i).address);
                            }

                            Glide.with(ShaittoActivity.this).load(imurl).into(im_twocode);

//                            Glide.with(ShaittoActivity.this).applyDefaultRequestOptions(new RequestOptions()
//                                    .error(R.mipmap.touxiang)
//                                    .placeholder(R.mipmap.touxiang))
//                                    .load(imurl)
//                                    .into(im_twocode);

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
    // 设置收款金额
    public void qrcode(final String amount){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.qrcode( amount,new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            shift_to(currency_id);
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
