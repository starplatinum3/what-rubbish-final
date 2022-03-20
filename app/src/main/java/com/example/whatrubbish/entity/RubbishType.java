package com.example.whatrubbish.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
public class RubbishType {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String name;
    //垃圾类型的名字
    String describe;
    //描述
    String throwRequirement;
    //投放要求
    String imgUrl;
    //图标id
    public RubbishType() {
    }

    @Ignore
    public RubbishType(Long id, String name, String describe, String throwRequirement, String imgUrl) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.throwRequirement = throwRequirement;
        this.imgUrl = imgUrl;
    }
    //    id
//            名字
//    描述
//            投放要求
//    图标id


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getThrowRequirement() {
        return throwRequirement;
    }

    public void setThrowRequirement(String throwRequirement) {
        this.throwRequirement = throwRequirement;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
