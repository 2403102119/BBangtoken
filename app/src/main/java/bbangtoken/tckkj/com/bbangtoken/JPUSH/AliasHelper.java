package bbangtoken.tckkj.com.bbangtoken.JPUSH;

import android.content.Context;

/**
 * Created by catdog on 2018/7/2.
 */

public class AliasHelper {

    public static void setAlias(Context context,String alias){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        TagAliasOperatorHelper.sequence++;
        tagAliasBean.alias = alias;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context.getApplicationContext(),TagAliasOperatorHelper.sequence,tagAliasBean);
    }
}
