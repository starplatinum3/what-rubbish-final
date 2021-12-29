package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Rubbish {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String name;
    Long typeId;
    String imgUrl;
    Long cityId;

}
