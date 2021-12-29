package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class CardGame {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Long userId;
    Long cardId;
}
