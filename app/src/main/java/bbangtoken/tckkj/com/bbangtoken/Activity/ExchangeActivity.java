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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.CycleAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.PopupAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import okhttp3.Call;

/*
*BBC兌換頁面
*@Author:李迪迦
*@Date:
*/
public class ExchangeActivity extends BaseActivity implements View.OnClickListener {

    PopupAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    private  String imurl;
    private Button bt_conterl;
    private ImageView select_the_currency;
    private PopupWindow state2;
    private RelativeLayout state3;
    private LinearLayout state_xxx;
    private TextView tv_token,tv_kedui;
    private ClearEditText ce_targrt;
    private float rate;
    private String id,currency_id;
    private int it_da;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_exchange);
        setTopTitle("BBC兌換");
        rightIcon.setImageResource(R.mipmap.xiangqing);
        rightIcon.setVisibility(View.VISIBLE);
        bt_conterl = findViewById(R.id.bt_conterl);
        select_the_currency = findViewById(R.id.select_the_currency);
        tv_token = findViewById(R.id.tv_token);
        ce_targrt = findViewById(R.id.ce_targrt);
        tv_kedui = findViewById(R.id.tv_kedui);


    }

    @Override
    protected void initEvent() {
        bt_conterl.setOnClickListener(this);
        rightIcon.setOnClickListener(this);
        select_the_currency.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        rate = getIntent().getFloatExtra("rate",1);
        currency_id = getIntent().getStringExtra("currency_id");

        curr();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_conterl:
                String y = ce_targrt.getText().toString();
                String x = tv_kedui.getText().toString();
                conversion(id,currency_id,y,x);

                break;
            case R.id.rightIcon:
                Intent intent = new Intent(ExchangeActivity.this,ParticularsActivity.class);
                startActivity(intent);
                break;
            case R.id.select_the_currency:
                state();
                state_xxx.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                state2.showAtLocation(v, Gravity.BOTTOM,0,0);
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


        RecyclerView recycle = view.findViewById(R.id.recycle);


        LinearLayoutManager layoutManager = new LinearLayoutManager(ExchangeActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new PopupAdapter(ExchangeActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new PopupAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                tv_token.setText(list.get(firstPosition).get("currency_name").toString());
                id = list.get(firstPosition).get("currency_id").toString();
                imurl = list.get(firstPosition).get("currency_icon").toString();
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
    //兑换
    public void conversion(final String from_currency_id,final String to_currency_id,final String amount,final String exchange_amount){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.conversion(from_currency_id,to_currency_id,amount,exchange_amount, new MApiResultCallback() {
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


}
