package com.example.whatrubbish.util;

import java.util.List;
import java.util.Random;

public class ListUtil {
    public  static  <T>   T randomGetOne(List<T> list){
        //RoomUtil
        Random rand = new Random();
        int i = rand.nextInt(list.size());
        return   list.get(i);
        //int randomElement = givenList.get(rand.nextInt(givenList.size()));
        //java 列表随机选取一个
    }
}
