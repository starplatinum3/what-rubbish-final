
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Rubbish;

public class RubbishRepository extends BaseRepository<Rubbish> {

    public RubbishRepository(BaseDao<Rubbish> baseDao) {
        super(baseDao);
    }

}
