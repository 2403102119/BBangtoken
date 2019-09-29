package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/6/28.
 */

public class SustermAdapter  extends RecyclerView.Adapter<SustermAdapter.MyHolder>{
    private Context context;
    private List<Map<String, Object>> list;
    public SustermAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public SustermAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sustermadapterlayout, parent, false);
        return new SustermAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SustermAdapter.MyHolder holder, final int position) {
        holder.ll_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });
        holder.tv_title.setText(list.get(position).get("title").toString());
        holder.tv_timer.setText(list.get(position).get("ctime").toString());
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }else {
            return list.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_mess;
        TextView tv_title,tv_timer;
        public MyHolder(View itemView) {
            super(itemView);
            ll_mess = itemView.findViewById(R.id.ll_mess);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_timer = itemView.findViewById(R.id.tv_timer);
        }
    }
    private SustermAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SustermAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
