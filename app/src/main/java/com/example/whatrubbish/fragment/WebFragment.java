package com.example.whatrubbish.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatrubbish.databinding.FragmentNotificationsBinding;
import com.example.whatrubbish.databinding.FragmentWebBinding;
import com.example.whatrubbish.ui.notifications.NotificationsViewModel;

public class WebFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentWebBinding binding;

    public WebFragment(String url) {
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

    void initWeb(){

        WebView browser=   binding.Toweb;
        //WebView
        //WebView browser=(WebView)findViewById(R.id.Toweb);
        //browser.loadUrl("http://www.baidu.com");
        //browser.loadUrl("https://www.baidu.com");
        //browser.loadUrl("http://www.baidu.com");
        Log.i("url", "initWeb: "+url);
        browser.loadUrl(url);

        //设置可自由缩放网页
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setBuiltInZoomControls(true);

        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}