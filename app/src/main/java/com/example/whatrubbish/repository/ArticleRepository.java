
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Article;

public class ArticleRepository extends BaseRepository<Article> {

    public ArticleRepository(BaseDao<Article> baseDao) {
        super(baseDao);
    }

}
