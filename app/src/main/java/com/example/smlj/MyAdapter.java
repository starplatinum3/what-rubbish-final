package com.example.smlj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.MainActivity;
import com.example.whatrubbish.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_VIEW = 0;
    final int i=1;
    private static final int NORMAL_VIEW = 1;
    //   ArrayList<String> list = new ArrayList();
    private Context context;

    List<String> namelist=new ArrayList<>();
    List<String> contentlist=new ArrayList<>();
    List<String> timelist=new ArrayList<>();
    List<Integer> touxianglist=new ArrayList<>();

    int flag=0;

        public MyAdapter(Context context,int flag) {
        this.context = context;
        if(flag==1)
        this.flag=flag;
        init();

    }
//    public MyAdapter(Context context) {
//        this.context = context;
//
//
//        init();
//
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {

            View headerView = LayoutInflater.from(context).inflate(R.layout.itemhead, parent, false);
            HeaderViewHolder Headerholder = new HeaderViewHolder(headerView);
            Headerholder.weidu=(TextView) headerView.findViewById(R.id.weidu);
            return Headerholder;
        }
        View normView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        NormalViewHolder holder = new NormalViewHolder(normView);
        holder.content=(TextView) normView.findViewById(R.id.textView4);
        holder.name=(TextView) normView.findViewById(R.id.name);
        holder.time=(TextView) normView.findViewById(R.id.time);
        holder.touxiang=(ImageView) normView.findViewById(R.id.imageView5);
        holder.mark=(ImageView) normView.findViewById(R.id.imageView6);
        holder.weiduback=(ImageView) normView.findViewById(R.id.weiduback);
        holder.weidunum=(TextView) normView.findViewById(R.id.weidunum);

        return holder;

    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public NormalViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }
        private TextView name;
        private TextView content;
        private ImageView touxiang;
        private TextView time;
        private ImageView mark;
        private ImageView weiduback;
        private TextView weidunum;


    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the item
                }
            });

        }
        private TextView weidu;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }

        return NORMAL_VIEW;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NormalViewHolder) {
            NormalViewHolder holder = (NormalViewHolder) viewHolder;


            holder.name.setText(namelist.get(position-1));
            holder.content.setText(contentlist.get(position-1));
            holder.time.setText(timelist.get(position-1));
            holder.touxiang.setImageResource(touxianglist.get(position-1));
            if(position==1){
                holder.mark.setImageResource(R.mipmap.mark19);//置顶
                if(flag==0){
                    holder.weidunum.setVisibility(View.VISIBLE);
                    holder.weiduback.setVisibility(View.VISIBLE);

                }
                else
                {
                    holder.content.setText("表情");
                    holder.weidunum.setVisibility(View.GONE);
                    holder.weiduback.setVisibility(View.GONE);}

            }
        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            if(flag==1){
                headerViewHolder.weidu.setText("(未读0)");
            }
        }


    }

    public  void init(){
        namelist.add("李薇薇");
        namelist.add("邓佳美");
        namelist.add("王一");
        namelist.add("张航");
        namelist.add("陈立飞");
        contentlist.add("不过我刚刚在游戏中看到了你想要的。");
        contentlist.add("我的排名快超过你啦！");
        contentlist.add("好的，我知道了。");
        contentlist.add("我觉得那个很好玩的。");
        contentlist.add("我昨天收集到了想要的碎片。");
        timelist.add("9:24");
        timelist.add("8:35");
        timelist.add("昨天21:56");
        timelist.add("昨天16:30");
        timelist.add("周一20:45");
        touxianglist.add(R.mipmap.tou1);
        touxianglist.add(R.mipmap.tou2);
        touxianglist.add(R.mipmap.tou3);
        touxianglist.add(R.mipmap.tou4);
        touxianglist.add(R.mipmap.tou5);
    }
    @Override
    public int getItemCount() {

        return 6;
    }
//        if (list == null) {
//            return 0;
//        }
//
//        if (list.size() == 0) {
//            //header count include
//            return 1;
//        }
//
//        //header count include
//        return list.size() + 1;
//    }
}