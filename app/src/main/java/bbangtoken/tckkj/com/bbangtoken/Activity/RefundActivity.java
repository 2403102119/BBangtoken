package bbangtoken.tckkj.com.bbangtoken.Activity;

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
import android.widget.EditText;
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
*还款页面
*@Author:李迪迦
*@Date:
*/
public class RefundActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout modify_item,modify_layout,ll_selec;
    PopupWindow modify_popu_item;
    private Button confirm_the_payment;
    private ClearEditText ce_refound;
    private LinearLayout state_xxx;
    PopupWindow state2;
    PopupAdapter adapter;
    private RelativeLayout state3;
    List<Map<String,Object>> list=new ArrayList<>();
    private  String imurl;
    private ImageView im_icon;
    private String id;
    private TextView tv_name;



    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_refund);
        setTopTitle("還款");
        confirm_the_payment = findViewById(R.id.confirm_the_payment);
        ce_refound = findViewById(R.id.ce_refound);
        im_icon = findViewById(R.id.im_icon);
        tv_name = findViewById(R.id.tv_name);
        ll_selec = findViewById(R.id.ll_selec);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        confirm_the_payment.setOnClickListener(this);
        ll_selec.setOnClickListener(this);
    }
    public  void set_money(){
        modify_popu_item=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.set_money,null);
        modify_item= view.findViewById(R.id.modify_item);
        modify_popu_item.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setBackgroundDrawable(new BitmapDrawable());
        modify_popu_item.setFocusable(true);
        modify_popu_item.setOutsideTouchable(true);
        modify_popu_item.setContentView(view);
        modify_layout= view.findViewById(R.id.modify_layout);
        TextView modify_cancel=view.findViewById(R.id.modify_cancel);
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


        LinearLayoutManager layoutManager = new LinearLayoutManager(RefundActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new PopupAdapter(RefundActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new PopupAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                tv_name.setText(list.get(firstPosition).get("currency_name").toString());
                id = list.get(firstPosition).get("currency_id").toString();
                imurl = list.get(firstPosition).get("currency_icon").toString();
                Glide.with(RefundActivity.this).load(imurl).into(im_icon);                state2.dismiss();
                state_xxx.clearAnimation();
            }



        });



        state3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr();
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr();
                state2.dismiss();
                state_xxx.clearAnimation();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_the_payment:
                refound(id,ce_refound.getText().toString(),v);

                break;
            case R.id.ll_selec:
                state();
                state_xxx.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                state2.showAtLocation(v, Gravity.BOTTOM,0,0);
                break;
        }
    }
    // 还款
    public void refound(final String currency_id,final String repay_amount,final View view){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.refound_money(currency_id,repay_amount, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Sort  define = JSON.parseObject(result,Define.Sort.class);
                            showToast(define.message);
                            set_money();
                            modify_item.startAnimation(AnimationUtils.loadAnimation(RefundActivity.this,R.anim.activity_translate_in));
                            modify_popu_item.showAtLocation(view, Gravity.CENTER,0,0);

                        }

                        @Override
                        public void onFail(String response) {
                            Define.Sort  define = JSON.parseObject(response,Define.Sort.class);
                            showToast(define.message);
                        }

                        @Override
                        public void onError(Call call, Exception exception) {

                        }

                        @Override
                        public void onFinish(String response) {
                            Define.Sort  define = JSON.parseObject(response,Define.Sort.class);
                            showToast(define.message);
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
