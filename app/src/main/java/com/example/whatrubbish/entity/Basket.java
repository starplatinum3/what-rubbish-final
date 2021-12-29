package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Basket {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String  imgRes;
    Long rubTypeId;
}
