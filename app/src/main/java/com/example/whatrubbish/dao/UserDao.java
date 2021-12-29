package com.example.whatrubbish.dao;

import androidx.room.Dao;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.entity.User;

//创建dao目录
// 创建自己的dao
// 一个dao的示例，如果只要正常的单表的增删查改，不用写任何东西，只要extends BaseDao<User> ，
// 需要注意的是 BaseDao<User> 里面要写自己需要的实体
@Dao
public interface UserDao extends BaseDao<User> {

}
