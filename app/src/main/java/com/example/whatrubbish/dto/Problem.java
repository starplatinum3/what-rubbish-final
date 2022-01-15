package com.example.whatrubbish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Problem {
//    int id;

    Integer id;
    String title;

    List<String> candidates;

    List<Integer> ansNums;
    //    正确答案是 哪些
//    List<Answer >answers;
    String analysis;


}
