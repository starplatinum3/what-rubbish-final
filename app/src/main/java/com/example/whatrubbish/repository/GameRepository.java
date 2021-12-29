
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Game;

public class GameRepository extends BaseRepository<Game> {

    public GameRepository(BaseDao<Game> baseDao) {
        super(baseDao);
    }

}
