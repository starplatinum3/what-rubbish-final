
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.SignInHonor;

public class SignInHonorRepository extends BaseRepository<SignInHonor> {

    public SignInHonorRepository(BaseDao<SignInHonor> baseDao) {
        super(baseDao);
    }

}
