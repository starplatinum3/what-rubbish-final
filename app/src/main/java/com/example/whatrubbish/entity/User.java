package com.example.whatrubbish.entity;


import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public static final String TABLE_NAME = "User";
    @PrimaryKey(autoGenerate = true)
    Long id;
    String username;
    String password;
    String avatarUrl;
    Integer achievementId;
    Integer score;
    String nickname;
    Integer stageId;
    Integer checkInDays;
    Integer pointCnt;
    String phone;
    String address;
    Long cityId;

    public void addPoint(int point) {
        Log.d("point", "addPoint: " + point);
        if (pointCnt == null) {
            pointCnt = 0;
        }
        pointCnt += point;
        Log.d("pointCnt", "addPoint: " + pointCnt);
    }
//    Integer icon;
//    String name;

//    头像url
//            成就id
//    目前分数
//            昵称
//    到达哪个关卡id（一个关卡就是一个游戏吧）
//    签到几天了
//            有多少积分
}
