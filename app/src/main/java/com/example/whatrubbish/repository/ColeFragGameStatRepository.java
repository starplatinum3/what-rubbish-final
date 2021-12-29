
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.ColeFragGameStat;

public class ColeFragGameStatRepository extends BaseRepository<ColeFragGameStat> {

    public ColeFragGameStatRepository(BaseDao<ColeFragGameStat> baseDao) {
        super(baseDao);
    }

}
