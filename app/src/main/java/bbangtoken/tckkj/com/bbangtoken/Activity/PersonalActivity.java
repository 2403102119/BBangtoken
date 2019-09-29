package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.just.agentweb.LogUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import bbangtoken.tckkj.com.bbangtoken.App;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.Base64;
import bbangtoken.tckkj.com.bbangtoken.Util.FileUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.View.CircleImageView;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import bbangtoken.tckkj.com.bbangtoken.View.PickPicDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
*个人信息界面
*@Author:李迪迦
*@Date:
*/
public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    private TextView replace_the_picture,tv_zhanghao;
    private Button bt_enter;
    private PickPicDialog pickPicDialog;
    private LinearLayout ll_nickname;
    private String imageUrl;
    private CircleImageView iv_my_head;
    private EditText tv_nicm;
    private String path;
    private String  header;
    private HashMap<String, String> params;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_personal);
        setTopTitle("個人信息");
        replace_the_picture = findViewById(R.id.replace_the_picture);
        bt_enter = findViewById(R.id.bt_enter);
        ll_nickname = findViewById(R.id.ll_nickname);
        tv_nicm = findViewById(R.id.tv_nicm);
        tv_zhanghao = findViewById(R.id.tv_zhanghao);
        iv_my_head = findViewById(R.id.iv_my_head);
    }

    @Override
    protected void initEvent() {
        replace_the_picture.setOnClickListener(this);
        bt_enter.setOnClickListener(this);
        ll_nickname.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        invite();
        pickPicDialog = new PickPicDialog(PersonalActivity.this);
        pickPicDialog.setOnActionClickListener(new PickPicDialog.OnPicListener() {
            @Override
            public void onTake() {
                PictureSelector.create(PersonalActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .previewImage(true)
                        .compress(true)
                        .circleDimmedLayer(true)// 是否圆形裁剪
                        .enableCrop(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }

            @Override
            public void onAlbum() {
                PictureSelector.create(PersonalActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .isCamera(false)
                        .previewImage(true)
                        .compress(true)
                        .enableCrop(true)
                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                        .circleDimmedLayer(true)// 是否圆形裁剪
                        .showCropGrid(false)
                        .showCropFrame(false)
                        .scaleEnabled(true)
                        .rotateEnabled(true)
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
                if (mediaList.size() > 0) {
                     path = mediaList.get(0).getCompressPath();
                     try {

                         Glide.with(this)
                                 .load(path)
                                 .into(iv_my_head);
                             File file = new File(path);
                          Log.i("11","文件的相对路径："+file.getPath());
                          Log.i("22","文件的绝对路径："+file.getAbsolutePath());
                          Log.i("22","文件可以读取：" + file.canRead());

                         uploading(path);
//                         uploding();
                     }catch (Exception e){
                         e.printStackTrace();
                     }



                }
                break;
        }
    }

    //当按钮点击时,执行使用OKhttp上传图片到服务器(http://blog.csdn.net/tangxl2008008/article/details/51777355)
    //注意:有时候上传图片失败,是服务器规定还要上传一个Key,如果开发中关于网络这一块出现问题,就多和面试官沟通沟通
    public void uploading(String path) {

        params = new HashMap<>();
        params.put("token", App.token+"");
        params.put("user_id",App.user_id+"");

        //图片上传接口地址
        String url = "http://qb.tangchaoke.com/api/user_header_info";
        //创建上传文件对象
        File file = new File(path);

        //创建RequestBody封装参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        //创建MultipartBody,给RequestBody进行设置
        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("u_header", file.getName(), fileBody);


        Set set = params.keySet();
        for (Object key :set) {
            multipartBody.addFormDataPart((String) key, params.get(key));
        }
        //创建Request
        final Request request = new Request.Builder()
                .url(url)
                .post(multipartBody.build())
                .build();

        //创建okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        //上传完图片,得到服务器反馈数据
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ff", "uploadMultiFile() e=" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("ff", "uploadMultiFile() response=" + response.body().string());
                JSONObject object = JSON.parseObject(response.body().string());
                                Log.i("111","data = "+object);

//                String data = object.data;
//                Log.i("111","data = "+data);
//                JSONObject jb = JSONObject.parseObject(response.body().string());
//                String data = jb.getString("data");
//                Glide.with(PersonalActivity.this)
//                        .load(data)
//                        .into(iv_my_head);

            }
        });


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.replace_the_picture:
                pickPicDialog.show();
                break;
            case R.id.bt_enter:

                user_info(tv_nicm.getText().toString().trim(),header);
                break;
            case R.id.ll_nickname:
                break;
        }
    }
    // 修改个人信息
    public void user_info(final String nick_name,final String u_header){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.user_info(nick_name,u_header, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {

                            finish();

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
    // 修改头像
    public void uploding(final String u_header){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.uploding(u_header, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {


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
    //推广邀请
    public void invite() {
        if (NetUtil.isNetWorking(getApplicationContext())) {
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.invite(new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Invite invite = JSON.parseObject(result, Define.Invite.class);
//                            showToast(invite.message);
                            Define.invite_item inviteItem = invite.data;
                            imageUrl = inviteItem.u_header;
                            Glide.with(PersonalActivity.this).applyDefaultRequestOptions(new RequestOptions()
                                    .error(R.mipmap.touxiang)
                                    .placeholder(R.mipmap.touxiang))
                                    .load(imageUrl)
                                    .into(iv_my_head);
                            tv_nicm.setText(inviteItem.nick_name);
                            tv_zhanghao.setText(inviteItem.u_account);

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
        } else {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//
    }
}
