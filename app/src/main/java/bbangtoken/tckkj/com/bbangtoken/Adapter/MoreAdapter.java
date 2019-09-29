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
 * Created by Administrator on 2019/6/27.
 */

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;

    public MoreAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public MoreAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.more_adapter, parent, false);
        return new MoreAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MoreAdapter.MyHolder holder, final  int firstPosition) {
        holder.ll_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(firstPosition);
            }
        });
        holder.tv_title.setText(list.get(firstPosition).get("title").toString());
        holder.tv_timer.setText(list.get(firstPosition).get("ctime").toString());
        imageUrl = list.get(firstPosition).get("thumbnail").toString();
        Glide.with(context).load(imageUrl).into(holder.im_thunbnail);
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
        LinearLayout ll_message;
        TextView tv_title,tv_timer;
        ImageView im_thunbnail;
        public MyHolder(View itemView) {
            super(itemView);
            ll_message = itemView.findViewById(R.id.ll_message);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_timer = itemView.findViewById(R.id.tv_timer);
            im_thunbnail = itemView.findViewById(R.id.im_thunbnail);

        }
    }

    private MoreAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MoreAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
