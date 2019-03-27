package com.twitter.repositories;

import org.springframework.data.repository.CrudRepository;

import com.twitter.entities.Hashtag;

public interface HashtagRepository extends CrudRepository<Hashtag, Long>{

}
