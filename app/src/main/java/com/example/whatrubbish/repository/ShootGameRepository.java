
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.ShootGame;

public class ShootGameRepository extends BaseRepository<ShootGame> {

    public ShootGameRepository(BaseDao<ShootGame> baseDao) {
        super(baseDao);
    }

}
