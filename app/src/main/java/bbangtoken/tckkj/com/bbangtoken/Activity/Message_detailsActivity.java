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

public class Message_detailsActivity extends BaseActivity {

    private String id;
    private TextView tv_title,tv_name,tv_context;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_message_details);
        setTopTitle("詳情");
        tv_title =findViewById(R.id.tv_title);
        tv_name =findViewById(R.id.tv_name);
        tv_context =findViewById(R.id.tv_context);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
          id = getIntent().getStringExtra("id");

    }
    //资讯详情页面数据展示
    public void home_more(final String id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.ip_message_item(id, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Moredetail moredetail = JSON.parseObject(result,Define.Moredetail.class);
                            showToast(moredetail.message);
                            Define.moredetailitem moredetailitem = moredetail.data;
                            tv_title.setText(moredetailitem.title);
                            tv_name.setText(moredetailitem.ctime+"");
                            tv_context.setText(moredetailitem.content);


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
        home_more(id);
    }
}
