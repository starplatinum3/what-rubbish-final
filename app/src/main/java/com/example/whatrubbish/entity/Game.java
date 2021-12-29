package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Game {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String  name;
    String describe;
    String  picUrl;
}
