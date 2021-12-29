
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.City;

public class CityRepository extends BaseRepository<City> {

    public CityRepository(BaseDao<City> baseDao) {
        super(baseDao);
    }

}
