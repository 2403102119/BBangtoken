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
import bbangtoken.tckkj.com.bbangtoken.Adapter.SortAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*智能收益界面
*@Author:李迪迦
*@Date:
*/
public class CapacityActivity extends BaseActivity {

    private RecyclerView capacity_re;
    LinearLayoutManager layoutManager;
    CapacityAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_capacity);
        setTopTitle("智能收益");
        capacity_re = findViewById(R.id.capacity_re);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(CapacityActivity.this);
        capacity_re.setLayoutManager(layoutManager);
        adapter = new CapacityAdapter(CapacityActivity.this, list);
        capacity_re.setAdapter(adapter);
        adapter.setOnItemClickListener(new CapacityAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }



        });


    }
    // 智能收益
    public void capacity_earnings(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.capacity_earnings(new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Smart_earnings smartEarnings = JSON.parseObject(result,Define.Smart_earnings.class);
//                            showToast(smartEarnings.message);
                            list.clear();
                            List<Define.Smart_earnings_item> smartEarningsItems = smartEarnings.data;
                            for (int i = 0; i <smartEarningsItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("currency_name",smartEarningsItems.get(i).currency_name);
                                map.put("currency_icon",smartEarningsItems.get(i).currency_icon);
                                map.put("ctime",smartEarningsItems.get(i).ctime);
                                map.put("amount",smartEarningsItems.get(i).amount);
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
        capacity_earnings();
    }
}
