package bbangtoken.tckkj.com.bbangtoken.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

import bbangtoken.tckkj.com.bbangtoken.Adapter.PickerLayoutManager;
import bbangtoken.tckkj.com.bbangtoken.R;


/**
 * @Description 选择上传图片方式的popupwindow
 */
public class HeadPickerWindow extends Dialog {
    Context context;//上下文

    public interface PickListener{
        void pick(String str);
    }

    public PickListener listener;

    public void setPickListener(PickListener listener){
        this.listener = listener;
    }

    private String currStr = "";
    private int textSize = 20;


    public HeadPickerWindow(Context context, final List<Map<String,Object>> data, String titleStr, int textSize) {
        super(context, R.style.processDialog);
        this.context = context;
        this.textSize = textSize;
        View view = getLayoutInflater().inflate(R.layout.dialog_picker,null);
        RecyclerView recyclerView = view.findViewById(R.id.wheel);
        final PickerLayoutManager layoutManager = new PickerLayoutManager
                (getContext(),recyclerView, PickerLayoutManager.VERTICAL,
                false,5,0.4f,true);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(data);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (data.size()>0){
            currStr = data.get(0).get("name").toString();
        }

        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });




        this.setContentView(view);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private List<Map<String,Object>> mList ;

        public MyAdapter(List<Map<String,Object>> list) {
            this.mList = list;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.selectlayout,parent,false);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
//            holder.tvText.setText(mList.get(position));
            holder.name.setText(mList.get(position).get("name").toString());
            holder.code.setText(mList.get(position).get("code").toString());
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        String name = mList.get(position).get("name").toString();
                        String code = mList.get(position).get("code").toString();
                        currStr = name + code;
                        listener.pick(currStr);
                    }
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView name,code;
            LinearLayout ll_item;
            public ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                code = itemView.findViewById(R.id.code);
                ll_item = itemView.findViewById(R.id.ll_item);

            }
        }
    }


}

