package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/7/8.
 */

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.MyHolder>  {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageurl;
    public SelectAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public SelectAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seeklayout, parent, false);
        return new SelectAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectAdapter.MyHolder holder, final int position) {
        if (list == null)
            holder.tv_Seek.setVisibility(View.VISIBLE);
        else
            holder.tv_Seek.setVisibility(View.GONE);

         holder.ll_currency.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onItemClickListener.OnItemClickListener(position);
             }
         });
        imageurl = list.get(position).get("currency_icon").toString();
        Glide.with(context).load(imageurl).into(holder.im_icon);
        holder.tv_name.setText(list.get(position).get("name").toString());

    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }else {
            return  list.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_Seek,tv_name;
        ImageView im_icon;
        LinearLayout ll_currency;
        public MyHolder(View itemView) {
            super(itemView);
            tv_Seek = itemView.findViewById(R.id.tv_Seek);
            ll_currency = itemView.findViewById(R.id.ll_currency);
            im_icon = itemView.findViewById(R.id.im_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
    private SelectAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SelectAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }

}
