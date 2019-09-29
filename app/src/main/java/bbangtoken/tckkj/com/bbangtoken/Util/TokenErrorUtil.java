package bbangtoken.tckkj.com.bbangtoken.Util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import bbangtoken.tckkj.com.bbangtoken.Activity.LoginActivity;
import bbangtoken.tckkj.com.bbangtoken.App;
import bbangtoken.tckkj.com.bbangtoken.JPUSH.TagAliasOperatorHelper;
import bbangtoken.tckkj.com.bbangtoken.R;


/**
 * kylin on 2017/7/11.
 */

public class TokenErrorUtil {

    private LayoutInflater inflater;

    public TokenErrorUtil(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    /*目前使用的是这个*/
    public void tokenError(final Context context){
        if (!App.islogin){
            return;
        }
        App.islogin = false;
        App.token = "";
//        setAlias(context,"*");
        View tokenErrorView = inflater.inflate(R.layout.token_error, null);
        TextView goToLogin = (TextView) tokenErrorView.findViewById(R.id.goToLogin);
        TextView cancel = (TextView) tokenErrorView.findViewById(R.id.cancel);
        AlertDialog.Builder loginTipBuilder = new AlertDialog.Builder(context);
        loginTipBuilder.setView(tokenErrorView);
        final AlertDialog tokenErrorDialog = loginTipBuilder.create();
        tokenErrorDialog.setCancelable(false);
        tokenErrorDialog.show();
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenErrorDialog.dismiss();
                toLogin(context,0);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenErrorDialog.dismiss();
                toLogin(context,1);
            }
        });
    }

    private void toLogin(Context context,int flag){
        //清除登录信息
        SharedPreferencesUtil.saveData(context,UriUtil.account,"");
        SharedPreferencesUtil.saveData(context,UriUtil.pwd,"");
        App.islogin=false;

        //清空Activity栈
        App.finishAllActivity();
        if (flag==0){
            //跳转到登录界面
            Intent intent2 = new Intent(context,LoginActivity.class);
            context.startActivity(intent2);
        }
    }

    private void setAlias(Context context,String alias){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        TagAliasOperatorHelper.sequence++;
        tagAliasBean.alias = alias;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction
                (context.getApplicationContext(),TagAliasOperatorHelper.sequence,tagAliasBean);
    }
}
