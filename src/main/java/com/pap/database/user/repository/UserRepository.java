package com.pap.database.user.repository;

import com.pap.database.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsUserByUsernameAndPassword(final String username, final String password);
    boolean existsUserByUsername(final String username);
}
