package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Entity
@Data
public class SignInHonor {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Long userId;
    String honor;
//    Date time;
    Long time;
}
