package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/6/27.
 */

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    public PriceAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public PriceAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.price, parent, false);
        return new PriceAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(PriceAdapter.MyHolder holder, final int position) {
        holder.tv_name.setText(list.get(position).get("name").toString());
        holder.details_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });
        holder.tv_close.setText(list.get(position).get("close").toString());
        holder.tv_rmb.setText(list.get(position).get("rmb").toString());
        if (list.get(position).get("name").toString().equals("BTC")){
            holder.im_icon.setImageResource(R.mipmap.hangqing_labol);
        }else if (list.get(position).get("name").toString().equals("EOS")){
            holder.im_icon.setImageResource(R.mipmap.eos);
        }else if (list.get(position).get("name").toString().equals("LTC")){
            holder.im_icon.setImageResource(R.mipmap.ltc);
        }else if (list.get(position).get("name").toString().equals("ETH")){
            holder.im_icon.setImageResource(R.mipmap.eth);
        }else if (list.get(position).get("name").toString().equals("XRP")){
            holder.im_icon.setImageResource(R.mipmap.xrp);
        }
        holder.tv_limit.setText(list.get(position).get("limit").toString()+"%");
//               int y = Integer.parseInt();
        Double y = Double.parseDouble(list.get(position).get("limit").toString().trim());
        if (y<0){
            holder.tv_limit.setBackgroundResource(R.drawable.hong);
        }else {
            holder.tv_limit.setBackgroundResource(R.drawable.luv);
        }
    }

    @Override
    public int getItemCount() {
        if (list== null){
            return 0;
        }else {
            return list.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout details_currency;
        TextView tv_name,tv_close,tv_rmb,tv_limit;
        ImageView im_icon;
        public MyHolder(View itemView) {
            super(itemView);
            details_currency = itemView.findViewById(R.id.details_currency);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_close = itemView.findViewById(R.id.tv_close);
            tv_rmb = itemView.findViewById(R.id.tv_rmb);
            im_icon = itemView.findViewById(R.id.im_icon);
            tv_limit = itemView.findViewById(R.id.tv_limit);
        }
    }
    private PriceAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(PriceAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
