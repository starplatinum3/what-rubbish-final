package com.example.whatrubbish.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.databinding.FragmentRubbishBinding;
import com.example.whatrubbish.databinding.FragmentRubbishTypeBinding;
import com.example.whatrubbish.databinding.FragmentRubbishTypeConsBinding;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.util.DrawUtil;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class RubbishTypeFragment extends Fragment {

    //FragmentRubbishTypeBinding binding;
    FragmentRubbishTypeConsBinding binding;

    //RubbishTypeFragment 显示在前端的是一个RubbishType 数据结构
    //展示了
    //String name;
    ////垃圾类型的名字
    //String describe;
    ////描述
    //String throwRequirement;
    ////投放要求
    //String imgUrl;
    ////图标id
    RubbishType rubbishType;

    public RubbishTypeFragment(RubbishType rubbishType) {
        this.rubbishType = rubbishType;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static final String  TAG = "ImageFragment";

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个修改为对应子Fragment和父Fragment的布局文件
//        R.id
//        binding = FragmentRubbishTypeBinding.inflate(inflater, container, false);
        binding = FragmentRubbishTypeConsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        View view = inflater.inflate(R.layout.father_fragment,null);

        binding.tvDescribe.setText(rubbishType.getDescribe());
        //binding.tvType.setText(rubbishInfo.getType());
        binding.tvName.setText(rubbishType.getName());
        binding.tvThrowRequirement.setText(rubbishType.getThrowRequirement());
        //binding.img.setText(rubbishType.getImgUrl());
        //DrawUtil.loadImage(getActivity(),rubbishType.getImgUrl(),binding.img);

        //Bus
        try {
            RequestOptions coverRequestOptions = new RequestOptions().
                    transform(new CenterInside())//, roundedCorners
//                .error(R.mipmap.default_img)//加载错误显示
//                .placeholder(R.mipmap.default_img)//加载中显示
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全部
//                .skipMemoryCache(true)
                    ;//不做内存缓存

            Glide.with(getActivity()).load(rubbishType.getImgUrl()).
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    apply(coverRequestOptions).into(binding.img);
        }catch (Exception e){
            e.printStackTrace();
        }


        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
//————————————————
//    版权声明：本文为CSDN博主「不知 不知」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/qq_41858698/article/details/105452276
}
