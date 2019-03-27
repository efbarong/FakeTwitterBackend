package com.twitter.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.entities.Message;
import com.twitter.entities.Tweet;
import com.twitter.entities.User;
import com.twitter.repositories.MessageRepository;
import com.twitter.repositories.UserRepository;

@RestController
public class MessageController {

	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepositoryDAO;
	
	
	@RequestMapping("/getAllMessages")
	public Iterable<Message> getAllUsers () {
		
		Iterable<Message> findAll = messageRepository.findAll();
		
		return findAll;
	}
	
	@RequestMapping(path="/sendMessageById", method=RequestMethod.POST)
	public @ResponseBody String sendMessageById 
	(
			@RequestParam Long idSender,
			@RequestParam Long idReceptor,
			@RequestParam String content) {
		
		Optional<User> opSender = userRepositoryDAO.findById(idSender);
		Optional<User> opReceptor = userRepositoryDAO.findById(idReceptor);
		
		if(!opSender.isPresent())   return "Error al seguir: usuario sender no existe";
		if(!opReceptor.isPresent()) return "Error al seguir: usuario receptor no existe";
		
		User sender   = opSender.get();
		User receptor = opReceptor.get();
		
		Message message = new Message(sender, receptor);
		message.setContentMessage(content);
		messageRepository.save(message);
		
		return "Mensaje enviado correctamente!";
	}
	
}
