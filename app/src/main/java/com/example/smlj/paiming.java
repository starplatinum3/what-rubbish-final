package com.example.smlj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.util.DrawUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class paiming extends Fragment {
    private MypaimingAdapter myAdapter;
    View view;

    @Override
    public void onResume() {
        super.onResume();
        //  initGridIcons();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.paiming, container, false);
        //getActivity().setContentView(R.layout.paiming);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置adapter
        myAdapter=new MypaimingAdapter(getActivity());
        mRecyclerView.setAdapter(myAdapter);
        //设置默认属性
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//       view = inflater.inflate(R.layout.paiming, container, false);

        return view;
    }





}
class MypaimingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    com.example.whatrubbish.databinding.PaimingitemBinding paimingitemBinding;

    List<String> namelist=new ArrayList<>();
    List<String> jifenlist=new ArrayList<>();
    //List<Integer> touxianglist=new ArrayList<>();
    List<String> touxianglist=new ArrayList<>();
    private Context context;
    public MypaimingAdapter(Context context) {
        this.context=context;
        init();


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.paimingitem, parent, false);
        paimingitemBinding= com.example.whatrubbish.databinding.PaimingitemBinding.bind(view);//最重要
        NormalViewHolder holder=new NormalViewHolder(view);
        holder.paim=(TextView)view.findViewById(R.id.paimnum);
        holder.touxiang=(ImageView) view.findViewById(R.id.imageView5);
        holder.jifen=(TextView) view.findViewById(R.id.textView8);
       // holder.jifen=paimingitemBinding.textView8;
        holder.name=(TextView) view.findViewById(R.id.textView6);
        return holder;



    }
    public class NormalViewHolder extends RecyclerView.ViewHolder {


        private TextView paim;
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
        private TextView jifen;
        private ImageView touxiang;
       // private com.example.whatrubbish.databinding.PaimingitemBinding paimingitemBinding;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        NormalViewHolder holder = (NormalViewHolder) viewHolder;
        if(position==0){
            holder.paim.setText(String.valueOf(position + 1));
            holder.name.setText(namelist.get(position));
            //holder.touxiang.setImageResource(touxianglist.get(position));
            //DrawUtil.loadImageChoose(context,touxianglist.get(position),holder.touxiang);
            DrawUtil.loadRoundImageChoose(context,touxianglist.get(position),holder.touxiang);
            Thread thread = new Thread(() -> {
                try{
                    setPointCnt();
                }catch (Exception e){
                    e.printStackTrace();
                }

            });
            ThreadPoolManager.run(thread);

        }
        else if(position<getItemCount()-1) {
            holder.paim.setText(String.valueOf(position + 1));
            holder.name.setText(namelist.get(position));
            holder.jifen.setText(jifenlist.get(position));
            String avatarUrl = touxianglist.get(position);
            //DrawUtil.loadImageChoose(context,avatarUrl,holder.touxiang);
            DrawUtil.loadRoundImageChoose(context,avatarUrl,holder.touxiang);
            //holder.touxiang.setImageResource(touxianglist.get(position));
        }
        else{
            holder.name.setText("");
            holder.paim.setText("");
            holder.touxiang.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    void setPointCnt() {
        if (Bus.curUser == null) {
            //Bus.httpDb
            //ToastUtil.show(, "当前没有用户登录 请登录");

            return;
        }
        //Integer stageId;
        //stageId = Bus.curUser.getStageId();
        //stageId = Bus.curUser.getPointCnt();
        Integer pointCnt = Bus.curUser.getPointCnt();

        if (pointCnt == null) {
            pointCnt = 0;
        }
        //if (stageId == null) {
        //    stageId = 0;
        //}
        //if (pointCnt > 5) {
        //    pointCnt = 5;
        //}
        //double progress = pointCnt * 100.0 / Bus.maxPoint;
        //double progress = pointCnt * 100.0 / Bus.maxPoint;
        //binding.tvPoint.setText(pointCnt+"");
        //jifenlist.add(pointCnt+"");
        paimingitemBinding.textView8.setText(pointCnt+"");
    }


   //static boolean mockData=true;
   static boolean mockData=false;
    public  void init(){
        if (mockData) {
            namelist.add("网友小郑");
            touxianglist.add(R.mipmap.touxiang+"");
        }else{

            if(Bus.curUser==null){
                namelist.add("网友小郑");
                touxianglist.add(R.mipmap.touxiang+"");
            }else{
                String nickname = Bus.curUser.getNickname();
                String avatarUrl = Bus.curUser.getAvatarUrl();
                namelist.add(nickname);
                touxianglist.add(avatarUrl);
            }

        }

        namelist.add("李薇薇");
        namelist.add("邓佳美");
        namelist.add("王一");
        namelist.add("张航");
        namelist.add("陈立飞");
        namelist.add("林雨");
        namelist.add("姚一鸣");

        jifenlist.add("1");
        jifenlist.add("20");
        jifenlist.add("15");
        jifenlist.add("11");
        jifenlist.add("10");

        jifenlist.add("8");
        jifenlist.add("5");
        jifenlist.add("2");
        //touxianglist.add(R.mipmap.touxiang);
        touxianglist.add(R.mipmap.tou1+"");
        touxianglist.add(R.mipmap.tou2+"");
        touxianglist.add(R.mipmap.tou3+"");
        touxianglist.add(R.mipmap.tou4+"");
        touxianglist.add(R.mipmap.tou5+"");
        touxianglist.add(R.mipmap.tou6+"");
        touxianglist.add(R.mipmap.tou7+"");

        //touxianglist.add(R.mipmap.tou1);
        //touxianglist.add(R.mipmap.tou2);
        //touxianglist.add(R.mipmap.tou3);
        //touxianglist.add(R.mipmap.tou4);
        //touxianglist.add(R.mipmap.tou5);
        //touxianglist.add(R.mipmap.tou6);
        //touxianglist.add(R.mipmap.tou7);
    }
    @Override
    public int getItemCount() {
        return 9;
    }
}