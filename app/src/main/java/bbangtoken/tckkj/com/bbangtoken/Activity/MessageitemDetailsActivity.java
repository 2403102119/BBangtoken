package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
*消息詳情界面
*@Author:李迪迦
*@Date:
*/
public class MessageitemDetailsActivity extends BaseActivity {

   private String id;
    private TextView tv_titale,tv_time,tv_content;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_messageitem_details);
        setTopTitle("消息詳情");
        tv_titale = findViewById(R.id.tv_titale);
        tv_time = findViewById(R.id.tv_time);
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
          id = getIntent().getStringExtra("id");
    }
    //系统消息详情
    public void system_item(final String id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.system_item(id, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.System_detail systemDetail = JSON.parseObject(result,Define.System_detail.class);
                            showToast(systemDetail.message);
                            Define.system_detail_item systemDetailItem = systemDetail.data;
                            tv_titale.setText(systemDetailItem.title);
                            tv_time.setText(systemDetailItem.ctime);
                            tv_content.setText(systemDetailItem.content);

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
        system_item(id);
    }
}
