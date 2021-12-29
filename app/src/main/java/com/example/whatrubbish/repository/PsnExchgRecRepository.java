
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.PsnExchgRec;

public class PsnExchgRecRepository extends BaseRepository<PsnExchgRec> {

    public PsnExchgRecRepository(BaseDao<PsnExchgRec> baseDao) {
        super(baseDao);
    }

}
