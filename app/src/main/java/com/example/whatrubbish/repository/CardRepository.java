
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Card;

public class CardRepository extends BaseRepository<Card> {

    public CardRepository(BaseDao<Card> baseDao) {
        super(baseDao);
    }

}
