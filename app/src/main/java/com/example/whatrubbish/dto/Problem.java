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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<String> candidates) {
        this.candidates = candidates;
    }

    public List<Integer> getAnsNums() {
        return ansNums;
    }

    public void setAnsNums(List<Integer> ansNums) {
        this.ansNums = ansNums;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
