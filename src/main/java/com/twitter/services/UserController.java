package com.twitter.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.entities.Message;
import com.twitter.entities.Tweet;
import com.twitter.entities.User;
import com.twitter.repositories.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepositoryDAO;
	
	@RequestMapping("/getAllUsers")
	public Iterable<User> getAllUsers () {
		
		Iterable<User> findAll = userRepositoryDAO.findAll();
		
		return findAll;
	}
	
	@RequestMapping(path="/addUser", method=RequestMethod.POST) 
	public @ResponseBody String addNewUser 
	(
			@RequestParam String 	username	, 
			@RequestParam String 	name		,
			@RequestParam String 	email		,
			@RequestParam Long 		cellphone	, 
			@RequestParam String 	password	) {
		
		User newUser = new User(username);
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setCellphone(cellphone);
		newUser.setPassword(password);
		
		userRepositoryDAO.save(newUser);
		return "Usuario Guardado";
	}
	
	
	@RequestMapping(path="/getUserById", method=RequestMethod.POST) 
	public @ResponseBody User getUserById
	(
			@RequestParam Long id) {
		
		Optional<User> user = userRepositoryDAO.findById(id);
		
		return user.get();
	}
	
	@CrossOrigin
	@RequestMapping(path="/getUserByUsername", method=RequestMethod.GET)
	public User getUserByUsername 
	(
			@RequestHeader String username) {
		
		User user = userRepositoryDAO.findByUsername(username);
		
		return user;
	}
	
	@CrossOrigin
	@RequestMapping(path="/validatedUser", method=RequestMethod.POST)
	public @ResponseBody User validatedUser 
	(
			@RequestParam String username, @RequestParam String password) {
		
		User user = userRepositoryDAO.findByUsernameAndPassword(username, password);
		return user;
	}
	
	
	@RequestMapping(path="/deleteUserById", method=RequestMethod.POST)
	public @ResponseBody String deleteUserById 
	(
			@RequestParam Long id) {
		
		userRepositoryDAO.deleteById(id);
		return "Usuario Eliminado con Exito";
	}
	
	
	@RequestMapping(path="/getAllTweetByUserId", method=RequestMethod.POST)
	public @ResponseBody Set<Tweet> getAllTweetByUserId 
	(
			@RequestParam Long id) {
		
		Optional<User> userOptional = userRepositoryDAO.findById(id);
		
		if(!userOptional.isPresent()) return null;
		return userOptional.get().getTweets();
	}
	
	@RequestMapping(path="/getAllTweetByUsername", method=RequestMethod.POST)
	public @ResponseBody Set<Tweet> getAllTweetByUsername 
	(
			@RequestParam String username) {
		
		User user = userRepositoryDAO.findByUsername(username);
		
		if(user == null) return null;
		return user.getTweets();
	}
	
	
	@RequestMapping(path="/getAllFollowersByUserId", method=RequestMethod.POST)
	public @ResponseBody Set<User> getAllFollowersByUserId 
	(
			@RequestParam Long id) {
		
		Optional<User> userOptional = userRepositoryDAO.findById(id);
		
		if(!userOptional.isPresent()) return null;
		return userOptional.get().getFollowers();
	}
	
	@RequestMapping(path="/getAllFollowersByUsername", method=RequestMethod.POST)
	public @ResponseBody Set<User> getAllFollowersByUsername 
	(
			@RequestParam String username) {
		
		User user = userRepositoryDAO.findByUsername(username);
		
		if(user == null) return null;
		return user.getFollowers();
	}
	
	@RequestMapping(path="/followById", method=RequestMethod.POST)
	public @ResponseBody String followById 
	(
			@RequestParam Long idFollow,
			@RequestParam Long idFollowed) {
		
		Optional<User> opFollow = userRepositoryDAO.findById(idFollow);
		Optional<User> opFollowed = userRepositoryDAO.findById(idFollowed);
		
		if(!opFollow.isPresent())   return "Error al seguir: usuario follow no existe";
		if(!opFollowed.isPresent()) return "Error al seguir: usuario followed no existe";
		
		User follow   = opFollow.get();
		User followed = opFollowed.get();
		
		follow.followUser(followed);
		followed.followedUser(follow);
		
		userRepositoryDAO.save(follow);
		userRepositoryDAO.save(followed);
		
		return "Usuario seguido correctamente!";
	}
	
	
	@RequestMapping(path="/getAllMessageSendedByUserId", method=RequestMethod.POST)
	public @ResponseBody Set<Message> getAllMessageSendedByUserId 
	(
			@RequestParam Long id) {
		
		Optional<User> userOptional = userRepositoryDAO.findById(id);
		
		if(!userOptional.isPresent()) return null;
		return userOptional.get().getMessageSend();
	}
	
	@RequestMapping(path="/getAllMessageReceptedByUserId", method=RequestMethod.POST)
	public @ResponseBody Set<Message> getAllMessageReceptedByUserId 
	(
			@RequestParam Long id) {
		
		Optional<User> userOptional = userRepositoryDAO.findById(id);
		
		if(!userOptional.isPresent()) return null;
		return userOptional.get().getMessageReceptor();
	}
	
}
