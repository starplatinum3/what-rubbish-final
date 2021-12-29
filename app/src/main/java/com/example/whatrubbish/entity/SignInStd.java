package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class SignInStd {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Integer days;
    String rewardName;
    String  rewardDescribe;
}
