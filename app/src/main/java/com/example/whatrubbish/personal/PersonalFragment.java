package com.example.whatrubbish.personal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityAchievementActiSelfBinding;
import com.example.whatrubbish.databinding.FragmentHomeBinding;
import com.example.whatrubbish.databinding.FragmentPersonalBinding;
import com.example.whatrubbish.gridIcons.GridIcons;
import com.example.whatrubbish.gridIcons.IconButton;
import com.example.whatrubbish.tab.Tab;
import com.example.whatrubbish.ui.home.HomeViewModel;
import com.example.whatrubbish.util.DrawUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment {


//    FragmentPersonalBinding binding;

//    private ActivityAchievementActiSelfBinding binding;
    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager mviewPager;
    //    private com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment.MyAdapter myAdapter;
//    private AchievementFragment.MyAdapter myAdapter;
    private MyAdapter myAdapter;

    private Context mContext;

    private void initView(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.mTabLayout);
        mviewPager = (ViewPager) rootView.findViewById(R.id.mViewPager);
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }


    private PersonalViewModel personalViewModel;
//    private FragmentDashboardBinding binding;
    private FragmentPersonalBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalViewModel =
                new ViewModelProvider(this).get(PersonalViewModel.class);

//        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding = FragmentPersonalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        stringList.add("勋章");
        stringList.add("好友");
        stringList.add("主播电台");
//        文档：错误 程序包android.support.design.wi...
//        链接：http://note.youdao.com/noteshare?id=e0525aebe47888e70c5fea891cb1670b&sub=CAC7FEC1D7474FABB943A4A987959933
//        for (String s : stringList) {
//            fragmentList.add(new MainFragment(s));
//        }
//        在里面会 add 外面 不要 add

        ImageView avatar =  binding.avatar;

//        ImageView avatar = findViewById(R.id.avatar);
//        avatar
        DrawUtil.setRoundImageDrawable(avatar,R.drawable.miku_fang,getResources(),90);

//        initView();
        initView(root);

        FragmentActivity activity = getActivity();
        Tab tab = new Tab();
//        tab.setActivity(this);
        tab.setActivity(activity);
//        Required type:
//        AppCompatActivity
//        Provided:
//        FragmentActivity
        tab.setTabLayout(mTabLayout);
        tab.setFragments(fragmentList);
        tab.setTitles(stringList);
        tab.setViewPager(mviewPager);
        tab.tabInit();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}