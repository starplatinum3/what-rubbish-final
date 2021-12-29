package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class ShootGame {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Long userId;
    Integer shootCnt;
    Integer goalCnt;
}
