package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/6/28.
 */

public class OpendogAdapter extends RecyclerView.Adapter<OpendogAdapter.MyHolder> {

    private Context context;
    private List<Map<String, Object>> list;
    private boolean ischeck = false;
    private String imageUrl;

    public OpendogAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public OpendogAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.openlayout, parent, false);
        return new OpendogAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final OpendogAdapter.MyHolder holder, final int position) {
        imageUrl = list.get(position).get("currency_icon").toString();
        Glide.with(context).load(imageUrl).into(holder.im_icon);
        holder.tv_name.setText(list.get(position).get("currency_name").toString());
        if (list.get(position).get("state").toString().equals("0")){
            holder.chinese.setChecked(false);
            ischeck = false;
        }else {
            holder.chinese.setChecked(true);
            ischeck = true;
        }

        if (list.get(position).get("principal_amount").toString().equals("0")){
            holder.tv_capital.setText("未開啟 ");
            holder.tv_capital.setTextColor(282828);

        }else {
            holder.tv_capital.setText(list.get(position).get("principal_amount").toString());
        }

        holder.chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ischeck == true){
                    holder.chinese.setChecked(true);
                }else {
                    holder.chinese.setChecked(false);
                }
                onItemClickListener.OnItemClickListener(position,ischeck,v);
            }
        });



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
        TextView tv_name,tv_capital;
        CheckBox chinese;
        public MyHolder(View itemView) {
            super(itemView);
            im_icon = itemView.findViewById(R.id.im_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            chinese = itemView.findViewById(R.id.chinese);
            tv_capital = itemView.findViewById(R.id.tv_capital);
        }
    }
    private OpendogAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OpendogAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition,boolean ischeck,View view);


    }
}
