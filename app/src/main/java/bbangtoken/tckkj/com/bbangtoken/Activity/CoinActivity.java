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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.CoinAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SortAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ActionDialog;
import okhttp3.Call;

/*
*幣融页面
*@Author:李迪迦
*@Date:2019.6.27
*/
public class CoinActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout modify_item,modify_layout;
    private RecyclerView coin;
    PopupWindow modify_popu_item;
    private TextView tv_jiade,borrow_money,refund_money;
    CoinAdapter adapter;
    LinearLayoutManager layoutManager;
    List<Map<String,Object>> list=new ArrayList<>();
    ActionDialog coindialog;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_coin);
        setTopTitle("幣融");
        tv_jiade = findViewById(R.id.tv_jiade);
        borrow_money = findViewById(R.id.borrow_money);
        refund_money = findViewById(R.id.refund_money);

        coindialog = new ActionDialog(CoinActivity.this,"1、借幣週期為1、3、6、9、12個月，不同的借幣週期，利率不同，以實際借幣時利率為準；2、每發起一次借幣申請，系統都會單獨進行核算；","确定",false);
        coindialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                //取消

            }

            @Override
            public void onRightClick() {

                 coindialog.dismiss();

            }
        });
        coindialog.show();


    }

    @Override
    protected void initEvent() {
        coin = findViewById(R.id.coin);



    }

    @Override
    protected void initData() {

        borrow_money.setOnClickListener(this);
        tv_jiade.setOnClickListener(this);
        refund_money.setOnClickListener(this);



        layoutManager = new LinearLayoutManager(CoinActivity.this);
        coin.setLayoutManager(layoutManager);
        adapter = new CoinAdapter(CoinActivity.this, list);
        coin.setAdapter(adapter);
        adapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }


        });



    }

    public  void coin_hint(){
        modify_popu_item=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.coin_hint,null);
        modify_item= view.findViewById(R.id.modify_item);
        modify_popu_item.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setBackgroundDrawable(new BitmapDrawable());
        modify_popu_item.setFocusable(true);
        modify_popu_item.setOutsideTouchable(true);
        modify_popu_item.setContentView(view);

        modify_layout= view.findViewById(R.id.modify_layout);
        final TextView be_as=view.findViewById(R.id.be_as);
        TextView modify_yes=view.findViewById(R.id.modify_yes);
        modify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
            }
        });

        modify_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_jiade:
                coin_hint();
                modify_item.startAnimation(AnimationUtils.loadAnimation(CoinActivity.this, R.anim.activity_translate_in));
                modify_popu_item.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case R.id.borrow_money:
                Intent intent = new Intent(CoinActivity.this,BorrowActivity.class);
                startActivity(intent);
                break;
            case R.id.refund_money:
                Intent intent1 = new Intent(CoinActivity.this,RefundActivity.class);
                startActivity(intent1);
                break;

        }
    }
    // 币融列表
    public void coin_melts(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.coin_melts(new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Coin_melts coinMelts = JSON.parseObject(result,Define.Coin_melts.class);
//                            showToast(coinMelts.message);
                            Define.coin_meltsitem coinMeltsitem = coinMelts.data;

                            list.clear();
                            List<Define.coin_list> coinLists = coinMeltsitem.fuse_list;
                            for (int i = 0; i <coinLists.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("user_id",coinLists.get(i).user_id);
                                map.put("currency_id",coinLists.get(i).currency_id);
                                map.put("may_borrow_amount",coinLists.get(i).may_borrow_amount);
                                map.put("already_borrow_amount",coinLists.get(i).already_borrow_amount);
                                map.put("already_repay_amount",coinLists.get(i).already_repay_amount);
                                map.put("wait_repay_amount",coinLists.get(i).wait_repay_amount);
                                map.put("expire_time",coinLists.get(i).expire_time);
                                map.put("rate",coinLists.get(i).rate);
                                map.put("ctime",coinLists.get(i).ctime);
                                map.put("currency_name",coinLists.get(i).currency_name);
                                map.put("currency_icon",coinLists.get(i).currency_icon);
                                list.add(map);

                            }
                            adapter.notifyDataSetChanged();


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
        coin_melts();
    }
}
