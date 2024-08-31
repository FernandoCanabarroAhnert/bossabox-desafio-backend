package com.fernandocanabarro.desafio_bossabox_tools.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_bossabox_tools.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User,String>{

    @Query("{ email: ?0 }")
    Optional<User> findByEmail(String email);
}
