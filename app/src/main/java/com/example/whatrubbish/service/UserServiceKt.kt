package com.example.whatrubbish.service

import android.util.Log
import com.example.whatrubbish.Bus
import okhttp3.*
import java.io.IOException

class UserServiceKt {
    companion object{
        fun userInit(): Call {
            val okHttpClient = OkHttpClient()

//            val usernameStr=username.value
//            val passwordStr=password.value

//            val formBody = FormBody.Builder()
//                .add("client_id","v-client")
//                .add("client_secret","v-client-ppp")
//                .add("grant_type","password")
//                .add("scope","select")
//                .add("username",usernameStr)
//                .add("password",passwordStr).build()
           val access_token= Bus.token["access_token"].asString
            Log.i("access_token", "userInit: $access_token ")
            val getRequest: Request =
                Request.Builder() //.url("https://api.github.com/markdown/raw")
                    .url(Bus.baseDbUrl+"/api/user/init") //.addHeader()
                    .addHeader("Authorization","bearer $access_token")
                    .post( FormBody.Builder().build())
                    .build()


            val newCall = okHttpClient.newCall(getRequest)
            return newCall
//            Bus.baseDbUrl+ "/api/user/init"
//            newCall.enqueue(object :Callback{
//                override fun onFailure(call: Call, e: IOException) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    TODO("Not yet implemented")
//                }
//
//            })
        }
    }
}