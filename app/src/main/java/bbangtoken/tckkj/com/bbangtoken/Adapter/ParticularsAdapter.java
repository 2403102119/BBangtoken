package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/6/26.
 */

public class ParticularsAdapter extends  RecyclerView.Adapter<ParticularsAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    public ParticularsAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public ParticularsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.particulars, parent, false);
        return new ParticularsAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ParticularsAdapter.MyHolder holder, int position) {
        holder.tv_moeney.setText(list.get(position).get("amount").toString());
        holder.tv_for_name.setText(list.get(position).get("currency_name").toString());
        holder.tv_time.setText(list.get(position).get("ctime").toString());
        holder.tv_cny.setText(list.get(position).get("exchange_amount").toString());
    }

    @Override
    public int getItemCount() {

        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_moeney,tv_for_name,tv_time,tv_cny;
        public MyHolder(View itemView) {
            super(itemView);
            tv_moeney = itemView.findViewById(R.id.tv_moeney);
            tv_for_name = itemView.findViewById(R.id.tv_for_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_cny = itemView.findViewById(R.id.tv_cny);
        }
    }
    private ParticularsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ParticularsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);

    }
}
