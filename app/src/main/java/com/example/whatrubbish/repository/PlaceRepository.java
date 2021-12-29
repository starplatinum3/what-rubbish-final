
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Place;

public class PlaceRepository extends BaseRepository<Place> {

    public PlaceRepository(BaseDao<Place> baseDao) {
        super(baseDao);
    }

}
