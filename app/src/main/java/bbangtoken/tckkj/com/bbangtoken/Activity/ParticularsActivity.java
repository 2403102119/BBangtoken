package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.DetailsAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.ParticularsAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.TestAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*兌換記錄頁面
*@Author:李迪迦
*@Date:
*/
public class ParticularsActivity extends BaseActivity {

    private RecyclerView particulars;
    LinearLayoutManager layoutManager;
    ParticularsAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    private Double rate;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_particulars);
        setTopTitle("兌換記錄");
        particulars = findViewById(R.id.particulars);
    }

    @Override
    protected void initEvent() {
        conversion_item();
    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(ParticularsActivity.this);
        particulars.setLayoutManager(layoutManager);
        adapter = new ParticularsAdapter (ParticularsActivity.this, list);
        particulars.setAdapter(adapter);
        adapter.setOnItemClickListener(new ParticularsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
//                Intent intent = new Intent(ParticularsActivity.this, BBCdetailActivity.class);
//                startActivity(intent);
            }



        });


    }
    // 兑换记录
    public void conversion_item(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.conversion_item(new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
//                            Define.RecordModel recordModel = JSON.parseObject(result,Define.RecordModel.class);
//                            showToast(recordModel.message);
//                            Define.recordmodel recordmodel = recordModel.data;
//                            rate = recordmodel.rate;
//                            Define.record record1 = recordmodel.record1;
//                            list.clear();
//
//                            Map<String,Object> map = new HashMap<>();
//                            map.put("currency_name",record1.currency_name);//币名
//                            map.put("amount",record1.amount);//兑换金额
//                            map.put("exchange_amount",record1.exchange_amount);//兑换了多少金额
//                            map.put("ctime",record1.ctime);//兑换时间
//                            list.add(map);
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

