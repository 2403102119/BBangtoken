package bbangtoken.tckkj.com.bbangtoken.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;
import com.zhangke.websocket.response.ErrorResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Activity.AddActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.BBCdetailActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.Details_currencyActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.SearchActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.SortActivity;
import bbangtoken.tckkj.com.bbangtoken.Adapter.PriceAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.TestAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseFragment;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;

/**
 * Created by Administrator on 2019/6/26.
 */
/*
*行情页面
*@Author:李迪迦
*@Date:2019.6.27
*/
public class PriceFragment extends BaseFragment {
    private RecyclerView price;
    private LinearLayoutManager layoutManager;
    List<Map<String,Object>> list=new ArrayList<>();
    private ImageView im_search,im_sort,im_add;
    PriceAdapter adapter;


    private SocketListener socketListener = new SimpleListener() {
        @Override
        public void onConnected() {
            appendMsgDisplay("onConnected");
        }

        @Override
        public void onConnectFailed(Throwable e) {
            if (e != null) {
                appendMsgDisplay("onConnectFailed:" + e.toString());
            } else {
                appendMsgDisplay("onConnectFailed:null");
            }
        }

        @Override
        public void onDisconnect() {
            appendMsgDisplay("onDisconnect");
        }

        @Override
        public void onSendDataError(ErrorResponse errorResponse) {
//            appendMsgDisplay(errorResponse.getDescription());
//            errorResponse.release();
        }

        @Override
        public <T> void onMessage(String message, T data) {

            Log.i("---------","======"+message);
            Define.commonResponseEntity commonResponseEntity = JSON.parseObject(message,Define.commonResponseEntity.class);
            appendMsgDisplay(message);
        }
    };



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_price, container, false);
        initView(view);
        initData();



        WebSocketHandler.getDefault().addListener(socketListener);
        return view;
    }
    private void initView(View view) {
        price = view.findViewById(R.id.price);
        im_search = view.findViewById(R.id.im_search);
        im_sort = view.findViewById(R.id.im_sort);
        im_add = view.findViewById(R.id.im_add);
    }
    private void initData() {


        layoutManager = new LinearLayoutManager(getActivity());
        price.setLayoutManager(layoutManager);
        adapter = new PriceAdapter(getActivity(), list);
        price.setAdapter(adapter);
        adapter.setOnItemClickListener(new PriceAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(getActivity(), Details_currencyActivity.class);
                intent.putExtra("name",list.get(firstPosition).get("name").toString());
                startActivity(intent);
            }



        });

        im_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        im_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SortActivity.class);
                startActivity(intent);
            }
        });
        im_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });

        Map<String,Object> map = new HashMap<>();
        map.put("name","EOS");
        map.put("close","0.00");
        map.put("rmb","0.00");
        map.put("limit","0.00");
        list.add(map);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("name","LTC");
        map1.put("close","0.00");
        map1.put("rmb","0.00");
        map1.put("limit","0.00");
        list.add(map1);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("name","ETH");
        map2.put("close","0.00");
        map2.put("rmb","0.00");
        map2.put("limit","0.00");
        list.add(map2);
        Map<String,Object> map3 = new HashMap<>();
        map3.put("name","BTC");
        map3.put("close","0.00");
        map3.put("rmb","0.00");
        map3.put("limit","0.00");
        list.add(map3);
        Map<String,Object> map4 = new HashMap<>();
        map4.put("name","XRP");
        map4.put("close","0.00");
        map4.put("rmb","0.00");
        map4.put("limit","0.00");
        list.add(map4);



    }
    public void appendMsgDisplay(String msg) {
        if (msg.equals("onDisconnect")){

        }else if (msg.equals("onConnected")){

        }else if (msg.equals("")){

        }else {
            Define.commonResponseEntity commonResponseEntity1 = JSON.parseObject(msg,Define.commonResponseEntity.class);
            Define.commonResponseEntity.tick_item commonResponseEntity = commonResponseEntity1.tick;
            Define.commonResponseEntity.exchange_rate_item exchangeRateItem = commonResponseEntity1.exchange_rate;

            if ("market.btcusdt.detail".equals(commonResponseEntity1.ch)){
                Map<String,Object> map3 = new HashMap<>();
                map3.put("name","BTC");
                map3.put("close",commonResponseEntity.close);
                map3.put("rmb",commonResponseEntity.close * exchangeRateItem.rate);
                map3.put("limit",StringUtil.keepTwoDecimalPlaces((commonResponseEntity.close - commonResponseEntity.open)/commonResponseEntity.open));
                Log.i("1111111111111111111", "appendMsgDisplay: "+commonResponseEntity.close * exchangeRateItem.rate);
                list.set(3,map3);
                adapter.notifyItemChanged(3);
            }else if ("market.ethusdt.detail".equals(commonResponseEntity1.ch)){
                Map<String,Object> map2 = new HashMap<>();
                map2.put("name","ETH");
                map2.put("close",commonResponseEntity.close);
                map2.put("rmb",commonResponseEntity.close * exchangeRateItem.rate);
                map2.put("limit",StringUtil.keepTwoDecimalPlaces((commonResponseEntity.close - commonResponseEntity.open)/commonResponseEntity.open));
                list.set(2,map2);
                adapter.notifyItemChanged(2);
            }else if ("market.eosusdt.detail".equals(commonResponseEntity1.ch)){
                Map<String,Object> map = new HashMap<>();
                map.put("name","EOS");
                map.put("close",commonResponseEntity.close);
                map.put("rmb",commonResponseEntity.close * exchangeRateItem.rate);
                map.put("limit",StringUtil.keepTwoDecimalPlaces((commonResponseEntity.close - commonResponseEntity.open)/commonResponseEntity.open));
                list.set(0,map);
                adapter.notifyItemChanged(0);
            }else if ("market.ltcusdt.detail".equals(commonResponseEntity1.ch)){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("name","LTC");
                map1.put("close",commonResponseEntity.close);
                map1.put("rmb",commonResponseEntity.close * exchangeRateItem.rate);
                map1.put("limit",StringUtil.keepTwoDecimalPlaces((commonResponseEntity.close - commonResponseEntity.open)/commonResponseEntity.open));
                list.set(1,map1);
                adapter.notifyItemChanged(1);
            }else if ("market.xrpusdt.detail".equals(commonResponseEntity1.ch)){
                Map<String,Object> map4 = new HashMap<>();
                map4.put("name","XRP");
                map4.put("close",commonResponseEntity.close);
                map4.put("rmb",commonResponseEntity.close * exchangeRateItem.rate);
                map4.put("limit",StringUtil.keepTwoDecimalPlaces((commonResponseEntity.close - commonResponseEntity.open)/commonResponseEntity.open));
                list.set(4,map4);
                adapter.notifyItemChanged(4);
            }


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        Map<String,Object> map=new HashMap<>();
        map.put("sub","market.allcoin.detail");
        map.put("id","allcoin");
        String json= StringUtil.map2Json(map);
        WebSocketHandler.getDefault().send(json);

        Map<String,Object> map1=new HashMap<>();
        map.put("ping","ping");
        String json1= StringUtil.map2Json(map1);
        WebSocketHandler.getDefault().send(json1);

        WebSocketHandler.getDefault().removeListener( socketListener);
    }
}
