//package com.example.whatrubbish.base;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
////import android.support.annotation.Nullable;
////import android.support.v7.app.AppCompatActivity;
////import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.Toolbar;
//
////import com.example.asus.detailsnew.R;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.whatrubbish.R;
//
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//
///**
// * Created by asus on 2019/1/7.
// */
//
//public abstract class BaseActivity extends AppCompatActivity {
//    private Context context;
//    private Unbinder unbinder;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(ininLayout());
//        context = this;
//        unbinder = ButterKnife.bind(this);
//        initView();
//    }
//
//    public Context getContext() {
//        return context;
//    }
//
//    protected abstract int ininLayout();
//
//    protected abstract void initView();
//
//    public void initToolbar(Toolbar toolbar, TextView textView, String title, boolean isBack) {
//        if (textView != null && title != null) {
//            textView.setText(title);
//        }
//        if (toolbar != null) {
//            toolbar.setTitle("");
//            if (isBack) {
//                toolbar.setNavigationIcon(R.drawable.ic_left);
//                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });
//            }
//
//        }
//    }
//
//    public void ShowToast(String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//    }
//
//    public void startActivity(Activity activity, Class<? extends Activity> cls) {
//        Intent intent = new Intent();
//        intent.setClass(activity, cls);
//        startActivity(intent);
//        overridePendingTransition(R.animator.up_in,R.animator.up_out);
//    }
//
//    public  void startActivity(Activity activity, Class<? extends Activity> cls, Bundle bundle) {
//        if (activity != null && cls != null && bundle != null) {
//            Intent intent = new Intent();
//            intent.putExtras(bundle);
//            intent.setClass(activity, cls);
//            startActivity(intent);
//            overridePendingTransition(R.animator.up_in,R.animator.up_out);
//        }
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.animator.down_in,R.animator.down_out);
//    }
//}