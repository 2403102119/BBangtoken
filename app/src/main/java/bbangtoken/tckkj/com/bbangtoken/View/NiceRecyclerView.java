package bbangtoken.tckkj.com.bbangtoken.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;


import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Util.ViewUtil;


/*
 * kylin on 2017年12月11日16:04:04
 */

public class NiceRecyclerView extends FrameLayout {
    public RecyclerView rv;
    public TextView tv;

    //RecyclerView 是listview还是gridview
    private ListDirection listDirection;
    //为空提示语是否显示
    private boolean isShowEmptyText;
    //为空提示语是否显示2
    private boolean isShowEmptyText2;
    //列表为空提示语
    private String emptyText;
    //gridview每行显示数量
    private int gridNum;

    /**
     * 只支持在xml文件定义
     * @param context 上下文activity
     * @param attrs 自定义属性
     */
    public NiceRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceRecyclerView);

        rv=new RecyclerView(context);
        LayoutParams lpRV=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rv.setLayoutParams(lpRV);

        tv=new TextView(context);
        tv.setGravity(Gravity.CENTER);
        LayoutParams lpTV=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tv.setPadding(0, ViewUtil.dp2px(getContext(),100),
                0,ViewUtil.dp2px(getContext(),100));
        tv.setLayoutParams(lpTV);

        this.addView(rv);
        this.addView(tv);

        emptyText=typedArray.getString(R.styleable.NiceRecyclerView_emptyText);
        listDirection= ListDirection.fromStep(typedArray.getInt(R.styleable.NiceRecyclerView_listDirection,1));
        gridNum=typedArray.getInt(R.styleable.NiceRecyclerView_gridNum,1);
        isShowEmptyText=typedArray.getBoolean(R.styleable.NiceRecyclerView_isShowEmptyText,true);
        isShowEmptyText2=typedArray.getBoolean(R.styleable.NiceRecyclerView_isShowEmptyText,true);

        setvisibility();

        if (listDirection== ListDirection.HORIZONTAL){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context,gridNum);
            rv.setLayoutManager(gridLayoutManager);
        }else {
            LinearLayoutManager lm = new LinearLayoutManager(context);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(lm);
        }

        tv.setText(emptyText);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        rv.setLayoutManager(layoutManager);
    }

    public void setNestedScrol(boolean enable){
        rv.setNestedScrollingEnabled(enable);
    }

    public void setHasFixedSize(boolean b){
        rv.setHasFixedSize(b);
    }

    public void setEmptyText(String emptyText){
        tv.setText(emptyText);
    }

    public void setEmptyImg(int resourceId){
        tv.setCompoundDrawablesWithIntrinsicBounds(0,0,0,resourceId);
        tv.setCompoundDrawablePadding(ViewUtil.dp2px(tv.getContext(),80));
    }

    private void setvisibility() {
        if(isShowEmptyText&&isShowEmptyText2){
            rv.setVisibility(GONE);
            tv.setVisibility(VISIBLE);
        }else {
            rv.setVisibility(VISIBLE);
            tv.setVisibility(GONE);
        }
    }

    public RecyclerView.Adapter<?> getAdapter() {
        return rv.getAdapter();
    }

    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        rv.setAdapter(adapter);
        adapter.registerAdapterDataObserver(observer);
        observer.onChanged();
    }

    public boolean isShowEmptyText() {
        return isShowEmptyText&&isShowEmptyText2;
    }

    public void setShowEmptyText(boolean showEmptyText) {
        isShowEmptyText = showEmptyText;
        isShowEmptyText2 = showEmptyText;
        setvisibility();
    }

    /**
     * RecyclerView 是listview还是gridview
     */
    private enum ListDirection {
        HORIZONTAL(0), VERTICAL(1);
        int step;

        ListDirection(int step) {
            this.step = step;
        }

        public static ListDirection fromStep(int step) {
            for (ListDirection f : values()) {
                if (f.step == step) {
                    return f;
                }
            }
            throw new IllegalArgumentException();
        }
    }


    private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            RecyclerView.Adapter adapter = getAdapter();
            if (adapter.getItemCount()==0){
                isShowEmptyText2=true;
                setvisibility();
            }else{
                isShowEmptyText2=false;
                setvisibility();
            }
        }
    };


    /*
    *
    * <com.tck.kuailaibang.view.NiceRecyclerView
    android:id="@+id/filterView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:background="#ffffff"
    android:paddingBottom="2dp"
    android:paddingLeft="6dp"
    android:paddingRight="6dp"
    android:paddingTop="2dp"
    app:listDirection="HORIZONTAL"
    app:emptyText="现在还没有数据哦~~"
    app:gridNum="4">
</com.tck.kuailaibang.view.NiceRecyclerView>

<com.tck.kuailaibang.view.NiceRecyclerView
    android:id="@+id/productView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"
    android:layout_marginBottom="7dp"
    android:background="#ffffff"
    app:listDirection="VERTICAL"
    app:emptyText="现在还没有数据哦~~">

</com.tck.kuailaibang.view.NiceRecyclerView>
    *
    * */
}
