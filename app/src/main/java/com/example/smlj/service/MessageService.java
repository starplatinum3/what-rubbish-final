package com.example.smlj.service;

import android.app.Activity;
import android.os.Build;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.entity.Message;
import com.example.whatrubbish.jo.RubClsfyNewsJo;
import com.example.whatrubbish.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService {
    Activity activity;
    Repository repository;
    void initDatabase() {
        repository=new Repository(activity);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
   public static JsonObject save(Message message) throws IOException {
        //Gson gson = new Gson();
        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/message/save",message);
        return  post;
        //JsonElement jsonElement = post.get(Bus.contentMark);
        //JsonObject content = post.get(Bus.contentMark).getAsJsonObject();
        ////content.get()
        //Message[] messages = gson.fromJson(content, Message[].class);
        //List<Message> collect = Arrays.stream(messages).collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) throws IOException {
        //MessageService messageService = new MessageService();
        List<Message> list = MessageService.list();
    }

    //void save()

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static  List<Message> list() throws IOException {
        Gson gson = new Gson();
        //Bus.gson.fromJson()
        //JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/message/list", new Message());
        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/message/list", new Message());
        //JsonElement jsonElement = post.get(Bus.contentMark);
        JsonObject content = post.get(Bus.contentMark).getAsJsonObject();
        //content.get()
        Message[] messages = gson.fromJson(content, Message[].class);
        //Object o = gson.fromJson(content, (Type) t);
        List<Message> collect = Arrays.stream(messages).collect(Collectors.toList());
        return collect;
        //List <Message> resLst=new ArrayList<>();
        //Object save = save(Bus.baseDbUrl, Message[].class);
        //Object save = save(Bus.baseDbUrl, Message[].class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    <T>void list(String  url,T t) throws IOException {
        Gson gson = new Gson();
        //JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/message/list", new Message());
        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/message/list", t);
        //JsonElement jsonElement = post.get(Bus.contentMark);
        JsonObject content = post.get(Bus.contentMark).getAsJsonObject();
        //content.get()
        Message[] messages = gson.fromJson(content, Message[].class);
        //Object o = gson.fromJson(content, (Type) t);
        List<Message> collect = Arrays.stream(messages).collect(Collectors.toList());
        //return
        //List<Message> resLst=new ArrayList<>();
        //Object save = save(Bus.baseDbUrl, Message[].class);
        //Object save = save(Bus.baseDbUrl, Message[].class);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    //<T> void save(String  url,Class<?>cls,List<T>resLst) throws IOException {
    <T> void save(String  url,T t,List<T>resLst) throws IOException {
        Gson gson = new Gson();
        //JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/message/list", new Message());
        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/message/list", t);
        //JsonElement jsonElement = post.get(Bus.contentMark);
        JsonObject content = post.get(Bus.contentMark).getAsJsonObject();
        //content.get()
        Object o1 = gson.fromJson(content, (Type) t);
        //Object o = gson.fromJson(content, cls);
        //return o;
        //Object o = gson.fromJson(content, (Type) t);
        //List<Message> collect = Arrays.stream(messages).collect(Collectors.toList());
    }
}
