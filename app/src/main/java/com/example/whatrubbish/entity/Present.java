package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Present {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String name;
    String describe;
    Double price;
    Integer imgRes;
    Integer needPoint;

}
