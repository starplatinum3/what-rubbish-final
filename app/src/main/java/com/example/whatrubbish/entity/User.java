package com.example.whatrubbish.entity;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Cloneable{
    @NonNull
    @Override
    public User clone() throws CloneNotSupportedException {
        return (User)super.clone();
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Integer getCheckInDays() {
        return checkInDays;
    }

    public void setCheckInDays(Integer checkInDays) {
        this.checkInDays = checkInDays;
    }

    public Integer getPointCnt() {
        return pointCnt;
    }

    public void setPointCnt(Integer pointCnt) {
        this.pointCnt = pointCnt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

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
