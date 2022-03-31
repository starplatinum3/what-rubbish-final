package com.example.whatrubbish.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.arjinmc.viewpagerupdatedemo.DataFragment;
import com.arjinmc.viewpagerupdatedemo.DataUtil;
import com.arjinmc.viewpagerupdatedemo.PageUpdateMainActivity;
import com.example.smlj.FmPagerAdapter;
import com.example.whatrubbish.R;
import com.example.whatrubbish.adapter.CardPagerAdapter;
import com.example.whatrubbish.adapter.ViewPageAdapter;
import com.example.whatrubbish.databinding.ActivityCardBinding;
import com.example.whatrubbish.databinding.ActivityCardComposeBinding;
import com.example.whatrubbish.entity.Card;
import com.example.whatrubbish.fragment.CardFragment;
import com.example.whatrubbish.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CardComposeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCardComposeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        //LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.main, null);
        //ImageView child = (ImageView)parent.findViewById(R.id.child);
        //setContentView(parent);
        //setUpViewPagerCards();
        ////setFlipCard();
        //setUpViewPagerChosen();

        //flipCard();
        initCards();

        binding.btnCompose.setOnClickListener(v -> {

            //融合
        });
    }

    private ViewPageAdapter adapter;
    private ViewPageAdapter adapterChosen;

    void initCards(){
        fragmentList = new ArrayList<>();
        fragmentChosenList = new ArrayList<>();
        cards=new ArrayList<>();
        Card card = new Card();
        card.setStar(1);

        Card card2 = new Card();
        card2.setStar(1);

        cards.add(card);
        cards.add(card2);
        //先前端能拿来看吧
        chosenCards=new ArrayList<>();
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapterChosen = new ViewPageAdapter(getSupportFragmentManager());

        ViewPager viewPager = binding.mViewPager;
        //viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPageListener());

        //binding.mViewPagerChosen.setAdapter(adapterChosen);
        binding.mViewPagerChosen.addOnPageChangeListener(new ViewPageListener());
        updateData();
    }
    private void initData(){
        //car
        if(DataUtil.data ==null){
            DataUtil.data = new ArrayList<>();
        }
        for(int i=0;i<DataUtil.DATA_SIZE;i++){
            DataUtil.data.add("data:"+i);
        }
        fragmentList = new ArrayList<>();
        fragmentChosenList = new ArrayList<>();
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        ViewPager viewPager = binding.mViewPager;
        viewPager.addOnPageChangeListener(new ViewPageListener());
        viewPager.setAdapter(adapter);
        updateData();


    }

    void moveCheckStart(Card remove ){
        Integer star = remove.getStar();
        //View view = chosenList.get(0);
        Card card = chosenCards.get(0);
        if(card.getStar()==null){
            ToastUtil.show(this,"不一样的星 不能移动进来");
            return;
        }
        if(card.getStar().equals(star)){
            chosenCards.add(remove);
        }else{
            ToastUtil.show(this,"不一样的星 不能移动进来");
        }
    }

    void moveToChosen(int index){

        //fragmentList.
        //Card card = cards.get(index);
        Card remove = cards.remove(index);
        //CardFragment cardFragment = new CardFragment();
        ////cardFragment.se
        //cardFragment.setCard(remove);
        //chosenList.add(card);
        if(chosenCards.size()==0){

            chosenCards.add(remove);

        }else{
            moveCheckStart(remove);
            //Integer star = remove.getStar();
            ////View view = chosenList.get(0);
            //Card card = chosenCards.get(0);
            //if(card.getStar().equals(star)){
            //    chosenCards.add(remove);
            //}else{
            //    ToastUtil.show(this,"不一样的星 不能移动进来");
            //}
        }

        updateData();


    }

    void mockCards(){
        int pageSize=3;
        for (int i=0;i<pageSize;i++){
            //fragmentList.add(DataFragment.newInstance(i));
            CardFragment cardFragment = new CardFragment();
            cardFragment.setIndex(i);
            cardFragment.setListener(new CardFragment.Listener() {
                @Override
                public void onCardClicked(int pos) {

                }
            });
            fragmentList.add(cardFragment);
        }

    }
    /**
     * to show data
     */
    public void updateData(){
        //fragmentList.clear();
        //cards.
        //ca
        //int pageSize = DataUtil.data.size()/DataUtil.PER_SIZE;
        //int pageRest = DataUtil.data.size()%DataUtil.PER_SIZE;
        //if(pageRest!=0){
        //    pageSize++;
        //}
        //tvTotalPage.setText("total:\t"+pageSize);
        //数据的列表变了 重新生成 fragment


        fragmentList=new ArrayList<>();
        int idx=0;
        for (Card card : cards) {
            CardFragment cardFragment = new CardFragment();
            cardFragment.setIndex(idx);
            cardFragment.setCard(card);
            idx++;
            cardFragment.setListener(new CardFragment.Listener() {
                @Override
                public void onCardClicked(int pos) {
                    moveToChosen(pos);

                }
            });
            fragmentList.add(cardFragment);
        }
        //adapter = new ViewPageAdapter(getSupportFragmentManager());
        //adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter = new ViewPageAdapter(getSupportFragmentManager(),
                ViewPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentList);
        binding.mViewPager.setAdapter(adapter);

        updateChosen();

        //fragmentChosenList.clear();
        //fragmentChosenList=new ArrayList<>();
        //
        //for (Card card : chosenCards) {
        //    CardFragment cardFragment = new CardFragment();
        //    cardFragment.setIndex(idx);
        //    cardFragment.setCard(card);
        //    idx++;
        //    //cardFragment.setListener(new CardFragment.Listener() {
        //    //    @Override
        //    //    public void onCardClicked(int pos) {
        //    //        moveToChosen(pos);
        //    //    }
        //    //});
        //    fragmentChosenList.add(cardFragment);
        //}
        //
        //
        //adapter = new ViewPageAdapter(getSupportFragmentManager());
        ////fragmentChosenList.clear();
        ////binding.mViewPagerChosen.setAdapter(adapter);
        ////数据清空了 重新来吗
        //
        //
        //adapterChosen = new ViewPageAdapter(getSupportFragmentManager());
        //binding.mViewPagerChosen.setAdapter(adapterChosen);

        //adapter.notifyDataSetChanged();
        //adapterChosen.notifyDataSetChanged();
    }

    void updateChosen(){
        fragmentChosenList=new ArrayList<>();

        int idx=0;
        for (Card card : chosenCards) {
            CardFragment cardFragment = new CardFragment();
            cardFragment.setIndex(idx);
            cardFragment.setCard(card);
            idx++;
            //cardFragment.setListener(new CardFragment.Listener() {
            //    @Override
            //    public void onCardClicked(int pos) {
            //        moveToChosen(pos);
            //    }
            //});
            fragmentChosenList.add(cardFragment);
        }



        //fragmentChosenList.clear();
        //binding.mViewPagerChosen.setAdapter(adapter);
        //数据清空了 重新来吗


                //adapterChosen = new ViewPageAdapter(getSupportFragmentManager());
        adapterChosen = new ViewPageAdapter(getSupportFragmentManager(),
                ViewPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentChosenList);
        binding.mViewPagerChosen.setAdapter(adapterChosen);
    }

    //private List<DataFragment> fragmentList;
    private List<Fragment> fragmentList;
    private List<Fragment> fragmentChosenList;

    //FragmentPagerAdapter fragment 点击
    //private class ViewPageAdapter extends FragmentPagerAdapter {
    //
    //    //这里引用的列表 一样的
    //   //List<Fragment> fragmentList = new ArrayList<>();
    //    public ViewPageAdapter(FragmentManager fm) {
    //        super(fm);
    //    }
    //
    //    @Override
    //    public Fragment getItem(int position) {
    //        return fragmentList.get(position);
    //    }
    //
    //    @Override
    //    public int getCount() {
    //        return fragmentList.size();
    //    }
    //
    //    @Override
    //    public int getItemPosition(Object object) {
    //        return POSITION_NONE;
    //    }
    //
    //    //clic
    //
    //}

    private class ViewPageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        //clic

        @Override
        public void onPageSelected(int position) {
            //tvCurrentPage.setText("Page\t"+(position+1));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    void setUpViewPagerCards(){

        //tabInit();

        //https://www.runoob.com/w3cnote/android-tutorial-viewpager.html
        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        //这不行的 布局有问题
        //这块东西的大小也要定好
        aList.add(li.inflate(R.layout.item_card,null,false));
        aList.add(li.inflate(R.layout.item_card,null,false));
        aList.add(li.inflate(R.layout.item_card,null,false));
        //aList.add(li.inflate(R.layout.view_one,null,false));
        //aList.add(li.inflate(R.layout.view_two,null,false));
        //aList.add(li.inflate(R.layout.view_three,null,false));
        mAdapter = new CardPagerAdapter(aList);
        mAdapter.setListener(new CardPagerAdapter.Listener() {
            @Override
            public void onItemClick(int position) {
                View remove = aList.remove(position);
                chosenList.add(remove);
                //vpager_one.no

                //adapter 的数据变化
                //vpager_one.notify
                mAdapter.notifyDataSetChanged();
                mAdapterChosen.notifyDataSetChanged();
            }
        });
        vpager_one=binding.mViewPager;
        vpager_one.setAdapter(mAdapter);

    }

    void setUpViewPagerChosen(){

        //tabInit();

        //https://www.runoob.com/w3cnote/android-tutorial-viewpager.html
        //aList = new ArrayList<View>();
        chosenList = new ArrayList<View>();
        //LayoutInflater li = getLayoutInflater();
        //这不行的 布局有问题
        //这块东西的大小也要定好
        //aList.add(li.inflate(R.layout.item_card,null,false));
        //aList.add(li.inflate(R.layout.item_card,null,false));
        //aList.add(li.inflate(R.layout.item_card,null,false));
        //aList.add(li.inflate(R.layout.view_one,null,false));
        //aList.add(li.inflate(R.layout.view_two,null,false));
        //aList.add(li.inflate(R.layout.view_three,null,false));
        //mAdapter = new CardPagerAdapter(aList);
        mAdapterChosen = new CardPagerAdapter(chosenList);
        //vpager_one=binding.mViewPager;
        //vpager_one.setAdapter(mAdapter);
        binding.mViewPagerChosen.setAdapter(mAdapterChosen);

    }

    private FrameLayout mFlCardBack, mFlCardFront, mFlContainer;


    // 改变视角距离, 贴近屏幕,这个必须设置，因为如果不这么做，沿着Y轴旋转的过程中有可能产生超出屏幕的3D效果。
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }

    private boolean mIsShowBack = false;

    // 翻转卡片
    public void flipCard() {
        // 正面朝上
        if (!mIsShowBack) {
            inSet.setTarget(mFlCardBack);
            outSet.setTarget(mFlCardFront);
            mIsShowBack = true;
        } else { // 背面朝上
            inSet.setTarget(mFlCardFront);
            outSet.setTarget(mFlCardBack);
            mIsShowBack = false;
        }
        inSet.start();
        outSet.start();
    }

    AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            mFlContainer.setClickable(false);//在动画执行过程中，不许允许接收点击事件
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            mFlContainer.setClickable(true);//在动画执行过程中，不许允许接收点击事件
        }
    };

    private AnimatorSet inSet, outSet;

    private void initAnimatorSet() {
        inSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "rotationY", -180f, 0f);
        animator.setDuration(500);
        animator.setStartDelay(0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(null, "alpha", 0.0f, 1f);
        animator2.setStartDelay(250);
        animator2.setDuration(0);
        inSet.playTogether(animator, animator2);
        inSet.addListener(animatorListenerAdapter);

        outSet = new AnimatorSet();
        ObjectAnimator animator_ = ObjectAnimator.ofFloat(null, "rotationY", 0, 180f);
        animator_.setDuration(500);
        animator_.setStartDelay(0);
        ObjectAnimator animator2_ = ObjectAnimator.ofFloat(null, "alpha", 1f, 0f);
        animator2_.setStartDelay(250);
        animator2_.setDuration(0);
        outSet.playTogether(animator_, animator2_);
        outSet.addListener(animatorListenerAdapter);
    }


    void setFlipCard(){

        mFlCardBack = findViewById(R.id.fl_back);
        mFlCardFront = findViewById(R.id.fl_front);
        mFlContainer = findViewById(R.id.main_fl_container);
        //mFlCardBack.setScaleX(0.5f);
        //mFlCardBack.setScaleY(0.5f);
        //mFlCardFront.setScaleX(0.5f);
        //mFlCardFront.setScaleY(0.5f);
        //整体缩小了 但是有些还是不显示

        mFlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

        initAnimatorSet();
        setCameraDistance(); // 设置镜头距离


    }

   private ViewPager vpager_one;
    private ArrayList<View> aList;
    private ArrayList<View> chosenList;
    private List<Card> chosenCards;
    private CardPagerAdapter mAdapter ;
    private CardPagerAdapter mAdapterChosen ;

    List<Card> cards;


    //ActivityCardBinding binding;
    ActivityCardComposeBinding binding;

    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

//    void initRecyclerView(Activity activity){
//
//        RecyclerView mRecyclerView = binding.recyclerView;
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
//        //RecyclerView view 点击 知道他的内容
////       myAdapter = new MyAdapter(activity);
//
//        myAdapter = new MyAdapter(activity,flag);
//        myAdapter.setFriends(friends);
//        myAdapter.setActivity(activity);
//        myAdapter.init();
//
//        //myAdapter.setRecyclerView(mRecyclerView);
//        mRecyclerView.setAdapter(myAdapter);
//        //设置默认属性
//
//        //myAdapter.setOnItemClickListerner(object :FruitAdapter.OnItemClickListerner{
//        //    override fun onItemClick(position: Int) {
//        //        Toast.makeText(this@MainActivity, "你点击了${frustList.get(position)}", Toast.LENGTH_SHORT).show()
//        //    }
//        //})
////————————————————
////        版权声明：本文为CSDN博主「强强爱学习」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
////        原文链接：https://blog.csdn.net/weixin_42708161/article/details/107919566
//
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        flag=1;
//
//    }

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
