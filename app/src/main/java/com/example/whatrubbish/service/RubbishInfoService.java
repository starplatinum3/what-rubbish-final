package com.example.whatrubbish.service;

import com.ejlchina.okhttps.GsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.dbo.RubbishInfoDbo;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.util.HttpUtil;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.HttpUrl;

public class RubbishInfoService {
    //api 拿到数据之后要 保存
   public static JsonObject save(RubbishInfo rubbishInfo) throws IOException {
//        HTTP http = HTTP.builder()
////                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
//                .baseUrl(Bus.baseDbUrl)
//                .addMsgConvertor(new GsonMsgConvertor())
//                .build();
        //Bus.httpDb.async()
        JsonObject post = HttpUtil.post(Bus.baseDbUrl+"/rubbishInfo/save", rubbishInfo);
        return post;
    }

    public static JsonObject save(RubbishInfoDbo rubbishInfoDbo) throws IOException {
//        HTTP http = HTTP.builder()
////                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
//                .baseUrl(Bus.baseDbUrl)
//                .addMsgConvertor(new GsonMsgConvertor())
//                .build();
        //Bus.httpDb.async()
        JsonObject post = HttpUtil.post(Bus.baseDbUrl+"/rubbishInfo/save", rubbishInfoDbo);
        return post;
    }
}
