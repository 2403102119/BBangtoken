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
 * Created by Administrator on 2019/6/26.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyHolder> {
    private Context context;
    private List<Map<String, Object>> list;
    public DetailsAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public DetailsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.details, parent, false);
        return new DetailsAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailsAdapter.MyHolder holder, int position) {
        if (list.get(position).get("transfer_type").toString().equals("1")){
            holder.transfer_type.setText("转入");
            holder.tv_address.setText(list.get(position).get("recive_address").toString());
        }else {
            holder.transfer_type.setText("转出");
            holder.tv_address.setText(list.get(position).get("payment_address").toString());
        }
        if (list.get(position).get("state").toString().equals("0")){
            holder.tv_state.setText("转账失败");
        }else if (list.get(position).get("state").toString().equals("1")){
            holder.tv_state.setText("转账成功");
        }else {
            holder.tv_state.setText("转账中");
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView transfer_type,tv_state,tv_address;
        public MyHolder(View itemView) {
            super(itemView);
            transfer_type = itemView.findViewById(R.id.transfer_type);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }
    private TestAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(TestAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);

        void checkListener(int firstPosition, int position, boolean isCheck, String oid);

        void isOpen(int position);

        void OnItemChildClickListener(int firstPosition, int childPosition, String oid);
    }

}
