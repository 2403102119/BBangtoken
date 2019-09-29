package bbangtoken.tckkj.com.bbangtoken.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Activity.BBCdetailActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.MessageitemDetailsActivity;
import bbangtoken.tckkj.com.bbangtoken.Adapter.InformAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.TestAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseFragment;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/6/28.
 */
/*
*转账通知页面
*@Author:李迪迦
*@Date:
*/
public class InformFragment extends BaseFragment{
    private RecyclerView recycler;
    LinearLayoutManager layoutManager;
    InformAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inform, container, false);
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
        adapter = new InformAdapter(getActivity(), list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new InformAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }



        });


    }

    private void initListener() {
    }
    // 转账通知
    public void transfer_accounts(final String user_id){
        if (NetUtil.isNetWorking(getActivity())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.transfer_accounts(user_id, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {


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
