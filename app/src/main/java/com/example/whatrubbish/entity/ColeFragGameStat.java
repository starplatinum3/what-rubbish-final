package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class ColeFragGameStat {
    @PrimaryKey(autoGenerate = true)
    Long id;
//    收集碎片游戏统计
//id
//        用户id
//    拼出的垃圾id
    Long userId;
    Long haveRubId;

}
