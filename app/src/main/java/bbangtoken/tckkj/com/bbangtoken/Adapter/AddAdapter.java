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

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    private String imurl;

    public AddAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public AddAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addlayout, parent, false);
        return new AddAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(AddAdapter.MyHolder holder, int position) {
        imurl = list.get(position).get("currency_icon").toString();
        Glide.with(context).load(imurl).into(holder.im_icon);
        holder.tv_name.setText(list.get(position).get("currency_name").toString());
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
        ImageView im_icon;
        TextView tv_name;
        public MyHolder(View itemView) {
            super(itemView);
            im_icon = itemView.findViewById(R.id.im_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
    private AddAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AddAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
