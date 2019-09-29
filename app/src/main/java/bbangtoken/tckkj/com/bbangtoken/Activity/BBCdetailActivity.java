package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.DetailsAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.TestAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*BBC详情页面
*@Author:李迪迦
*@Date:
*/
public class BBCdetailActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    DetailsAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    private ImageView duihuan;
    private TextView tv_rool_out,shift_to;
    private String currency_id,currency_name;
    private double rate;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_bbcdetail);
        setTopTitle(currency_name);
        recycle = findViewById(R.id.recycle);
        duihuan = findViewById(R.id.duihuan);
        tv_rool_out = findViewById(R.id.tv_rool_out);
        shift_to = findViewById(R.id.shift_to);

    }

    @Override
    protected void initEvent() {
        duihuan.setOnClickListener(this);
        tv_rool_out.setOnClickListener(this);
        shift_to.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        currency_id = getIntent().getStringExtra("currency_id");
        currency_name = getIntent().getStringExtra("currency_name");
        layoutManager = new LinearLayoutManager(BBCdetailActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new DetailsAdapter(BBCdetailActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new TestAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
//                Intent intent = new Intent(getContext(), BBCdetailActivity.class);
//                startActivity(intent);
            }

            @Override
            public void checkListener(int firstPosition, int position, boolean isCheck, String oid) {

            }

            @Override
            public void isOpen(int position) {

            }

            @Override
            public void OnItemChildClickListener(int firstPosition, int childPosition, String oid) {

            }

        });

        record(currency_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.duihuan:
                update(currency_id);
                Intent intent = new Intent(BBCdetailActivity.this,ExchangeActivity.class);
                intent.putExtra("rate",rate);
                intent.putExtra("currency_id",currency_id);
                startActivity(intent);
                break;
            case R.id.tv_rool_out:
                update(currency_id);
                Intent intent1 = new Intent(BBCdetailActivity.this,RoolActivity.class);
                intent1.putExtra("currency_id",currency_id);
                startActivity(intent1);
                break;
            case R.id.shift_to:
                update(currency_id);
                Intent intent2 = new Intent(BBCdetailActivity.this,ShaittoActivity.class);
                intent2.putExtra("currency_name",currency_name);
                intent2.putExtra("currency_id",currency_id);
                startActivity(intent2);
                break;

        }
    }
    // 币种转入转出记录
    public void record(final String currency_id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.record(currency_id, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.ShiftModel shiftModel = JSON.parseObject(result,Define.ShiftModel.class);
                            Define.shiftlistmodel shiftlistmodel = shiftModel.data;
                            rate = shiftlistmodel.rate;

                            List<Define.shift> shiftlistmodels = shiftlistmodel.result;

                            list.clear();
                            for (int i = 0; i <shiftlistmodels.size() ; i++) {

                                Map<String,Object> map = new HashMap<>();
                                map.put("amount",shiftlistmodels.get(i).amount);//转账金额
                                map.put("transfer_type",shiftlistmodels.get(i).transfer_type);//	1转入2转出
                                map.put("ctime",shiftlistmodels.get(i).ctime);//交易时间
                                map.put("currency_name",shiftlistmodels.get(i).currency_name);//币名
                                map.put("recive_address",shiftlistmodels.get(i).recive_address);//收款地址
                                map.put("payment_address",shiftlistmodels.get(i).payment_address);//发款地址
                                map.put("state",shiftlistmodels.get(i).state);//转账状态
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
    // 更新钱包地址
    public void update(final String currency_id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.update(currency_id, new MApiResultCallback() {
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

    @Override
    protected void onResume() {
        super.onResume();

    }
}
