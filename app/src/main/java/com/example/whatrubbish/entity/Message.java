package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.whatrubbish.util.HttpUtil;

import lombok.Data;

@Entity
@Data
public class Message {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Long fromId;
    Long toId;
    Long content;
    Long time;

}
