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
 * Created by Administrator on 2019/6/28.
 */

public class SiteAdapter  extends RecyclerView.Adapter<SiteAdapter.MyHolder>  {
    private Context context;
    private List<Map<String, Object>> list;
    private String imageUrl;

    public SiteAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public SiteAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sitelayout, parent, false);
        return new SiteAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SiteAdapter.MyHolder holder, final int position) {
        holder.tv_name.setText(list.get(position).get("currency_name").toString());
        holder.tv_site.setText(list.get(position).get("wallet_address").toString());
        imageUrl = list.get(position).get("currency_icon").toString();
        Glide.with(context).load(imageUrl).into(holder.im_icon);
          holder.ll_ever.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClickListener.OnItemClickListener(position);
              }
          });
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 2;
        }else {
            return  list.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_site;
        ImageView im_icon;
        LinearLayout ll_ever;
        public MyHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_site = itemView.findViewById(R.id.tv_site);
            im_icon = itemView.findViewById(R.id.im_icon);
            ll_ever = itemView.findViewById(R.id.ll_ever);
        }
    }
    private SiteAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SiteAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }

}
