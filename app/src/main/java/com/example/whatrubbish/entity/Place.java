package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Place {
    @PrimaryKey(autoGenerate = true)
    long id;
    String  placeName;

}
