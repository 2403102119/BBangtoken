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

public class GeneralizeAdapter extends RecyclerView.Adapter<GeneralizeAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;

    public GeneralizeAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public GeneralizeAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.generalizeadapterlayout, parent, false);
        return new GeneralizeAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(GeneralizeAdapter.MyHolder holder, int position) {
        imageUrl = list.get(position).get("u_header").toString();
        Glide.with(context).load(imageUrl).into(holder.im_touxiang);
        holder.tv_nickname.setText(list.get(position).get("nick_name").toString());
        holder.tv_amount.setText(list.get(position).get("amount").toString());
        holder.tv_time.setText(list.get(position).get("ctime").toString());
        holder.tv_account.setText(list.get(position).get("u_account").toString());


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
        ImageView im_touxiang;
        TextView tv_nickname,tv_amount,tv_time,tv_account;
        public MyHolder(View itemView) {
            super(itemView);
            im_touxiang = itemView.findViewById(R.id.im_touxiang);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_account = itemView.findViewById(R.id.tv_account);
        }
    }
    private GeneralizeAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(GeneralizeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
