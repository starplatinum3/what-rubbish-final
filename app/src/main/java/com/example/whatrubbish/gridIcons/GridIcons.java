package com.example.whatrubbish.gridIcons;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.util.DpPxSpTool;

import java.util.ArrayList;
import java.util.List;

//import en.edu.zucc.pb.loginapplication.R;
import lombok.Data;
//import en.edu.zucc.pb.loginapplication.adapter.RvAdapter;

@Data
public class GridIcons {

    private static final String TAG = "GridIcons";

    private RecyclerView rv;
    private RvAdapter rvAdapter;
    int recyclerViewId;
    int itemId;

    TextView tvShow;
    Context context;
    private List<IconButton> iconButtons;
    //之后要调用里面的东西怎么办
    public IconButton selectedBtn;
    Activity activity;
    int spanCount = 5;

    public static String getTAG() {
        return TAG;
    }

    public RecyclerView getRv() {
        return rv;
    }

    public void setRv(RecyclerView rv) {
        this.rv = rv;
    }

    public RvAdapter getRvAdapter() {
        return rvAdapter;
    }

    public void setRvAdapter(RvAdapter rvAdapter) {
        this.rvAdapter = rvAdapter;
    }

    public int getRecyclerViewId() {
        return recyclerViewId;
    }

    public void setRecyclerViewId(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public TextView getTvShow() {
        return tvShow;
    }

    public void setTvShow(TextView tvShow) {
        this.tvShow = tvShow;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<IconButton> getIconButtons() {
        return iconButtons;
    }

    public void setIconButtons(List<IconButton> iconButtons) {
        this.iconButtons = iconButtons;
    }

    public IconButton getSelectedBtn() {
        return selectedBtn;
    }

    public void setSelectedBtn(IconButton selectedBtn) {
        this.selectedBtn = selectedBtn;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    //    gridview 每个 item 之间的 margin

    /**
     * @param context     目前感觉不一定需要 但是 如果有操作 也许需要把
     * @param iconButtons
     */
    public GridIcons(Context context, List<IconButton> iconButtons) {
        this.context = context;
        this.iconButtons = iconButtons;

    }

    public void init() {
//        rv = (RecyclerView) findViewById(R.id.rv);
//        Activity activity=(Activity)context;
        activity = (Activity) context;
//        activity.findViewById()
//        rv = activity.findViewById(R.id.rv);
//        那个 用来放 grid 的 recyclerViewId
//        Log.i(recyclerViewId,)
//        rv = activity.findViewById(recyclerViewId);
        initData();
        gridIconsInit();
    }


    void initIconsMock() {
//                datas = new ArrayList<>();
//        for (int i = 0; i < 38; i++) {
//            Resources res = getResources();
//            datas.add(res.getIdentifier("ic_category_" + i, "mipmap", getPackageName()));
//        }

    }

    /**
     * 初始化数据
     */

    private void initData() {
//        initIconsMock();

        /**
         *用来确定每一个item如何进行排列摆放
         * LinearLayoutManager 相当于ListView的效果
         GridLayoutManager相当于GridView的效果
         StaggeredGridLayoutManager 瀑布流
         */
        /**第一步：设置布局管理器**/
//        Context context;
//        Context
//        rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
//        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        rv.setLayoutManager(new RelativeLayout(context, LinearLayoutManager.VERTICAL, false));
        /**第二步：添加分割线**/
//        DividerItemDecoration   itemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
//        DividerItemDecoration   itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//        rv.addItemDecoration(itemDecoration);
        /**第三步：设置适配器**/
//        rvAdapter = new RvAdapter(this, datas);
//        rvAdapter = new RvAdapter(context, iconButtons, R.layout.item);
        rvAdapter = new RvAdapter(context, iconButtons, itemId);
//        rvAdapter.set
//        rvAdapter.setImageViewId();
//        rvAdapter.setTextViewId();
        if(rv!=null){
            rv.setAdapter(rvAdapter);

        }
//        rv.addItemDecoration();
        rvAdapter.setOnItemClickListener(new RvAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(int position, Integer data) {
////                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
////                Toast.makeText(activity, "点击了" + position, Toast.LENGTH_SHORT).show();
////                关键这个position 怎么来的 他就是个接口
////                他是走了哪个接口啊
//                Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onItemClickListener(int position, Long typeId) {
//
//            }

            @Override
            public void onItemClickListener(int position, IconButton iconButton) {
//                Long typeId = iconButton.getTypeId();
//                这里的数据怎么传出去。。
                selectedBtn = iconButton;
//                iconButton.
                Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
//                rvAdapter.
                if (tvShow != null) {
                    tvShow.setText(iconButton.getText());
                }

            }

        });
    }


    void gridIconsInit() {
        //        平铺的 很多个 的 按钮

//        new RvAdapter ();

//        背景图片和 文字是一起的 不是一上一下
//        <!--    android:background="#312f2f"-->
//<!--    不要颜色-->
//        并不是 ，是因为弄了个黑色背景
//其实是一上一下的
        /**竖向的GridView**/
//        GridView 点击了 背景颜色变化 其他的变回去
//        case R.id.id_action_gridview:
//        rvAdapter.setType(1);
        rvAdapter.setType(RvAdapter.verticalGridView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        rvAdapter.setGridLayoutManager(gridLayoutManager);
        rv.setBackgroundColor(Color.TRANSPARENT);
//        设置了背景就不行吗
//        rv.removeItemDecoration(itemDecoration);
//        这里是RelativeLayout
//        GridLayout 点击其中一个 变色
        Log.i(TAG, "setLayoutParams");
//        但是他不显示
//        rv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT));
//        GridLayoutManager selected
//        rv.setLayoutManager(new GridLayoutManager(context, spanCount));
        rv.setLayoutManager(gridLayoutManager);
//        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(this);
//        分隔栅装饰
//        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(context);
//        rv.addItemDecoration(itemDecoration);
//        break;
//        可以知道 Decoration 是可以叠加的 所以是 add

//        https://blog.csdn.net/JM_beizi/article/details/105364227
        // 添加间距
        int dpRowSpacing = 100;
        int dpColumnSpacing = 100;
//        int dpRowSpacing=30;
//        int dpColumnSpacing=20;
//                    public GridSpaceItemDecoration(int spanCount, int rowSpacing, int columnSpacing) {
//
        rv.addItemDecoration(new GridSpaceItemDecoration(4,
                DpPxSpTool.Dp2Px(activity, dpRowSpacing),
                DpPxSpTool.Dp2Px(activity, dpColumnSpacing)));


    }

}
