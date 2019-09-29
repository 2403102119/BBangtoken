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

import bbangtoken.tckkj.com.bbangtoken.Activity.BBCdetailActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.Message_detailsActivity;
import bbangtoken.tckkj.com.bbangtoken.Adapter.MoreAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.TestAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseFragment;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/6/26.
 */
/*
*资讯页面
*@Author:李迪迦
*@Date:
*/
public class MoreFragment extends BaseFragment {
    private RecyclerView more;
    LinearLayoutManager layoutManager;
    MoreAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        initView(view);
        initData();

        return view;
    }
    private void initView(View view) {
        more = view.findViewById(R.id.more);
    }
    private void initData() {
        layoutManager = new LinearLayoutManager(getActivity());
        more.setLayoutManager(layoutManager);
        adapter = new MoreAdapter(getActivity(), list);
        more.setAdapter(adapter);
        adapter.setOnItemClickListener(new MoreAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                      Intent intent = new Intent(getActivity(), Message_detailsActivity.class);
                      intent.putExtra("id",list.get(firstPosition).get("id").toString());
                      startActivity(intent);
            }



        });


    }
    public void home_message(){
        if (NetUtil.isNetWorking(getActivity())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.home_message( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.More  model = JSON.parseObject(result,Define.More.class);
                            toToast(model.message);
                            List<Define.moreitem>  moremodels = model.data;
                            list.clear();
                            for (int i = 0; i <moremodels.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("id",moremodels.get(i).id);
                                map.put("title",moremodels.get(i).title);
                                map.put("thumbnail",moremodels.get(i).thumbnail);
                                map.put("ctime",moremodels.get(i).ctime);
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
        home_message();
    }
}
