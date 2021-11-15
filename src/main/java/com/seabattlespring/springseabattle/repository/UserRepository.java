package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.repository.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String username);

    User getById(String id);

    User getByUserName(String userName);
}
