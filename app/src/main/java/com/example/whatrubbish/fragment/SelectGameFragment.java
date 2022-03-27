package com.example.whatrubbish.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.FragmentSelectGameBinding;
import com.example.whatrubbish.databinding.FragmentWebBinding;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.ui.notifications.NotificationsViewModel;

/**
 * 天上掉下垃圾的游戏
 */
public class SelectGameFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    //private FragmentWebBinding binding;
    private FragmentSelectGameBinding binding;

    //Activity activity;
    FragmentActivity activity;
    //public SelectGameFragment(String url) {
    //    this.url = url;
    //}


    //public SelectGameFragment(FragmentActivity activity) {
    //    this.activity = activity;
    //}

    String url;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSelectGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

         activity = getActivity();
        binding.btnToWxSorting.setOnClickListener(v->{
            String url = "http://139.196.8.79:8890/";
            //activity.getSupportFragmentManager().beginTransaction().
            //        replace(R.id.nav_host_fragment_activity_main, new SplitDropRubFragment(url)).
            //        addToBackStack(null).commit();
            this.activity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.holder,new SplitDropRubFragment(url))
                    .addToBackStack(null).commit();
            //getChildFragmentManager();

        });

        return root;
    }

    //go back
    //@Override
    //public boolean onKeyDown(int keyCode, KeyEvent event) {
    //    WebView browser=(WebView)findViewById(R.id.Toweb);
    //    // Check if the key event was the Back button and if there's history
    //    if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
    //        browser.goBack();
    //        return true;
    //    }
    //    //  return true;
    //    // If it wasn't the Back key or there's no web page history, bubble up to the default
    //    // system behavior (probably exit the activity)
    //    return super.onKeyDown(keyCode, event);
    //}
    //@SuppressLint("JavascriptInterface")


    //js调用安卓，必须加@JavascriptInterface注释的方法才可以被js调用
    @JavascriptInterface
    public User getCurUser() {
        //可以传给js im 的话 也可以用他的账号密码登陆  但是这里的密码是md5 的
        Log.i("qcl0228", "js调用了安卓的方法");
        return Bus.curUser;
    }

    //void initWeb(){
    //
    //    WebView browser=   binding.Toweb;
    //    //WebView
    //    //WebView browser=(WebView)findViewById(R.id.Toweb);
    //    //browser.loadUrl("http://www.baidu.com");
    //    //browser.loadUrl("https://www.baidu.com");
    //    //browser.loadUrl("http://www.baidu.com");
    //    browser.loadUrl(url);
    //
    //    //设置可自由缩放网页
    //    browser.getSettings().setSupportZoom(true);
    //    browser.getSettings().setBuiltInZoomControls(true);
    //
    //    // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
    //    // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
    //    browser.setWebViewClient(new WebViewClient() {
    //        @Override
    //        public boolean shouldOverrideUrlLoading(WebView view, String url)
    //        {
    //            //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
    //            view.loadUrl(url);
    //            return true;
    //        }
    //    });
    //}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}