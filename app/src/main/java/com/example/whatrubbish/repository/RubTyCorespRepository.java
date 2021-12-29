
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.RubTyCoresp;

public class RubTyCorespRepository extends BaseRepository<RubTyCoresp> {

    public RubTyCorespRepository(BaseDao<RubTyCoresp> baseDao) {
        super(baseDao);
    }

}
