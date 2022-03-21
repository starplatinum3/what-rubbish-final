package com.example.whatrubbish.util

import com.example.whatrubbish.entity.ImUser
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

class JsonUtil {

    companion object{
//        D9(){
////        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
//        }
//

        /**
         * fromJson2List
         */
        inline fun <reified T> fromJson2List(json: String) = fromJson<List<T>>(json)

        /**
         * fromJson
         */
        inline fun <reified T> fromJson(json: String): T? {
            return try {
                val type = object : TypeToken<T>() {}.type
//               val gson=  Gson()
//                gson.fromJson<ImUser>("")
                return Gson().fromJson(json, type)
            } catch (e: Exception) {
                println("try exception,${e.message}")
                null
            }
        }

//        inline fun <reified T> jsonToJsonObj(json: ): T? {
//            return try {
//                val type = object : TypeToken<T>() {}.type
//                return Gson().fromJson(json, type)
//            } catch (e: Exception) {
//                println("try exception,${e.message}")
//                null
//            }
//        }

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