package bbangtoken.tckkj.com.bbangtoken.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhangke.websocket.WebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bbangtoken.tckkj.com.bbangtoken.Activity.BBCdetailActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.RoolActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.ScanningActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.SearchActivity;
import bbangtoken.tckkj.com.bbangtoken.Adapter.TestAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseFragment;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.MainActivity;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import okhttp3.Call;


/**
 * Created by Administrator on 2019/6/26.
 */
/*
*首页页面
*@Author:李迪迦
*@Date:
*/
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recycler;
    LinearLayoutManager layoutManager;
    TestAdapter adapter;
    List<Map<String,Object>>list=new ArrayList<>();
    private ImageView iv_index_qiehuan;
    private TextView moeney,tv_set;
    private int REQUEST_CODE = 1;
    private LinearLayout ll_seek;
    private MyAdapter adapter1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        initView(view);
        initData();


        iv_index_qiehuan.setOnClickListener(this);
        ll_seek.setOnClickListener(this);
        adapter1 = new MyAdapter();
        return view;
    }

    private void initView(View view) {
        recycler = view.findViewById(R.id.recycle);
        moeney = view.findViewById(R.id.moeney);
        ll_seek = view.findViewById(R.id.ll_seek);
        iv_index_qiehuan = view.findViewById(R.id.iv_index_qiehuan);


    }

    private void initData() {
        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        adapter = new TestAdapter(getActivity(), list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new TestAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(getActivity(), BBCdetailActivity.class);
                intent.putExtra("currency_id",list.get(firstPosition).get("currency_id")+"");
                intent.putExtra("currency_name",list.get(firstPosition).get("currency_name").toString());
                startActivity(intent);
            }

            @Override
            public void checkListener(int firstPosition, int position, boolean isCheck, String oid) {

            }

            @Override
            public void isOpen(int position) {

            }

            @Override
            public void OnItemChildClickListener(int firstPosition, int childPosition, String oid) {


            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    String[] s= result.split("&");
                    String x = s[0].substring(0,3);

                    String[] y = result.split("=");
                    String s1 =y[3];
                    String s2 = y[2];

                    String[] a = s2.split("&");
                    String s3 = a[0];
                    if (x.equals("BBC")){
                        Intent intent = new Intent(getActivity(), RoolActivity.class);
                        intent.putExtra("velue","0");
                        intent.putExtra("result",s3);
                        intent.putExtra("moeney",s1);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getActivity(), ScanningActivity.class);
                        intent.putExtra("result",result);
                        startActivity(intent);
                    }
//                    Toast.makeText(getActivity(), "解析结果:" + x, Toast.LENGTH_LONG).show();
//                    Log.i("111","111:"+s);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_scroll_loan,parent,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            final  int tempPos = position%(list.size());
            holder.t1.setText("1.0正式上線！幣幣交易、幣幣商城等更多內容詳...");

        }

        @Override
        public int getItemCount() {
            return 9999;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private TextView t1;

            public ViewHolder(View itemView) {
                super(itemView);
                t1 = itemView.findViewById(R.id.t1);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_index_qiehuan:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

                break;
            case R.id.ll_seek:
                Intent intent1 = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent1);
                break;

        }
    }

    public void home_page(){
        if (NetUtil.isNetWorking(getActivity())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.home_page( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.HomeModel homeModel = JSON.parseObject(result,Define.HomeModel.class);
                            toToast(homeModel.message);
                            try{
                                Define.homelist homelist =  homeModel.data;
                                List<Define.currency_list1> currency_list1s = homelist.currency_list;
                                list.clear();
                                for (int i = 0; i <currency_list1s.size() ; i++) {
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("balance",currency_list1s.get(i).balance);
                                    map.put("currency_id",currency_list1s.get(i).currency_id);
                                    map.put("currency_icon",currency_list1s.get(i).currency_icon);
                                    map.put("currency_name",currency_list1s.get(i).currency_name);
                                    list.add(map);
                                }
                                adapter.notifyDataSetChanged();
                            }catch (Exception e){
                                e.printStackTrace();
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
    public void onResume() {
        super.onResume();
//        home_page();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            home_page();

            Map<String,Object> map=new HashMap<>();
            map.put("sub","market.btcusdt.detail");
            map.put("id","btcusdt");
            String json= StringUtil.map2Json(map);
            WebSocketHandler.getDefault().send(json);

            Map<String,Object> map1=new HashMap<>();
            map.put("ping","ping");
            String json1= StringUtil.map2Json(map1);
            WebSocketHandler.getDefault().send(json1);
        } else {

        }
    }
}
