package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/6/27.
 */

public class Test_itemAdapter  extends RecyclerView.Adapter<Test_itemAdapter.MyitemHolder>{
    private Context context;


    private List<Map<String,Object>> mlist;

    public Test_itemAdapter(Context context,List<Map<String,Object>> list){
        this.context=context;
        this.mlist=list;
        Log.i("11111111", "TestAdapter: " + this.mlist.size());
    }
    @Override
    public Test_itemAdapter.MyitemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.second_item,parent,false);
        return new Test_itemAdapter.MyitemHolder(view);
    }

    @Override
    public void onBindViewHolder(final Test_itemAdapter.MyitemHolder holder, final int position) {




    }

    @Override
    public int getItemCount() {
//        if (mlist==null){
//            return 0;
//        }else {
//            return mlist.size();
//        }
        return 4;
    }
    public class MyitemHolder extends RecyclerView.ViewHolder{
        TextView bianhao,zhuangtai;
        LinearLayout item;

        public MyitemHolder(View itemView) {
            super(itemView);

        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnItemClickListener(int position, String oid);
    }
}
