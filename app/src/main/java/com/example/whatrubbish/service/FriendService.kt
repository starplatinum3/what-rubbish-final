package com.example.whatrubbish.service

import com.example.whatrubbish.entity.Response
import com.example.whatrubbish.wanandroid.ProjectResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendService {
//    获取的是 。。
    @GET("/project/list/{page}/json")
    suspend fun getProjects(@Path("page") page:Int, @Query("cid") id:Int): ProjectResponse

    @POST("/user/list")
    suspend fun getUsers(): Response
}
