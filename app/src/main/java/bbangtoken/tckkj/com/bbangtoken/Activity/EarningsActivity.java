package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import okhttp3.Call;

/*
*我的收益页面
*@Author:李迪迦
*@Date:
*/
public class EarningsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout capacity,generalize;
    private TextView tv_sum,tv_capacity,tv_generalize;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_earnings);
        setTopTitle("我的收益");
        capacity = findViewById(R.id.capacity);
        generalize = findViewById(R.id.generalize);
        tv_sum = findViewById(R.id.tv_sum);
        tv_capacity = findViewById(R.id.tv_capacity);
        tv_generalize = findViewById(R.id.tv_generalize);
    }

    @Override
    protected void initEvent() {
        capacity.setOnClickListener(this);
        generalize.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.capacity:
                Intent intent = new Intent(EarningsActivity.this,CapacityActivity.class);
                startActivity(intent);
                break;
                case R.id.generalize:
                Intent intent1 = new Intent(EarningsActivity.this,GeneralizeActivity.class);
                startActivity(intent1);
                break;
        }
    }
    // 我的收益
    public void earnings(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.earnings( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.My_income myIncome = JSON.parseObject(result,Define.My_income.class);
//                            showToast(myIncome.message);
                            Define.My_income_item myIncomeItem = myIncome.data;
                            tv_sum.setText(myIncomeItem.total_intellect_profit);
                            tv_capacity.setText(myIncomeItem.today_intellect_profit);
                            tv_generalize.setText(myIncomeItem.today_extend_profit);


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
        earnings();
    }
}
