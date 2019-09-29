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
*币种排序界面
*@Author:李迪迦
*@Date:
*/
public class SortActivity extends BaseActivity {

    private RecyclerView sort;
    LinearLayoutManager layoutManager;
    SortAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_sort);
        setTopTitle("自定義排序");
        sort = findViewById(R.id.sort);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(SortActivity.this);
        sort.setLayoutManager(layoutManager);
        adapter = new SortAdapter(SortActivity.this, list);
        sort.setAdapter(adapter);
        adapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                sort(list.get(firstPosition).get("currency_id").toString());
                adapter.notifyDataSetChanged();
            }



        });


    }

    // 币种排序
    public void sort(final String currency_id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.sort(currency_id,new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            sort_list();

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
    // 币种排序
    public void sort_list(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.sort_list( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {

                            Define.Sort sort = JSON.parseObject(result,Define.Sort.class);
                            List<Define.Sort_item> sortItem = sort.data;
                            list.clear();
                            for (int i = 0; i <sortItem.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("currency_id",sortItem.get(i).currency_id);
                                map.put("currency_name",sortItem.get(i).currency_name);
                                map.put("currency_icon",sortItem.get(i).currency_icon);
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
        sort_list();
    }
}
