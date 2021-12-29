package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Card {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String  name;
    String describe;
    String  imgUrl;
    String recogLabel;
//    卡片
//            id
//    名字
//            描述
//    图片id
//            识别标签

}
