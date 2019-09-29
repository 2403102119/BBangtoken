package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.R;
/*
*查看私钥界面
*@Author:李迪迦
*@Date:
*/
public class LookActivity extends BaseActivity implements View.OnClickListener {
   private TextView tv_enter,tv_key;
   private String p_key;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_look);
        setTopTitle("查看私鑰");
        tv_enter = findViewById(R.id.tv_enter);
        tv_key = findViewById(R.id.tv_key);
    }

    @Override
    protected void initEvent() {
        tv_enter.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        p_key = getIntent().getStringExtra("p_key").toString();
        tv_key.setText(p_key);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_enter:
                Intent intent = new Intent(LookActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
