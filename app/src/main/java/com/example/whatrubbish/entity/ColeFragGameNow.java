package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class ColeFragGameNow {
    @PrimaryKey(autoGenerate = true)
    Long id;
//    收集碎片游戏
//            id
//    用户id
//    碎片id（垃圾id
//            数量

    Long userId;
    Long rubId;
    Integer cnt;


}
