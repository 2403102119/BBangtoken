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

import bbangtoken.tckkj.com.bbangtoken.Adapter.AddAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.AddnotAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SortAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.TestAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*添加行情页面
*@Author:李迪迦
*@Date:
*/
public class AddActivity extends BaseActivity {
    private RecyclerView add_ry,not_add;
    private LinearLayoutManager layoutManager,layoutManager_not;
    AddAdapter adapter;
    AddnotAdapter addnotAdapter;
    List<Map<String,Object>> list=new ArrayList<>();
    List<Map<String,Object>> list2=new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_add);
        setTopTitle("添加行情");
        add_ry = findViewById(R.id.add_ry);
        not_add = findViewById(R.id.not_add);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(AddActivity.this);
        add_ry.setLayoutManager(layoutManager);
        adapter = new AddAdapter(AddActivity.this, list);
        add_ry.setAdapter(adapter);
        adapter.setOnItemClickListener(new AddAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {


            }



        });

        layoutManager_not = new LinearLayoutManager(AddActivity.this);
        not_add.setLayoutManager(layoutManager_not);
        addnotAdapter = new AddnotAdapter(AddActivity.this, list2);
        not_add.setAdapter(addnotAdapter);
        addnotAdapter.setOnItemClickListener(new AddAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }



        });



    }

    //添加行情
    public void add_quotation(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.add_quotation(new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Market market = JSON.parseObject(result,Define.Market.class);
                            list.clear();
                            List<Define.Market_item> marketItem = market.data;
                            for (int i = 0; i <marketItem.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("currency_id",marketItem.get(i).currency_id);
                                map.put("currency_name",marketItem.get(i).currency_name);
                                map.put("currency_icon",marketItem.get(i).currency_icon);
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
        add_quotation();
    }
}
