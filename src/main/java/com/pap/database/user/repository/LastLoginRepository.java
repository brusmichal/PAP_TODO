package com.pap.database.user.repository;

import com.pap.database.user.LastLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastLoginRepository extends MongoRepository<LastLogin,String> {
    LastLogin findTopByOrderByLastLoginTime();
}
