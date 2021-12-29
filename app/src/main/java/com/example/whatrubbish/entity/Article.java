package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Article {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String title;
    String picRes;
}
