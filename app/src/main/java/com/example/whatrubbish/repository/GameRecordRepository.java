
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.GameRecord;

public class GameRecordRepository extends BaseRepository<GameRecord> {

    public GameRecordRepository(BaseDao<GameRecord> baseDao) {
        super(baseDao);
    }

}
