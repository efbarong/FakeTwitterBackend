package com.twitter.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_message")
	private Long idMessage;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_sender")
	private User userSender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_receptor", nullable = false)
	private User userReceptor;
	
	@Column(name = "content_message", nullable = false)
	private String contentMessage;
	
	@Column(name = "content_url")
	private String contentUrl;
	
	@Column(name = "date_message", nullable = false)
	private Date dateMessage;
	
	public Message() {
	}
	
	public Message(User sender, User receptor) {
		this.userSender   = sender;
		this.userReceptor = receptor;
		this.dateMessage = new Date(System.currentTimeMillis());
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getContentMessage() {
		return contentMessage;
	}

	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}
}
