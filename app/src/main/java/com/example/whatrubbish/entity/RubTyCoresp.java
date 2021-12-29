package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class RubTyCoresp {
    @PrimaryKey(autoGenerate = true)
    Long id;
//    垃圾类型对应表
//            id
//    垃圾id
//            垃圾类型id
//    城市id

    Long rubbishId;
    Long rubTypeId;
    Long cityId;
}
