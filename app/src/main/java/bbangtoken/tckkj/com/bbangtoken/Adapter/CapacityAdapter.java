package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/6/27.
 */

public class CapacityAdapter extends RecyclerView.Adapter<CapacityAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;
    public CapacityAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public CapacityAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.capacityadapterlayout, parent, false);
        return new CapacityAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(CapacityAdapter.MyHolder holder, int position) {
        imageUrl = list.get(position).get("currency_icon").toString();
        Glide.with(context).load(imageUrl).into(holder.im_icon);
        holder.tv_name.setText(list.get(position).get("currency_name").toString());
        holder.tv_amount.setText(list.get(position).get("amount").toString());
        holder.tv_time.setText(list.get(position).get("ctime").toString());

    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 6;
        }else {
            return list.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView im_icon;
        TextView tv_name,tv_amount,tv_time;
        public MyHolder(View itemView) {
            super(itemView);
            im_icon = itemView.findViewById(R.id.im_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
    private CapacityAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CapacityAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
