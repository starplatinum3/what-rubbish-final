package com.example.whatrubbish.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Entity
@Data
public class GameRecord {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Long userId;
    Long score;
    Long gameId;
//    Date time;
    Long time;

}
