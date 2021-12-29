
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.GameHonor;

public class GameHonorRepository extends BaseRepository<GameHonor> {

    public GameHonorRepository(BaseDao<GameHonor> baseDao) {
        super(baseDao);
    }

}
