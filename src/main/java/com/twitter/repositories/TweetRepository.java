package com.twitter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.twitter.entities.*;
@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long>{

	
}