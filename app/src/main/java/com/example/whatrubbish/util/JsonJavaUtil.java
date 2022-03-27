package com.example.whatrubbish.util;

//import com.alibaba.fastjson.asm.Type;
//import com.alibaba.fastjson.asm.Type;
import android.util.Log;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;
//import com.squareup.otto.Bus;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

public class JsonJavaUtil {

    //public <T> T jsonArrToObjArr(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
    //    Object object = fromJson(json, (Type) classOfT);
    //    return Primitives.wrap(classOfT).cast(object);
    //}

    public  static  <T> T jsonArrToObjArr(JsonArray json, Class<T> classOfT) throws JsonSyntaxException {
        //Gson gson = new Gson();
        Gson gson =  Bus.gson;
        Object object =gson. fromJson(json, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    //void d(JsonObject jsonObject,){
    //    User user1 = Bus.gson.fromJson(asJsonArray, User.class);
    //}

    public  static <T> T jsonObjToObj(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        Gson gson =  Bus.gson;
        Object object =gson. fromJson(json, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    public  static  JsonObject responseToJsonObject(Response response) throws IOException {

        String string = response.body().string();
        Log.i("string", "responseToJsonObject: "+string);
        //JsonJavaUtil.co
        JsonObject jsonObject = JsonUtil.Companion.strToJsonObject(string);
        return jsonObject;
    }
}
