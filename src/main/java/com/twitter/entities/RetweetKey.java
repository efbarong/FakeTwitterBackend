package com.twitter.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable 
public class RetweetKey implements Serializable {
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tweet", nullable = false)
	private Tweet tweet;
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RetweetKey)) return false;
		
		RetweetKey o = (RetweetKey) obj;
		
		if(this.user != null? !this.user.equals(o.user): o.user != null)
			return false;
		if(this.tweet != null? !this.tweet.equals(o.tweet): o.tweet != null)
			return false;
		
		return true;
	}
	
	public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 43 * result + (tweet != null ? tweet.hashCode() : 0);
        return result;
    }
}
