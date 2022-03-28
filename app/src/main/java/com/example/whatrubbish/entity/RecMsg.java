package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;


@Entity
@Data
public class RecMsg {
    @PrimaryKey(autoGenerate = true)
    Integer id;
    String title;
    String msg;
    Date date;
}
