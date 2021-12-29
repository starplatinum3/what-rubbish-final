
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Present;

public class PresentRepository extends BaseRepository<Present> {

    public PresentRepository(BaseDao<Present> baseDao) {
        super(baseDao);
    }

}
