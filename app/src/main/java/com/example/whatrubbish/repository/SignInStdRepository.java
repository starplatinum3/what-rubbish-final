
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.SignInStd;

public class SignInStdRepository extends BaseRepository<SignInStd> {

    public SignInStdRepository(BaseDao<SignInStd> baseDao) {
        super(baseDao);
    }

}
