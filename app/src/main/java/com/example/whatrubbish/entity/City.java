package com.example.whatrubbish.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Builder
@ToString
public class City implements  Cloneable{
    @PrimaryKey(autoGenerate = true)
    Long id;
    String name;
    String picRes;
    String describe;
    Integer fragFullNeed;

    public City() {
    }

    @Ignore
    public City(Long id, String name, String picRes, String describe, Integer fragFullNeed) {
        this.id = id;
        this.name = name;
        this.picRes = picRes;
        this.describe = describe;
        this.fragFullNeed = fragFullNeed;
    }

    @NonNull
    @Override
    public City clone()  {
        City city = null;
        try{
            city = (City)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return city;
//        return super.clone();
    }

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

    public String getPicRes() {
        return picRes;
    }

    public void setPicRes(String picRes) {
        this.picRes = picRes;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getFragFullNeed() {
        return fragFullNeed;
    }

    public void setFragFullNeed(Integer fragFullNeed) {
        this.fragFullNeed = fragFullNeed;
    }
}
