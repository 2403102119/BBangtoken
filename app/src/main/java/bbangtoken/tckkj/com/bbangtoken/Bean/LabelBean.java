package bbangtoken.tckkj.com.bbangtoken.Bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;

/**
 * @author 依风听雨
 * @version 创建时间：2017/5/23 15:26
 */

public class LabelBean {
    public static final int HEADER = 0;
    public static final int LABEL = 1;

    @IntDef({HEADER, LABEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private String label;
    private int type;
    private String groupName;
    private boolean isChecked = false;
    private String oid;
    public List<HashMap<String,String>> hashMaps;

    private double money;
    private int day;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LabelBean(String label, String groupName, String oid, double money, int day) {
        this.label = label;
        this.type = LABEL;
        this.groupName = groupName;
        this.oid = oid;
        this.money = money;
        this.day = day;
    }

    public LabelBean(String label, int type) {
        this.label = label;
        this.type = type;
        this.groupName = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @TYPE
    public int getType() {
        return type;
    }

    public void setType(@TYPE int type) {
        this.type = type;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
