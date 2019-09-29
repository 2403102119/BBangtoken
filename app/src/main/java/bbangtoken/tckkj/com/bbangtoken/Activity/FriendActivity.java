package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.FriendAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SortAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;
/*
*我的好友界面
*@Author:李迪迦
*@Date:
*/
public class FriendActivity extends BaseActivity {

    private RecyclerView friend;
    LinearLayoutManager layoutManager;
    FriendAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    private TextView tv_popel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_friend);
        setTopTitle("我的好友");
        friend = findViewById(R.id.friend);
        tv_popel = findViewById(R.id.tv_popel);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(FriendActivity.this);
        friend.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(FriendActivity.this, list);
        friend.setAdapter(adapter);
        adapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }



        });


    }
    //我的好友
    public void friend(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.friend( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Friend friend = JSON.parseObject(result,Define.Friend.class);
//                            showToast(friend.message);
                            Define.friend_item friendItem = friend.data;
                            tv_popel.setText(friendItem.total_friend+"");
                            list.clear();
                            List<Define.friend_item_one> friendItems = friendItem.list;
                            for (int i = 0; i <friendItems.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("nick_name",friendItems.get(i).nick_name);
                                map.put("u_header",friendItems.get(i).u_header);
                                map.put("u_account",friendItems.get(i).u_account);
                                map.put("c_time",friendItems.get(i).c_time);
                                map.put("state",friendItems.get(i).state);
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
        friend();
    }
}
