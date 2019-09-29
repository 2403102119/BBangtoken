package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.CapacityAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.GeneralizeAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*推廣收益页面
*@Author:李迪迦
*@Date:
*/
public class GeneralizeActivity extends BaseActivity {

    private RecyclerView generalize_re;
    LinearLayoutManager layoutManager;
    GeneralizeAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_generalize);
        setTopTitle("推廣收益");
        generalize_re = findViewById(R.id.generalize_re);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(GeneralizeActivity.this);
        generalize_re.setLayoutManager(layoutManager);
        adapter = new GeneralizeAdapter(GeneralizeActivity.this, list);
        generalize_re.setAdapter(adapter);
        adapter.setOnItemClickListener(new GeneralizeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }



        });

    }
    //推广收益
    public void generalize_earnings(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.generalize_earnings( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Promotion_earnings promotionEarnings = JSON.parseObject(result,Define.Promotion_earnings.class);
//                            showToast(promotionEarnings.message);
                            list.clear();
                            List<Define.Promotion_earnings_item> promotionEarningsItems = promotionEarnings.data;
                            for (int i = 0; i <promotionEarningsItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("nick_name",promotionEarningsItems.get(i).nick_name);
                                map.put("u_account",promotionEarningsItems.get(i).u_account);
                                map.put("u_header",promotionEarningsItems.get(i).u_header);
                                map.put("ctime",promotionEarningsItems.get(i).ctime);
                                map.put("amount",promotionEarningsItems.get(i).amount);
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
        generalize_earnings();
    }
}
