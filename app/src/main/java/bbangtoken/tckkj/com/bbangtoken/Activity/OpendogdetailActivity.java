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

import bbangtoken.tckkj.com.bbangtoken.Adapter.OpendetailAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SortAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*开启智能狗详细记录页面
*@Author:李迪迦
*@Date:
*/
public class OpendogdetailActivity extends BaseActivity {

    private RecyclerView open_detail;
    LinearLayoutManager layoutManager;
    OpendetailAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_opendogdetail);
        setTopTitle("啟動記錄");
        open_detail = findViewById(R.id.open_detail);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(OpendogdetailActivity.this);
        open_detail.setLayoutManager(layoutManager);
        adapter = new OpendetailAdapter(OpendogdetailActivity.this, list);
        open_detail.setAdapter(adapter);
        adapter.setOnItemClickListener(new OpendetailAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }



        });

    }
    //智能狗启动记录
    public void open_item(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.start_the_recording( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Start_dog startDog = JSON.parseObject(result,Define.Start_dog.class);
                            showToast(startDog.message);
                            List<Define.Start_dog_item> startDogItems = startDog.data;
                            list.clear();
                            for (int i = 0; i <startDogItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("append_amount",startDogItems.get(i).append_amount);
                                map.put("currency_name",startDogItems.get(i).currency_name);
                                map.put("currency_icon",startDogItems.get(i).currency_icon);
                                map.put("ctime",startDogItems.get(i).ctime);
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
        open_item();
    }
}
