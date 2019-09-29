package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.PopupAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SiteAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import okhttp3.Call;

/*
*添加钱包二级页面
*@Author:李迪迦
*@Date:
*/
public class AddSiteActivity extends BaseActivity implements View.OnClickListener {

    private PopupWindow state2;
    private RelativeLayout state3;
    private LinearLayout state_xxx;
    private ImageView select_the_currency;
    private ImageView im_scan;
    PopupAdapter adapter;
    private int REQUEST_CODE = 1;
    private TextView tv_name;
    private Button bt_conterl;
    List<Map<String,Object>> list=new ArrayList<>();
    private String id;
    private ClearEditText ce_site,ce_password,ce_name;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_add_site);
        setTopTitle("添加錢包地址");
        select_the_currency = findViewById(R.id.select_the_currency);
        im_scan = findViewById(R.id.im_scan);
        tv_name = findViewById(R.id.tv_name);
        bt_conterl = findViewById(R.id.bt_conterl);
        ce_site = findViewById(R.id.ce_site);
        ce_password = findViewById(R.id.ce_password);
        ce_name = findViewById(R.id.ce_name);
    }

    @Override
    protected void initEvent() {
        select_the_currency.setOnClickListener(this);
        im_scan.setOnClickListener(this);
        bt_conterl.setOnClickListener(this);
        ce_password.setOnClickListener(this);
    }

    @Override
    protected void initData() {



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_the_currency:
                state();
                state_xxx.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                state2.showAtLocation(v, Gravity.BOTTOM,0,0);
                break;
            case R.id.im_scan:
                Intent intent = new Intent(AddSiteActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.bt_conterl:
                add_money(id,ce_site.getText().toString(),ce_password.getText().toString(),ce_name.getText().toString());
                break;
        }
    }
    public  void  state(){
        state2=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.currency,null);
        state_xxx= view.findViewById(R.id.state_xx);
        state2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        state2.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        state2.setBackgroundDrawable(new BitmapDrawable());
        state2.setFocusable(true);
        state2.setOutsideTouchable(true);
        state2.setContentView(view);
        state3=view.findViewById(R.id.state_xxx);
        TextView quxiao = view.findViewById(R.id.quxiao);
        TextView zidong = view.findViewById(R.id.zidong);
        RecyclerView recycle = view.findViewById(R.id.recycle);


        LinearLayoutManager  layoutManager = new LinearLayoutManager(AddSiteActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new PopupAdapter (AddSiteActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new PopupAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                tv_name.setText(list.get(firstPosition).get("currency_name").toString());
                id = list.get(firstPosition).get("currency_id").toString();
                state2.dismiss();
                state_xxx.clearAnimation();
            }



        });



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


    }
    // 添加钱包地址
    public void add_money(final String currency_id,final String address,final String t_password,final String bag_name){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.add_money(currency_id,address,t_password ,bag_name,new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Sort sort = JSON.parseObject(result,Define.Sort.class);
                            showToast(sort.message);
                            finish();

                        }

                        @Override
                        public void onFail(String response) {
                            Define.Sort sort = JSON.parseObject(response,Define.Sort.class);
                            showToast(sort.message);
                        }

                        @Override
                        public void onError(Call call, Exception exception) {

                        }

                        @Override
                        public void onFinish(String response) {
                            Define.Sort sort = JSON.parseObject(response,Define.Sort.class);
                            showToast(sort.message);
                        }
                    });
                }
            });
        }else{
        }
    }

    //币种列表
    public void curr(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.curr(new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            List<Define.Sort_item> sort = JSON.parseObject(result,Define.Sort.class).data;
                            list.clear();
                            for (int i = 0; i <sort.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("currency_id",sort.get(i).currency_id);
                                map.put("currency_name",sort.get(i).currency_name);
                                map.put("currency_icon",sort.get(i).currency_icon);
                                list.add(map);
                            }
//                            adapter.notifyDataSetChanged();
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
        curr();
    }
}
