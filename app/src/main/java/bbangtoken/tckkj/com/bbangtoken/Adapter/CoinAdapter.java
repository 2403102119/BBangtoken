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

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;

    public CoinAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public CoinAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coinadapter, parent, false);
        return new CoinAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(CoinAdapter.MyHolder holder, int position) {
        holder.tv_time.setText(list.get(position).get("ctime").toString());
        imageUrl = list.get(position).get("currency_icon").toString();
        Glide.with(context).load(imageUrl).into(holder.im_coin_icon);
        holder.tv_coin_name.setText(list.get(position).get("currency_name").toString());
        holder.tv_borrow.setText(list.get(position).get("may_borrow_amount").toString());
        holder.tv_already.setText(list.get(position).get("already_borrow_amount").toString());
        holder.tv_also.setText(list.get(position).get("already_repay_amount").toString());
        holder.tv_number_also.setText(list.get(position).get("wait_repay_amount").toString());
        holder.tv_remaining.setText(list.get(position).get("expire_time").toString());


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
        TextView tv_time,tv_coin_name,tv_borrow,tv_already,tv_also,tv_number_also,tv_remaining;
        ImageView im_coin_icon;
        public MyHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            im_coin_icon = itemView.findViewById(R.id.im_coin_icon);
            tv_coin_name = itemView.findViewById(R.id.tv_coin_name);
            tv_borrow = itemView.findViewById(R.id.tv_borrow);
            tv_already = itemView.findViewById(R.id.tv_already);
            tv_also = itemView.findViewById(R.id.tv_also);
            tv_number_also = itemView.findViewById(R.id.tv_number_also);
            tv_remaining = itemView.findViewById(R.id.tv_remaining);
        }
    }
    private SortAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SortAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
