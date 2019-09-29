package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/6/27.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;
    public FriendAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public FriendAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friendlayout, parent, false);
        return new FriendAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendAdapter.MyHolder holder, int position) {
        Glide.with(context).load(imageUrl).into(holder.tv_touxiang);
        holder.tv_name.setText(list.get(position).get("nick_name").toString());
        holder.tv_phone.setText(list.get(position).get("u_account").toString());
        holder.tv_timenr.setText(list.get(position).get("c_time").toString());
        if (list.get(position).get("state").equals("1")){
            holder.tv_open_dog.setText("已开启智能狗");
        }else {
            holder.tv_open_dog.setText("未开启智能狗");
        }


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
        CircleImageView tv_touxiang;
        TextView tv_name,tv_phone,tv_timenr,tv_open_dog;
        public MyHolder(View itemView) {
            super(itemView);
            tv_touxiang = itemView.findViewById(R.id.tv_touxiang);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_timenr = itemView.findViewById(R.id.tv_timenr);
            tv_open_dog = itemView.findViewById(R.id.tv_open_dog);
        }
    }
    private FriendAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(FriendAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
