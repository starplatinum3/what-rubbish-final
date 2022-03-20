package com.example.whatrubbish.util

import com.google.gson.Gson
import com.google.gson.JsonObject

class JsonUtil {

    companion object{
//        D9(){
////        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
//        }
//

        fun strToJsonObject(string: String): JsonObject? {
            val gson=  Gson()
//            gson.fromJson<>()
//            gson kotlin
//            https://www.jianshu.com/p/af66079e2cf5
//           val jsonObject= gson.fromJson<JsonObject>(string,JsonObject.class.java)
           val jsonObject= gson.fromJson(string,JsonObject::class.java)
//            JsonObject jsonObject = gson.fromJson(res, JsonObject.class);\
            return jsonObject
        }
    }

}