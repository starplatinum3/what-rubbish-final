package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.User;

public class UserRepository extends BaseRepository<User> {

    public UserRepository(BaseDao<User> baseDao) {
        super(baseDao);
    }

}
