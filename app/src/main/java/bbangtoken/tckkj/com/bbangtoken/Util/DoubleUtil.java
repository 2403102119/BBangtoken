package bbangtoken.tckkj.com.bbangtoken.Util;

import java.text.NumberFormat;

/**
 * kylin on 2017/10/17.
 */

public class DoubleUtil {

    public static String douleEffect(double d){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String str = (d+"");
        String[] a = new String[2];
        //Log.e("rrrr str",str+"##!!");
        try{
            if (str.contains(".")){
                a[0] = str.substring(0,str.indexOf("."));
                a[1] = str.substring(str.indexOf("."),str.length());
                //Log.e("a[0]",a[0]+" ** ");
                //Log.e("a[1]",a[1]+" ** ");
                String t = a[1].trim();
                for (int i = 0;t.endsWith("0");i++){
                    t = t.substring(0,t.length()-1);
                }
                if (".0".equals(t)||".".equals(t)){
                    t = "";
                }
                //Log.e("wwwwwwwww",a[0]+"^"+t);
                if (t.length()>3){
                    t = t.substring(0,3);
                }
                str = a[0]+t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

}
