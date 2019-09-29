package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import bbangtoken.tckkj.com.bbangtoken.Adapter.SelectAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SiteAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ClearEditText;
import okhttp3.Call;

/*
*搜索界面
*@Author:李迪迦
*@Date:2019.6.27
*/
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private ImageView im_seek,im_back;
    private ClearEditText words;
    SelectAdapter adapter;
    private TextView iv_index_qiehuan;
    private RecyclerView search_recycle;
    LinearLayoutManager layoutManager;
    List<Map<String,Object>> list=new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_search);
        baseTop.setVisibility(View.GONE);
        im_seek = findViewById(R.id.im_seek);
        words = findViewById(R.id.words);
        im_back = findViewById(R.id.im_back);
        search_recycle = findViewById(R.id.search_recycle);
        iv_index_qiehuan = findViewById(R.id.iv_index_qiehuan);

    }

    @Override
    protected void initEvent() {
        im_seek.setOnClickListener(this);
        im_back.setOnClickListener(this);
        iv_index_qiehuan.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        layoutManager = new LinearLayoutManager(SearchActivity.this);
        search_recycle.setLayoutManager(layoutManager);
        adapter = new SelectAdapter(SearchActivity.this, list);
        search_recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new SelectAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(SearchActivity.this, BBCdetailActivity.class);
                intent.putExtra("currency_id",list.get(firstPosition).get("currency_id").toString());
                startActivity(intent);
            }



        });

    }


    // 币种搜索
    public void select_currency(final String currency_name){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.select_currency(currency_name, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {

                            Define.seek seek = JSON.parseObject(result,Define.seek.class);
                            list.clear();
                            Define.seek_item seekItem = seek.data;
                            if (seekItem ==null){

                            }else {
                                Map<String,Object> map = new HashMap<>();
                                map.put("name",seekItem.currency_name);
                                map.put("currency_id",seekItem.currency_id);
                                map.put("currency_icon",seekItem.currency_icon);
                                list.add(map);
                                adapter.notifyDataSetChanged();
                            }

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_seek:
                select_currency(words.getText().toString());
                break;
            case R.id.im_back:
                finish();
                break;
            case R.id.iv_index_qiehuan:
                list.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
