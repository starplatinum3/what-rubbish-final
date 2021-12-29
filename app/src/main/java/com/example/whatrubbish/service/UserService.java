package com.example.whatrubbish.service;

import android.app.Activity;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.dbo.RubbishInfoDbo;
import com.example.whatrubbish.entity.Article;
import com.example.whatrubbish.entity.CardGame;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.util.HttpUtil;
import com.google.gson.JsonObject;

import java.io.IOException;

public class UserService {

    Activity activity;
    Repository repository;
    void initDatabase() {
        repository=new Repository(activity);
    }


    void  registerHttp(){

    }


    void sendMessage(){
//        Message message = new Message();
//        message.setContent();
//        RoomUtil.
        AppDatabase database = AppDatabase.getDatabase(activity);
//        articleRepository = new ArticleRepository(database.articleDao());
        BaseDao<Article> baseDao;
//        BaseRepository<Article> articleBaseRepository=new BaseRepository<>(new BaseDao<Article>);
        BaseRepository<CardGame> cardGameBaseRepository=new BaseRepository<>(database.cardGameDao());
//        cardGameBaseRepository.insert();
//        repository.getArticleRepository().insert(message);

    }
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
