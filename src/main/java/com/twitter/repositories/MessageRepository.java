package com.twitter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.twitter.entities.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{
}
