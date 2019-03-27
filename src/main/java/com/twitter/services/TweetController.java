package com.twitter.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.entities.Tweet;
import com.twitter.entities.User;
import com.twitter.repositories.TweetRepository;
import com.twitter.repositories.UserRepository;

@RestController
public class TweetController {

	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private TweetRepository TweetRepository;
	
	@RequestMapping(path="/addTweetByUserId", method=RequestMethod.POST) 
	public @ResponseBody String addTweetByUserId 
	(
			@RequestParam Long 		userId		, 
			@RequestParam String 	content		,
			@RequestParam Long 	idCommentTweet	) {
		
		Optional<User> userOptional = UserRepository.findById(userId);
		
		if(!userOptional.isPresent()) return "Fallo al crear Tweet: Usuario no existe";
		
		User user = userOptional.get();
		Tweet tweetComTweet = null;
		if(idCommentTweet != null)
		tweetComTweet = TweetRepository.findById(idCommentTweet).get();
		
		Tweet tweet = new Tweet(user, tweetComTweet);
		
		tweet.setContentTweet(content);
		
		TweetRepository.save(tweet);
		
		return "Usuario Guardado";
	}
	
	@CrossOrigin
	@RequestMapping(path="/addTweetByUsername", method=RequestMethod.POST) 
	public @ResponseBody String addTweetByUsername 
	(
			@RequestParam String	username	, 
			@RequestParam String 	content		,
			@RequestParam Long 	idCommentTweet	) {
		
		User user = UserRepository.findByUsername(username);
		
		if(user == null) return "Fallo al crear Tweet: Usuario no existe";
		
		Tweet tweetComTweet = null;
		if(idCommentTweet != null)
		tweetComTweet = TweetRepository.findById(idCommentTweet).get();
		
		Tweet tweet = new Tweet(user, tweetComTweet);
		
		tweet.setContentTweet(content);
		
		TweetRepository.save(tweet);
		
		return "Tweet Guardado";
	}
	@CrossOrigin
	@RequestMapping("/getAllTweets")
	public Iterable<Tweet> getAllTweets () {
		
		Iterable<Tweet> findAll = TweetRepository.findAll();
		
		return findAll;
	}
	
	
	@RequestMapping(path="/deleteTweetById", method=RequestMethod.POST)
	public @ResponseBody String deleteTweetById 
	(
			@RequestParam Long id) {
		
		TweetRepository.deleteById(id);
		return "Tweet Eliminado con Exito";
	}
	
	
	
}
