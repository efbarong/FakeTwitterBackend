package com.twitter.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class MessageKey implements Serializable{

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_sender")
	private User userSender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_receptor", nullable = false)
	private User userReceptor;
	
	public MessageKey() {
	}
	
	public MessageKey(User sender, User receptor) {
		this.userSender   = sender;
		this.userReceptor = receptor;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MessageKey)) return false;
		
		MessageKey o = (MessageKey) obj;
		
		if(this.userSender != null? !this.userSender.equals(o.userSender): o.userSender != null)
			return false;
		if(this.userReceptor != null? !this.userReceptor.equals(o.userReceptor): o.userReceptor != null)
			return false;
		
		return true;
	}
	
	public int hashCode() {
        int result;
        result = (userSender != null ? userSender.hashCode() : 0);
        result = 47 * result + (userReceptor != null ? userReceptor.hashCode() : 0);
        return result;
    }
	
}
