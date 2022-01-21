package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rubbish {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String name;
    Long typeId;
    String imgUrl;
    Long cityId;

}
