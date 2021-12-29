package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Entity
@Data
public class PsnExchgRec {
//    礼品的兑换记录
//            id
//    用户id
//            礼品id
//    time

    @PrimaryKey(autoGenerate = true)
    Long id;
    String userId;
    String presentId;
//    Date time;
    Long time;

}
