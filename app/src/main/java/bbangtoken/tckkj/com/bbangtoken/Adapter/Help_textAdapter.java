package bbangtoken.tckkj.com.bbangtoken.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.R;

/**
 * Created by Administrator on 2019/6/27.
 */

public class Help_textAdapter extends RecyclerView.Adapter<Help_textAdapter.MyHolder>  {

    private Context context;
    private List<Map<String, Object>> list;
    private boolean ischeck = false;

    public Help_textAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Help_textAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.textadapter, parent, false);
        return new Help_textAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final Help_textAdapter.MyHolder holder, final int firstPosition) {


        if(!(boolean)list.get(firstPosition).get("isOpen")){     //根据记录设置分组的打开状态
            holder.control.setImageDrawable(context.getResources().getDrawable(R.drawable.d_xiangxai));

            holder.huida.setVisibility(View.GONE);

        }else {
            holder.control.setImageDrawable(context.getResources().getDrawable(R.drawable.d_xiangshang));

            holder.huida.setVisibility(View.VISIBLE);

        }
        holder.ll_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(firstPosition);
            }
        });
         holder.tv_title.setText(list.get(firstPosition).get("title_name").toString());
         holder.tv_content.setText(list.get(firstPosition).get("content").toString());

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
        LinearLayout huida,ll_issue;
        ImageView control;
        TextView tv_title,tv_content;
        public MyHolder(View itemView) {
            super(itemView);
            ll_issue = itemView.findViewById(R.id.ll_issue);
            huida = itemView.findViewById(R.id.huida);
            control = itemView.findViewById(R.id.control);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);

        }
    }
    private Help_textAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Help_textAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
