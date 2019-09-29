package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.tifezh.kchartlib.chart.MinuteChartView;
import com.github.tifezh.kchartlib.utils.DateUtil;
import com.luck.picture.lib.tools.DateUtils;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.SocketWrapperListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.request.Request;
import com.zhangke.websocket.response.ErrorResponse;
import com.zhangke.websocket.response.Response;
import com.zhangke.websocket.response.ResponseFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.CommonResponseEntity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.DateUtilsl;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.View.DataRequest;
import bbangtoken.tckkj.com.bbangtoken.View.MinuteLineEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
/*
*币种详情界面
*@Author:李迪迦
*@Date:
*/
public class Details_currencyActivity extends BaseActivity implements View.OnClickListener {


    private ImageView im_fanhui;
    @BindView(R.id.minuteChartView)
    MinuteChartView mMinuteChartView;
    private String name;
    private TextView tv_name,tv_acount,tv_market_rank,tv_market_value,tv_circul_num,tv_issue_num,tv_funding_cost,tv_time,tv_tallest,tv_rate,tv_lowest,tv_deal;
    private SocketWrapperListener socketWrapperListener;

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
            appendMsgDisplay(errorResponse.getDescription());
            errorResponse.release();
        }

        @Override
        public <T> void onMessage(String message, T data) {

            Log.i("==========","======"+message);
            Define.commonResponseEntity commonResponseEntity = JSON.parseObject(message,Define.commonResponseEntity.class);
            tv_tallest.setText(commonResponseEntity.tick.high+"");//24h最高
            tv_lowest.setText(commonResponseEntity.tick.low+"");//24h最低
            tv_deal.setText(commonResponseEntity.tick.amount+"");//24h成交量

        }
    };




    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_details_currency);
        baseTop.setVisibility(View.GONE);
        im_fanhui = findViewById(R.id.im_fanhui);
        tv_name = findViewById(R.id.tv_name);
        tv_acount = findViewById(R.id.tv_acount);
        tv_market_rank = findViewById(R.id.tv_market_rank);
        tv_market_value = findViewById(R.id.tv_market_value);
        tv_circul_num = findViewById(R.id.tv_circul_num);
        tv_issue_num = findViewById(R.id.tv_issue_num);
        tv_funding_cost = findViewById(R.id.tv_funding_cost);
        tv_time = findViewById(R.id.tv_time);
        tv_tallest = findViewById(R.id.tv_tallest);
        tv_rate = findViewById(R.id.tv_rate);
        tv_lowest = findViewById(R.id.tv_lowest);
        tv_deal = findViewById(R.id.tv_deal);

        //设置状态栏文字颜色及图标为深色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ButterKnife.bind(this);

        WebSocketManager webSocketManager = WebSocketHandler.getWebSocket("111");
        webSocketManager.addListener(socketListener);
        webSocketManager.start();


        Map<String,Object> map=new HashMap<>();
        map.put("sub","market.btcusdt.detail");
        map.put("id","btcusdt");
        String json= StringUtil.map2Json(map);
        WebSocketHandler.getDefault().send(json);

        Map<String,Object> map1=new HashMap<>();
        map.put("ping","ping");
        String json1= StringUtil.map2Json(map1);
        WebSocketHandler.getDefault().send(json1);


        WebSocketHandler.getDefault().addListener(socketListener);
    }



    @Override
    protected void initEvent() {
        im_fanhui.setOnClickListener(this);
//        Response<String> response = ResponseFactory.createTextResponse();
//        Log.i("====","接收到的数据："+response);
//
//        socketWrapperListener.onMessage(response);

    }

    @Override
    protected void initData() {
        name = getIntent().getStringExtra("name");
        introduction(name);
        try {
            //整体开始时间
            Date startTime = DateUtil.shortTimeFormat.parse("09:30");
            //整体的结束时间
            Date endTime = DateUtil.shortTimeFormat.parse("15:00");
            //休息开始时间
            Date firstEndTime = DateUtil.shortTimeFormat.parse("11:30");
            //休息结束时间
            Date secondStartTime = DateUtil.shortTimeFormat.parse("13:00");
            //获取随机生成的数据
            List<MinuteLineEntity> minuteData =
                    DataRequest.getMinuteData(startTime,
                            endTime,
                            firstEndTime,
                            secondStartTime);
            mMinuteChartView.initData(minuteData,
                    startTime,
                    endTime,
                    firstEndTime,
                    secondStartTime,
                    (float) (minuteData.get(0).price - 0.5 + Math.random()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSocketHandler.getDefault().removeListener( socketListener);

    }

    private void appendMsgDisplay(String msg) {
        StringBuilder textBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(tv_tallest.getText())) {
            textBuilder.append(tv_tallest.getText().toString());
            textBuilder.append("\n");
        }
        textBuilder.append(msg);
        textBuilder.append("\n");
        tv_tallest.setText(msg);

    }
    // 行情介绍
    public void introduction(final String currency_name){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.introduction(currency_name, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.introduce_item introduceItem = JSON.parseObject(result,Define.introduce.class).data;
                            tv_name.setText(introduceItem.currency_name);
                            tv_acount.setText(introduceItem.currency_account);
                            tv_market_rank.setText(introduceItem.market_rank);
                            tv_market_value.setText("¥ "+introduceItem.market_value);
                            tv_circul_num.setText(introduceItem.circul_num);
                            tv_issue_num.setText(introduceItem.issue_num);
                            tv_funding_cost.setText(introduceItem.funding_cost);

                            String adoptTimeStr= introduceItem.issue_time;
                            String timeString = null;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 ");
                            long  l = Long.valueOf(adoptTimeStr);
                            timeString = sdf.format(new Date(l));//单位秒
                            tv_time.setText(timeString);
                            ; //时间戳转为时间格式
//                            tv_time.setText(DateUtilsl.stampToDate(introduceItem.issue_time));
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
            case R.id.im_fanhui:
                finish();
                break;
        }
    }



}
