
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.RubbishType;

public class RubbishTypeRepository extends BaseRepository<RubbishType> {

    public RubbishTypeRepository(BaseDao<RubbishType> baseDao) {
        super(baseDao);
    }

}
