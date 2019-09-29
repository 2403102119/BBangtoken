package bbangtoken.tckkj.com.bbangtoken.Util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

/**
 * kylin on 2017/12/15.
 */

public class SpanUtil {

    private Context context;

    public SpanUtil(Context context){this.context = context.getApplicationContext();}

    public void setColorSpan(int colorResource,String str, int start,int end,TextView textView){
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(colorResource ),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sp);
    }

    public void setSizeSpan(int size,String str, int start,int end,TextView textView){
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new AbsoluteSizeSpan(ViewUtil.dp2px(context,size)),
                start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sp);
    }

    public void setUnderLineSpan(String str,TextView textView){
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        textView.setText(content);
    }


}
