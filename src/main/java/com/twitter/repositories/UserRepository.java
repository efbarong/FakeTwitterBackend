package com.twitter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.twitter.entities.*;
@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public User findById(long id);
	public User findByUsername(String username);
	public User findByUsernameAndPassword(String username, String password);
}
