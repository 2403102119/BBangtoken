package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/7/9.
 */

public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.MyHolder>  {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;

    public PopupAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public PopupAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popuplayout, parent, false);
        return new PopupAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(PopupAdapter.MyHolder holder, final int position) {
        holder.tv_item.setText(list.get(position).get("currency_name").toString());
        holder.tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });
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
        TextView tv_item;

        public MyHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);

        }
    }
    private PopupAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(PopupAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
