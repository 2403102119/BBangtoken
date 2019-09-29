package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.R;

/*
*备份助记词一级页面
*@Author:李迪迦
*@Date:
*/
public class MnemonicActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_next_step;
    private String auxiliaries;
    private TextView word;
    ArrayList<String> list = new ArrayList<>();
    private  String s ;
    private  String y ;
    private  String z ;
    private  String x ;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_mnemonic);
        setTopTitle("備份助記詞");
        bt_next_step = findViewById(R.id.bt_next_step);
        word = findViewById(R.id.word);


    }

    @Override
    protected void initEvent() {
        bt_next_step.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        auxiliaries = getIntent().getStringExtra("auxiliaries");
        s = auxiliaries.substring(0,5);
        y = auxiliaries.substring(5,10);
        z = auxiliaries.substring(10,15);
        x = auxiliaries.substring(15,20);

        list.add(s);
        list.add(y);
        list.add(z);
        list.add(x);
        Log.i("1111","1111"+s);
        word.setText(s + ","+ y + "," + z + "," + x);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next_step:
                Intent intent = new Intent(MnemonicActivity.this,BackupsActivity.class);
                intent.putExtra("s",s);
                intent.putExtra("y",y);
                intent.putExtra("z",z);
                intent.putExtra("x",x);
                startActivity(intent);
                break;
        }
    }


}
