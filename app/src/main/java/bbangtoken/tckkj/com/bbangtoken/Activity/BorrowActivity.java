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

import bbangtoken.tckkj.com.bbangtoken.Adapter.CoinAdapter;
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
*借款页面
*@Author:李迪迦
*@Date:
*/
public class BorrowActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_borr,state_xxx;
    PopupWindow state2;
    PopupAdapter adapter;
    CycleAdapter cycleAdapter;
    private RelativeLayout state3;
    private Button bt_conterl;
    private TextView tv_choic,tv_zhouqi,tv_name;
    private ClearEditText ce_borrow;
    private LinearLayout ll_choic;
    List<Map<String,Object>> list=new ArrayList<>();
    List<Map<String,Object>> list1=new ArrayList<>();
    private String id,id1;
    private ImageView im_icon;
    private  String imurl;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_borrow);
        setTopTitle("借款");
    }

    @Override
    protected void initEvent() {
        ll_borr = findViewById(R.id.ll_borr);
        bt_conterl = findViewById(R.id.bt_conterl);
        tv_choic = findViewById(R.id.tv_choic);
        ce_borrow = findViewById(R.id.ce_borrow);
        tv_zhouqi = findViewById(R.id.tv_zhouqi);
        ll_choic = findViewById(R.id.ll_choic);
        tv_name = findViewById(R.id.tv_name);
        im_icon = findViewById(R.id.im_icon);
    }

    @Override
    protected void initData() {
        ll_borr.setOnClickListener(this);
        bt_conterl.setOnClickListener(this);
        ll_choic.setOnClickListener(this);
        curr();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ll_borr:
                cycle();
                state1();
                state_xxx.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                state2.showAtLocation(v, Gravity.BOTTOM,0,0);
                break;
            case R.id.bt_conterl:
                //TODO   这里做非空判断
                String c = tv_choic.getText().toString();
                String ce_borrow1 = ce_borrow.getText().toString();
                borrow(id,ce_borrow1,id1);
                break;
            case R.id.ll_choic:
                state();
                state_xxx.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                state2.showAtLocation(v, Gravity.BOTTOM,0,0);
                break;
        }
    }

    public  void  state(){
        state2=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.coin_no,null);
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


        LinearLayoutManager  layoutManager = new LinearLayoutManager(BorrowActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new PopupAdapter(BorrowActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new PopupAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                tv_name.setText(list.get(firstPosition).get("currency_name").toString());
                id = list.get(firstPosition).get("currency_id").toString();
                imurl = list.get(firstPosition).get("currency_icon").toString();
                Glide.with(BorrowActivity.this).load(imurl).into(im_icon);
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
    public  void  state1(){
        state2=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.coin_no,null);
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
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("選擇借款週期");
        LinearLayoutManager  layoutManager = new LinearLayoutManager(BorrowActivity.this);
        recycle.setLayoutManager(layoutManager);
        cycleAdapter = new CycleAdapter(BorrowActivity.this, list1);
        recycle.setAdapter(cycleAdapter);
        cycleAdapter.setOnItemClickListener(new CycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                id1 = list1.get(firstPosition).get("id").toString();
                tv_zhouqi.setText(list1.get(firstPosition).get("cycle_name").toString());
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


    // 借款
    public void borrow(final String currency_id,final String borrow_amount,final String periodic_id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.borrow_money(currency_id,borrow_amount,periodic_id, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                           Define.Sort sort = JSON.parseObject(result,Define.Sort.class);
                           showToast(sort.message);

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
    //借款周期列表
    public void cycle(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.cycle(new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            List<Define.Cycle_item> cycleItems = JSON.parseObject(result,Define.Cycle.class).data;
                            list1.clear();
                            for (int i = 0; i <cycleItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("id",cycleItems.get(i).id);
                                map.put("cycle",cycleItems.get(i).cycle);
                                map.put("cycle_name",cycleItems.get(i).cycle_name);
                                map.put("interest_rate",cycleItems.get(i).interest_rate);
                                list1.add(map);
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

    }
}
