
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.CardGame;

public class CardGameRepository extends BaseRepository<CardGame> {

    public CardGameRepository(BaseDao<CardGame> baseDao) {
        super(baseDao);
    }

}
