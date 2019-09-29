package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.Help_textAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;
/*
*帮助中心界面
*@Author:李迪迦
*@Date:
*/
public class HelpActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView text_one;
    LinearLayoutManager layoutManager;
    Help_textAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    private ImageView m_phone;
    public Context context;
    private String navigationHeight;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_help);
        baseTop.setVisibility(View.GONE);
        text_one = findViewById(R.id.text_one);
        m_phone = findViewById(R.id.m_phone);
//        Resources resources = context.getResources();
//        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//        getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, resources.getDimensionPixelSize(resourceId));
//设置状态栏文字颜色及图标为浅色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }


    @Override
    protected void initEvent() {
        m_phone.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        layoutManager=new LinearLayoutManager(this);
        text_one.setLayoutManager(layoutManager);
        adapter=new Help_textAdapter(this,list);
        text_one.setAdapter(adapter);
        adapter.setOnItemClickListener(new Help_textAdapter.OnItemClickListener() {


            @Override
            public void OnItemClickListener(int firstPosition) {

                if(!(boolean)list.get(firstPosition).get("isOpen")){
                    Map<String, Object> map = list.get(firstPosition);
                    list.get(firstPosition).put("isOpen", true);
                    list.set(firstPosition, map);
                }else {
                    Map<String, Object> map = list.get(firstPosition);
                    list.get(firstPosition).put("isOpen", false);
                    list.set(firstPosition, map);
                }
                adapter.notifyDataSetChanged();

            }
        });

        help();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.m_phone:
                finish();
                break;
        }
    }
    //帮助中心
    public void help(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.help( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.help_center helpCenter = JSON.parseObject(result,Define.help_center.class);
//                            showToast(helpCenter.message);
                            list.clear();
                            List<Define.help_center_item> helpCenterItems = helpCenter.data;
                            for (int i = 0; i <helpCenterItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("title_name",helpCenterItems.get(i).title_name);
                                map.put("content",helpCenterItems.get(i).content);
                                map.put("isOpen",false);        //分组是否是打开状态     默认关闭
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
}
