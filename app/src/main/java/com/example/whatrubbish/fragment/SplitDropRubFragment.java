package com.example.whatrubbish.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.databinding.FragmentWebBinding;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.ui.notifications.NotificationsViewModel;

/**
 * 天上掉下垃圾的游戏
 */
public class SplitDropRubFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentWebBinding binding;

    public SplitDropRubFragment(String url) {
        this.url = url;
    }

    String url;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentWebBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        initWeb();

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
    @SuppressLint("SetJavaScriptEnabled")
    void initWeb() {
        WebView webview=   binding.Toweb;

        //String url = "file:///android_asset/h5demo2.html";
        //String url = "http://139.196.8.79:8890/";
        webview.loadUrl(url);
        //1，js调用安卓
        webview.getSettings().setJavaScriptEnabled(true);//这里必须开启
        //把当前JavaH5Activity对象作为androidObject别名传递给js
        //js通过window.androidObject.androidMethod()就可以直接调用安卓的androidMethod方法
        webview.addJavascriptInterface(this, "androidObject");

    }

    //应该不能直接传递一个 java的类吧
    //js调用安卓，必须加@JavascriptInterface注释的方法才可以被js调用
    @JavascriptInterface
    public String  getCurUser() {
        //他需要token 吗
        Log.i("qcl0228", "js调用了安卓的方法");
        String json = Bus.gson.toJson(Bus.curUser);
        //return Bus.curUser;
        //或者拿到账号密码直接登陆得了
        Log.i("json", "getCurUser: "+json);
        return json;
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