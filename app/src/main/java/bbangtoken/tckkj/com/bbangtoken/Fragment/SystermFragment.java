package bbangtoken.tckkj.com.bbangtoken.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Activity.MessageitemDetailsActivity;
import bbangtoken.tckkj.com.bbangtoken.Adapter.InformAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SustermAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseFragment;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/6/28.
 */
/*
*系统通知页面
*@Author:李迪迦
*@Date:
*/
public class SystermFragment extends BaseFragment {
    private RecyclerView recycler;
    LinearLayoutManager layoutManager;
    SustermAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.systermlayout, container, false);
        initView(view);
        initData();

        return view;
    }
    private void initView(View view) {
        recycler = view.findViewById(R.id.recycle);
    }
    private void initData() {
        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        adapter = new SustermAdapter(getActivity(), list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new SustermAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(getActivity(), MessageitemDetailsActivity.class);
                intent.putExtra("id",list.get(firstPosition).get("id").toString());
                startActivity(intent);
            }



        });


    }

    private void initListener() {
    }
    //系统消息
    public void system(){
        if (NetUtil.isNetWorking(getActivity())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.system( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.System system = JSON.parseObject(result,Define.System.class);
                            toToast(system.message);
                            list.clear();
                            List<Define.system_item> systemItems = system.data;
                            for (int i = 0; i <systemItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("title",systemItems.get(i).title);
                                map.put("ctime",systemItems.get(i).ctime);
                                map.put("id",systemItems.get(i).id);
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
    public void onResume() {
        super.onResume();
        system();
    }
}
