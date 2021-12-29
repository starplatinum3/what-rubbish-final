package com.example.whatrubbish.gridIcons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.R;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

//import en.edu.zucc.pb.loginapplication.R;

/**
 * RecyclerView的适配器
 */
//@Data
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    private Context context;
//    private List<Integer> datas;
    private List<IconButton> iconButtons;
    int layoutItemId;

    GridLayoutManager gridLayoutManager;

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

    public void setGridLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public int getTextViewId() {
        return textViewId;
    }

    public void setTextViewId(int textViewId) {
        this.textViewId = textViewId;
    }
    //也就是一个东西的布局吗
    /**
     * item的点击事件的长按事件接口
     */
    private OnItemClickListener onItemClickListener;
    /**
     * 瀑布流时的item随机高度
     */
    private List<Integer> heights = new ArrayList<>();

    /**
     * 不同的类型设置item不同的高度
     *
     * @param type
     */

    private int type = 0;

  public final  static int verticalGridView=1;
//    int verticalGridView=1;
//    public RvAdapter(Context context, List<Integer> datas) {
//        this.context = context;
//        this.datas = datas;
//        for (int i : datas) {
//            int height = (int) (Math.random() * 100 + 300);
//            heights.add(height);
//        }
//    }
//
//    public RvAdapter(Context context, List<Integer> datas, int layoutItemId) {
//        this.context = context;
//        this.datas = datas;
//        this.layoutItemId = layoutItemId;
//        for (int i : datas) {
//            int height = (int) (Math.random() * 100 + 300);
//            heights.add(height);
//        }
//    }

    public RvAdapter(Context context, List<IconButton> iconButtons, int layoutItemId) {
        this.context = context;
        this.iconButtons = iconButtons;
        this.layoutItemId = layoutItemId;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View contentView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        View contentView = LayoutInflater.from(context).inflate(layoutItemId, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(contentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RecyclerView.LayoutParams layoutParams;
        if (type == 0) {
            layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        } else if (type == 1) {
        } else if (type == verticalGridView) {
            layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(2, 2, 2, 2);
//            这是他本身吗
//            int margin=10;
//            layoutParams.setMargins(margin, margin, margin, margin);
//            layoutParams.height=layoutParams.width;
        } else {
            layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heights.get(position));
            layoutParams.setMargins(2, 2, 2, 2);
        }
//        这是给 item 设置的
        holder.itemView.setLayoutParams(layoutParams);
        IconButton iconButton = iconButtons.get(position);
//        holder.imageView.setImageResource(datas.get(position));
        holder.imageView.setImageResource(iconButton.getIconId());
        if( holder.tv!=null){
            holder.tv.setText(iconButton.getText());

        }
//        可能需要和 type 转化一下吧. 还是说直接用type？
//        holder.tv.setText("分类" + position);
//        怎么才能设置其他的不是呢 好像获取不到别的？
        /**设置item点击监听**/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClickListener(position, datas.get(position));
//                    哦哦 就是这里传出去的 数据
//                    每个type都有个 id吧
//                    onItemClickListener.onItemClickListener(position, iconButton.getIconId());
//                    哦哦因为这里只是走了 传递type id的
//                    onItemClickListener.onItemClickListener(position, iconButton.getTypeId());
                    onItemClickListener.onItemClickListener(position, iconButton);

//                    int color = ContextCompat.getColor(context, R.color.orange);
//                    view.setBackgroundColor(color);

//                    其他的要变成不是这个颜色 啊
//                    view.back
//                    context.   getResources().getColor(R.color.orange)
//                    'getColor(int)' is deprecated as of API 23: Android 6.0 (Marshmallow
                }
            }
        });

//        get

//获取内容layout的parms
        ViewGroup.LayoutParams parm = holder.layout.getLayoutParams();
//通过获取gridlayoutmanager的个数于宽度动态给parm赋予高度
        parm.height = gridLayoutManager.getWidth()/ gridLayoutManager.getSpanCount()
                - 2 * holder.layout.getPaddingLeft() - 2*((ViewGroup.MarginLayoutParams)parm).leftMargin;

//————————————————
//        版权声明：本文为CSDN博主「Qiyandays」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/Qiyandays/article/details/105633674

    }

    @Override
//    public int getItemCount() {
//        return datas == null ? 0 : datas.size();
//    }
    public int getItemCount() {
        return iconButtons == null ? 0 : iconButtons.size();
    }

    int imageViewId;
    int textViewId;
    /**
     * 用于缓存的ViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public TextView tv;
        public ViewGroup layout;
//        但是这个 不一定每次都是这样的
//        int imageViewId;
//        int textViewId;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
            layout= itemView.findViewById(R.id.layout);
//            imageView = (ImageView) itemView.findViewById(imageViewId);
//            tv = (TextView) itemView.findViewById(textViewId);

//            imageView = (ImageView) itemView.findViewById(R.id.iv);
//            tv = (TextView) itemView.findViewById(textViewId);

        }
    }

    /**
     * 设置item监听的接口
     */
    public interface OnItemClickListener {
//        void onItemClickListener(int position, Integer data);
//        void onItemClickListener(int position, Long typeId);
        void onItemClickListener(int position, IconButton iconButton);

    }
}
