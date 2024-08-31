package com.fernandocanabarro.desafio_bossabox_tools.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_bossabox_tools.entities.Tool;

@Repository
public interface ToolRepository extends MongoRepository<Tool,String>{

    @Query("{ tags: ?0 }")
    Page<Tool> findByTag(String tag,Pageable pageable);
}
