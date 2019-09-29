package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.R;
/*
*系统设置页面
*@Author:李迪迦
*@Date:
*/
public class SystemsetupActivity extends BaseActivity implements View.OnClickListener {
     private LinearLayout personal_details,amend_password,traders_Password,export_key,language,about_me;
     private Button bt_logging_out;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_systemsetup);
        setTopTitle("系統設置");
        personal_details = findViewById(R.id.personal_details);
        amend_password = findViewById(R.id.amend_password);
        traders_Password = findViewById(R.id.traders_Password);
        export_key = findViewById(R.id.export_key);
        language = findViewById(R.id.language);
        about_me = findViewById(R.id.about_me);
        bt_logging_out = findViewById(R.id.bt_logging_out);
    }

    @Override
    protected void initEvent() {
        personal_details.setOnClickListener(this);
        amend_password.setOnClickListener(this);
        traders_Password.setOnClickListener(this);
        export_key.setOnClickListener(this);
        language.setOnClickListener(this);
        about_me.setOnClickListener(this);
        bt_logging_out.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_details://个人信息
                Intent intent = new Intent(SystemsetupActivity.this,PersonalActivity.class);
                startActivity(intent);
                break;
                case R.id.amend_password://修改登录密码
                Intent intent2 = new Intent(SystemsetupActivity.this,AmendActivity.class);
                startActivity(intent2);
                break;
            case R.id.traders_Password://修改交易密码
                Intent intent3 = new Intent(SystemsetupActivity.this,TradersPasswordActivity.class);
                startActivity(intent3);
                break;
                case R.id.export_key://导出私钥
                Intent intent4 = new Intent(SystemsetupActivity.this,ExportActivity.class);
                startActivity(intent4);
                break;
                case R.id.language://语言版本
                Intent intent5 = new Intent(SystemsetupActivity.this,LanguageActivity.class);
                startActivity(intent5);
                break;
                case R.id.about_me://关于我们
                Intent intent6 = new Intent(SystemsetupActivity.this,AboutActivity.class);
                startActivity(intent6);
                break;
                case R.id.bt_logging_out://退出登录
                Intent intent7 = new Intent(SystemsetupActivity.this,LoginActivity.class);
                startActivity(intent7);
                break;
        }
    }
}
