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
 * Created by Administrator on 2019/6/26.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;

    public TestAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.first_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }

    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int firstPosition) {

        holder.ll_bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(firstPosition);
            }
        });

        holder.tv_name.setText((String) list.get(firstPosition).get("currency_name"));
        holder.tv_balance.setText((String) list.get(firstPosition).get("tv_balance"));
        imageUrl = list.get(firstPosition).get("currency_icon").toString();
        Glide.with(context).load(imageUrl).into(holder.im_icon);
//        holder.tv_shuliang.setText(list.get(firstPosition).get("green") + "/" + list.get(firstPosition).get("count"));


    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView im_icon;
        LinearLayout control, ll_bb;
        TextView tv_name, tv_balance;
        RecyclerView fenzu_item;

        public MyHolder(final View itemView) {
            super(itemView);
            ll_bb = itemView.findViewById(R.id.ll_bb);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_balance = itemView.findViewById(R.id.tv_balance);
            im_icon = itemView.findViewById(R.id.im_icon);

        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);

        void checkListener(int firstPosition, int position, boolean isCheck, String oid);

        void isOpen(int position);

        void OnItemChildClickListener(int firstPosition, int childPosition, String oid);
    }


}
