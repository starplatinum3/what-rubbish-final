package com.example.whatrubbish.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smlj.FmPagerAdapter;
import com.example.smlj.MyAdapter;
import com.example.whatrubbish.adapter.CardListAdapter;
import com.example.whatrubbish.databinding.ActivityCardBinding;
import com.example.whatrubbish.databinding.ActivityCardListBinding;
import com.example.whatrubbish.entity.RecMsg;
import com.example.whatrubbish.fragment.CardFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCardListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //tabInit();
        initRecyclerView(this);

    }

    //ActivityCardBinding binding;
    ActivityCardListBinding binding;

    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    CardListAdapter myAdapter;
    void initRecyclerView(Activity activity){

        RecyclerView mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //RecyclerView view 点击 知道他的内容
//       myAdapter = new MyAdapter(activity);

        //myAdapter = new MyAdapter(activity,flag);
        //不是啊 这是给数据
        List<RecMsg> recMsgs=new ArrayList<>();
        RecMsg recMsg = new RecMsg();
        recMsg.setMsg("1");
        recMsg.setDate(new Date());
        recMsg.setTitle("3");
        recMsgs.add(recMsg);
         myAdapter = new CardListAdapter(recMsgs);
        myAdapter.setActivity(activity);

        //myAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(myAdapter);
        //设置默认属性

        //myAdapter.setOnItemClickListerner(object :FruitAdapter.OnItemClickListerner{
        //    override fun onItemClick(position: Int) {
        //        Toast.makeText(this@MainActivity, "你点击了${frustList.get(position)}", Toast.LENGTH_SHORT).show()
        //    }
        //})
//————————————————
//        版权声明：本文为CSDN博主「强强爱学习」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/weixin_42708161/article/details/107919566

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void tabInit() {
        titles.add("   勋章   ");
        titles.add("   好友   ");
        titles.add("   个人   ");

//        for (String title : titles) {
//            fragments.add(new TabFragment());
//
//
//        }
//        fragments.add(new TabFragment());
        fragments.add(new CardFragment());
        fragments.add(new CardFragment());
        fragments.add(new CardFragment());
        //fragments.add(new paiming());
        //fragments.add(new geren());


        //FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        //Fragment fragment;
        //FragmentManager supportFragmentManager = getChildFragmentManager();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, supportFragmentManager);

        //binding.mViewPager.setAdapter(pagerAdapter);

//        viewPager.setAdapter(pagerAdapter);

        //tabLayout.setupWithViewPager(viewPager);

    }
}
