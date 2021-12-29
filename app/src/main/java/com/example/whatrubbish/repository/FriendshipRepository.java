
package com.example.whatrubbish.repository;

import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.entity.Friendship;

public class FriendshipRepository extends BaseRepository<Friendship> {

    public FriendshipRepository(BaseDao<Friendship> baseDao) {
        super(baseDao);
    }

}
