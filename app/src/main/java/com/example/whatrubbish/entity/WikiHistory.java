package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Entity
@Data
public class WikiHistory {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String content ;
    Long userId;
//    Date time;
    Long time;
}
