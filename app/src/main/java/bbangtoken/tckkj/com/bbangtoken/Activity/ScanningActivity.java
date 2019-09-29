package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.R;
/*
*扫描结果界面
*@Author:李迪迦
*@Date:
*/
public class ScanningActivity extends BaseActivity {

   private String sanning;
   private TextView tv_scann;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_scanning);
        setTopTitle("扫描结果");
        tv_scann =findViewById(R.id.tv_scann);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        sanning = getIntent().getStringExtra("result");
        tv_scann.setText(sanning);
    }
}
