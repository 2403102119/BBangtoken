package bbangtoken.tckkj.com.bbangtoken.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.OpendogAdapter;
import bbangtoken.tckkj.com.bbangtoken.Adapter.SortAdapter;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseActivity;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.MD5Utils;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.StringUtil;
import bbangtoken.tckkj.com.bbangtoken.View.ActionDialog;
import okhttp3.Call;

/*
*开启智能狗页面
*@Author:李迪迦
*@Date:
*/
public class OpendogActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView open;
    LinearLayoutManager layoutManager;
    OpendogAdapter adapter;
    List<Map<String,Object>> list=new ArrayList<>();
    private String currency_id;
    ActionDialog openDialog,messageDialog;
    private boolean ischeck;
    private PopupWindow modify_popu_item;
    private LinearLayout modify_item,modify_layout;



    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_opendog);
        setTopTitle("開啟智能狗");
        rightIcon.setVisibility(View.VISIBLE);
        rightIcon.setImageResource(R.mipmap.xiangqing);
        open = findViewById(R.id.open);
        openDialog = new ActionDialog(OpendogActivity.this,"關閉智能狗","確定要關閉智能狗嗎？","取消","确定");
        openDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                openDialog.dismiss();
            }

            @Override
            public void onRightClick() {
                close(currency_id);
                openDialog.dismiss();
            }
        }) ;
        messageDialog = new ActionDialog(OpendogActivity.this,"關閉智能狗","您有一筆未還貸款，請先清還貸款","取消","还款");
        messageDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                messageDialog.dismiss();
            }

            @Override
            public void onRightClick() {
                  Intent intent = new Intent(OpendogActivity.this,RefundActivity.class);
                  startActivity(intent);
            }
        });


    }

    @Override
    protected void initEvent() {
        rightIcon.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        layoutManager = new LinearLayoutManager(OpendogActivity.this);
        open.setLayoutManager(layoutManager);
        adapter = new OpendogAdapter(OpendogActivity.this, list);
        open.setAdapter(adapter);
        adapter.setOnItemClickListener(new OpendogAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition,boolean ischeck,View v) {
                currency_id = list.get(firstPosition).get("currency_id").toString();
                String state = list.get(firstPosition).get("state").toString();

                if (state.equals("1")){
                    openDialog.show();
                }else if (state.equals("0")){
                    modify();
                    modify_item.startAnimation(AnimationUtils.loadAnimation(OpendogActivity.this,R.anim.activity_translate_in));
                    modify_popu_item.showAtLocation(v, Gravity.CENTER,0,0);
                }

            }



        });


    }
    public  void modify(){
        modify_popu_item=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.open_popu,null);
        modify_item= view.findViewById(R.id.modify_item);
        modify_popu_item.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setBackgroundDrawable(new BitmapDrawable());
        modify_popu_item.setFocusable(true);
        modify_popu_item.setOutsideTouchable(true);
        modify_popu_item.setContentView(view);
        modify_layout= view.findViewById(R.id.modify_layout);
        final EditText et_number1=view.findViewById(R.id.et_number);
        final EditText et_password=view.findViewById(R.id.et_password);
        final TextView modify_yes=view.findViewById(R.id.modify_yes);

        modify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
            }
        });

        modify_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String be_asd = et_number1.getText().toString().trim();
                 String et_password1 = et_password.getText().toString().trim();
                et_password1 = MD5Utils.md5Password(et_password1);
                et_password1 = MD5Utils.md5Password(et_password1+"qaz123");
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
                open_dog(currency_id,be_asd,et_password1);



            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rightIcon:
                Intent intent = new Intent(OpendogActivity.this,OpendogdetailActivity.class);
                startActivity(intent);
                break;
        }
    }
    //智能狗列表
    public void smart_dog_list(){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.smart_dog_list( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.Smart_dog smart_dog = JSON.parseObject(result,Define.Smart_dog.class);
                            showToast(smart_dog.message);
                            list.clear();
                            List<Define.Smart_dog_item>  smart_dog_items = smart_dog.data;
                            for (int i = 0; i <smart_dog_items.size() ; i++) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("currency_id",smart_dog_items.get(i).currency_id);
                                map.put("currency_name",smart_dog_items.get(i).currency_name);
                                map.put("currency_icon",smart_dog_items.get(i).currency_icon);
                                map.put("state",smart_dog_items.get(i).state);
                                map.put("principal_amount",smart_dog_items.get(i).principal_amount);
                                list.add(map);
                            }
                            adapter.notifyDataSetChanged();
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
    //开启智能狗
    public void open_dog(final String currency_id,final String amount,final String t_password){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.open(currency_id,amount,t_password, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.dog dog = JSON.parseObject(result,Define.dog.class);
                            showToast(dog.message);
                            smart_dog_list();
                            list.clear();
                            adapter.notifyDataSetChanged();
                            ischeck = true;
                        }

                        @Override
                        public void onFail(String response) {
//                            ischeck = false;
                            Define.dog dog = JSON.parseObject(response,Define.dog.class);
                            showToast(dog.message);
                        }

                        @Override
                        public void onError(Call call, Exception exception) {
                            ischeck = false;


                        }

                        @Override
                        public void onFinish(String response) {
//                            ischeck = false;
                            Define.dog dog = JSON.parseObject(response,Define.dog.class);
                            showToast(dog.message);
                        }
                    });
                }
            });
        }else{
        }
    }
    //关闭智能狗
    public void close(final String currency_id){
        if (NetUtil.isNetWorking(getApplicationContext())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.close_item(currency_id, new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.dog dog = JSON.parseObject(result,Define.dog.class);
                            if (dog.error_code.equals("8888")){
                               messageDialog.show();
                            }else {
                                showToast(dog.message);
                            }

                            smart_dog_list();
                            list.clear();
                            adapter.notifyDataSetChanged();
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
        smart_dog_list();
    }
}
