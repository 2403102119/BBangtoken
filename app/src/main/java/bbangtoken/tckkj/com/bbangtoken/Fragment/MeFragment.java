package bbangtoken.tckkj.com.bbangtoken.Fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import bbangtoken.tckkj.com.bbangtoken.Activity.CoinActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.EarningsActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.FriendActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.HelpActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.MessagerieActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.OpendogActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.PopularizeActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.ShaittoActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.SiteActivity;
import bbangtoken.tckkj.com.bbangtoken.Activity.SystemsetupActivity;
import bbangtoken.tckkj.com.bbangtoken.Base.BaseFragment;
import bbangtoken.tckkj.com.bbangtoken.Bean.Define;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Thread.MApiResultCallback;
import bbangtoken.tckkj.com.bbangtoken.Thread.ThreadPoolManager;
import bbangtoken.tckkj.com.bbangtoken.Util.NetUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/6/26.
 */
/*
*我的页面
*@Author:李迪迦
*@Date:2019.6.26
*/
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_coin_melts,modify_item,modify_layout,ll_popularize,my_friend,earnings,ll_help,system_setup,open_dog,messagerie,site;
    PopupWindow modify_popu_item;
    private TextView m_phone,tv_amount,tv_principal;
    private String imageUrl;
    private CircleImageView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        ll_coin_melts = view.findViewById(R.id.ll_coin_melts);
        ll_popularize = view.findViewById(R.id.ll_popularize);
        my_friend = view.findViewById(R.id.my_friend);
        earnings = view.findViewById(R.id.earnings);
        ll_help = view.findViewById(R.id.ll_help);
        system_setup = view.findViewById(R.id.system_setup);
        open_dog = view.findViewById(R.id.open_dog);
        messagerie = view.findViewById(R.id.messagerie);
        site = view.findViewById(R.id.site);
        m_phone = view.findViewById(R.id.m_phone);
        logo = view.findViewById(R.id.logo);
        tv_amount = view.findViewById(R.id.tv_amount);
        tv_principal = view.findViewById(R.id.tv_principal);




    }
    private void initData() {
        ll_coin_melts.setOnClickListener(this);
        ll_popularize.setOnClickListener(this);
        my_friend.setOnClickListener(this);
        earnings.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        system_setup.setOnClickListener(this);
        open_dog.setOnClickListener(this);
        messagerie.setOnClickListener(this);
        site.setOnClickListener(this);

        personal_details();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_coin_melts:
                coin();
                modify_item.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.activity_translate_in));
                modify_popu_item.showAtLocation(v, Gravity.CENTER,0,0);
                break;
            case R.id.ll_popularize://推广邀请
                Intent intent = new Intent(getActivity(), PopularizeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_friend://我的好友
                Intent intent1 = new Intent(getActivity(), FriendActivity.class);
                startActivity(intent1);
                break;
            case R.id.earnings://我的收益
                Intent intent2 = new Intent(getActivity(), EarningsActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_help://帮助中心
                Intent intent3 = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent3);
                break;
            case R.id.system_setup://系统设置
                Intent intent4 = new Intent(getActivity(), SystemsetupActivity.class);
                startActivity(intent4);
                break;
            case R.id.open_dog://开启智能狗
                Intent intent5 = new Intent(getActivity(), OpendogActivity.class);
                startActivity(intent5);
                break;
            case R.id.messagerie://站内消息
                Intent intent6 = new Intent(getActivity(), MessagerieActivity.class);
                startActivity(intent6);
                break;
            case R.id.site://钱包地址
                Intent intent7 = new Intent(getActivity(), SiteActivity.class);
                startActivity(intent7);
                break;
        }
    }
    public  void coin(){
        modify_popu_item=new PopupWindow(getActivity());
        View view=getLayoutInflater().inflate(R.layout.coin,null);
        modify_item= view.findViewById(R.id.modify_item);
        modify_popu_item.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        modify_popu_item.setBackgroundDrawable(new BitmapDrawable());
        modify_popu_item.setFocusable(true);
        modify_popu_item.setOutsideTouchable(true);
        modify_popu_item.setContentView(view);
        modify_layout= view.findViewById(R.id.modify_layout);
        final TextView be_as=view.findViewById(R.id.be_as);
        TextView modify_cancel=view.findViewById(R.id.modify_cancel);
        TextView modify_yes=view.findViewById(R.id.modify_yes);
        modify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
            }
        });
        modify_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
            }
        });
        modify_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_popu_item.dismiss();
                modify_item.clearAnimation();
                Intent intent = new Intent(getActivity(), CoinActivity.class);
                startActivity(intent);


            }
        });
    }


    // 个人信息
    public void personal_details(){
        if (NetUtil.isNetWorking(getActivity())){
            ThreadPoolManager.getInstance().getNetThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    httpInterface.personal_details( new MApiResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Define.me me = JSON.parseObject(result,Define.me.class);
//                            toToast(me.message);
                            Define.meitem meitem = me.data;
                            m_phone.setText(meitem.nick_name);
                            imageUrl = meitem.u_header;
                            tv_amount.setText("已借金額："+meitem.aleady_borrow_currency);
                            tv_principal.setText("我的本金："+meitem.intelligence_currency);
                            Glide.with(getContext()).load(imageUrl).into(logo);


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
    public void onResume() {
        super.onResume();
//        personal_details();
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            personal_details();
//        } else {
//
//        }
//    }
}
