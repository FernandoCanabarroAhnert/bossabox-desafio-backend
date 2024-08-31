package com.fernandocanabarro.desafio_bossabox_tools.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_bossabox_tools.entities.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role,String>{

    @Query("{ authority: ?0 }")
    Role findByAuthority(String authority);
}
