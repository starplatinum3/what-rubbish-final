//package com.example.whatrubbish.base;
//
////import android.arch.lifecycle.MutableLiveData;
////import android.arch.lifecycle.ViewModel;
//import android.util.Log;
//
////import com.example.asus.detailsnew.http.manager.HttpManager;
////import com.example.asus.detailsnew.http.manager.NewsApi;
////import com.example.asus.detailsnew.ui.bean.DataBean;
////import com.example.asus.detailsnew.ui.bean.Result;
////import com.example.asus.detailsnew.ui.bean.ResultBean;
//
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import java.util.List;
//
////import retrofit2.Call;
////import retrofit2.Callback;
////import retrofit2.Response;
////
///**
// * Created by asus on 2019/1/14.
// */
//public class DataViewModel extends ViewModel {
//    MutableLiveData<List<DataBean>> liveData = new MutableLiveData<>();
//    HttpManager manager = new HttpManager();
//    NewsApi api = manager.getRetrofit().create(NewsApi.class);
//
//    public MutableLiveData<List<DataBean>> getLiveData(String type) {
//        api.getNews(type, "bc0a3a53be1e97115c2313e638662cae").enqueue(new Callback<Result<ResultBean<DataBean>>>() {
//            @Override
//            public void onResponse(Call<Result<ResultBean<DataBean>>> call, Response<Result<ResultBean<DataBean>>> response) {
//                if (response != null) {
//                    liveData.setValue(response.body().getResult().getData());
//                   // Log.i("是否有数据",response.body().getData().size()+"");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Result<ResultBean<DataBean>>> call, Throwable t) {
//
//            }
//        });
//        return liveData;
//    }
//}