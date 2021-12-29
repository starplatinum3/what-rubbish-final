
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.WikiHistory;

public class WikiHistoryRepository extends BaseRepository<WikiHistory> {

    public WikiHistoryRepository(BaseDao<WikiHistory> baseDao) {
        super(baseDao);
    }

}
