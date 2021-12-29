
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Basket;

public class BasketRepository extends BaseRepository<Basket> {

    public BasketRepository(BaseDao<Basket> baseDao) {
        super(baseDao);
    }

}
