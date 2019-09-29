package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.SiteAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*钱包地址页面
*@Author:李迪迦
*@Date:
*/
public class SiteActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView site;
    LinearLayoutManager layoutManager;
    SiteAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    private String key;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_site);
        setTopTitle("錢包地址");
        rightIcon.setVisibility(View.VISIBLE);
        rightIcon.setImageResource(R.mipmap.market_add);
        site = findViewById(R.id.site);
    }

    @Override
    protected void initEvent() {
        rightIcon.setOnClickListener(this);
    }

    @Override
    protected void initData() {


        layoutManager = new LinearLayoutManager(SiteActivity.this);
        site.setLayoutManager(layoutManager);
        adapter = new SiteAdapter(SiteActivity.this, list);
        site.setAdapter(adapter);
        adapter.setOnItemClickListener(new SiteAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                key = list.get(firstPosition).get("wallet_address").toString();
                Intent intent = new Intent();
                intent.putExtra("key",key);
                setResult(RESULT_OK,intent);
                finish();

            }



        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rightIcon:
                Intent intent = new Intent(SiteActivity.this,AddSiteActivity.class);
                startActivity(intent);
                break;
        }
    }
    // 钱包地址
    public void site(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.site( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.site site = JSON.parseObject(result,Define.site.class);
//                            showToast(site.message);
                            list.clear();
                            List<Define.site_item> siteItems = site.data;
                            for (int i = 0; i <siteItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("currency_name",siteItems.get(i).currency_name);
                                map.put("wallet_address",siteItems.get(i).wallet_address);
                                map.put("currency_icon",siteItems.get(i).currency_icon);
                                map.put("wallet_id",siteItems.get(i).wallet_id);
                                map.put("bag_name",siteItems.get(i).bag_name);
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
        site();
    }
}
