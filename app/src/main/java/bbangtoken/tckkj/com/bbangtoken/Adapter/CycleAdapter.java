package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/7/10.
 */

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.MyHolder>{
    private Context context;
    private List<Map<String, Object>> list1;
    private String imageUrl;

    public CycleAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list1 = list;

    }

    @Override
    public CycleAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popuplayout, parent, false);
        return new CycleAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(CycleAdapter.MyHolder holder, final int position) {
        if ("".equals(list1.get(position).get("cycle_name").toString())){
            holder.tv_item.setText("");
        }else {
            holder.tv_item.setText(list1.get(position).get("cycle_name").toString());
        }

        holder.tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list1 == null){
            return 0;
        }else {
            return  list1.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_item;

        public MyHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);

        }
    }
    private CycleAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CycleAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
