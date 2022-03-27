package com.example.whatrubbish.repository

import com.example.whatrubbish.service.FriendService
import com.example.whatrubbish.service.ProjectService
import com.example.whatrubbish.util.ServiceCreator

object Repository {
   suspend fun getProjects(page:Int,id:Int) = ServiceCreator.create<ProjectService>().getProjects(page,id)
   suspend fun getFriends() = ServiceCreator.create<FriendService>().getUsers()
}
