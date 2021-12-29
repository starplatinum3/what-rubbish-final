package com.example.whatrubbish.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whatrubbish.databinding.FragmentImageBinding;
import com.example.whatrubbish.databinding.FragmentTextBinding;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class TextFragment extends Fragment {

    FragmentTextBinding binding;
//    int imgId;
    String text;

    public TextFragment(String text) {
        this.text = text;
    }

    public TextFragment() {
    }

    //    public int getImgId() {
//        return imgId;
//    }
//
//    public void setImgId(int imgId) {
//        this.imgId = imgId;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个修改为对应子Fragment和父Fragment的布局文件
//        R.id
        binding = FragmentTextBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.textView.setText(text);
//        View view = inflater.inflate(R.layout.father_fragment,null);
//        binding.textView.setImageResource(imgId);
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
