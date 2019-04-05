package com.twitter.entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "tweet")
public class Tweet {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_tweet")
	private Long idTweet;
	
	@Column(name = "content_tweet")
	private String contentTweet;

	@Column(name = "date_tweet", nullable = false)
	private Timestamp dateTweet;

	@Column(name = "attachment")
	private String attachment;

	
	//****************************************************************
	
	
//	@OneToOne
	@ManyToOne
	@JoinColumn(name = "user_tweet", nullable = false)
//	@JsonIdentityReference
	@JsonManagedReference
//	@JsonBackReference
	private User userTweet;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_tweet", nullable = true)
	@JsonIgnore
	private Tweet commentTweet;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="commentTweet")
	@JsonIgnore
	private Set<Tweet> comments = new HashSet<Tweet>();
	
	
	@ManyToMany(targetEntity=Hashtag.class, fetch=FetchType.LAZY)
	@JoinTable(
	        name = "tweet_hashtag", 
	        joinColumns = { @JoinColumn(name = "id_tweet") }, 
	        inverseJoinColumns = { @JoinColumn(name = "id_hashtag") }
	    )
	private Set<Hashtag> listHashtag = new HashSet<Hashtag>();
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.tweet")
	@JsonManagedReference
	private Set<Retweet> retweets;
	
	@ManyToMany(mappedBy = "likes")
	@JsonIgnore

	private Set<User> userLikes = new HashSet<User>();
	
	
	
	//-------------------------------------------------------------------

	
	public Tweet() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
		Timestamp actual = new Timestamp(System.currentTimeMillis());
		this.dateTweet = new Timestamp(Timestamp.valueOf(actual.toString()).getTime());
		System.out.println(dateTweet);
	}

	public Tweet(User user, Tweet tweetComTweet) {
		
		this();
		this.userTweet = user;
//		this.username = user.getUsername();
		this.commentTweet = tweetComTweet;
	}

	public String getContentTweet() {
		return contentTweet;
	}

	public void setContentTweet(String contentTweet) {
		this.contentTweet = contentTweet;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Long getIdTweet() {
		return idTweet;
	}

	public Timestamp getDateTweet() {
		return dateTweet;
	}

	public User getUserTweet() {
		return userTweet;
	}

	public Tweet getCommentTweet() {
		return commentTweet;
	}

	public Set<Tweet> getComments() {
		return comments;
	}

	public Set<Hashtag> getListHashtag() {
		return listHashtag;
	}

	public Set<Retweet> getRetweets() {
		return retweets;
	}

	public Set<User> getUserLikes() {
		return userLikes;
	}

	public void setUserTweet(User userTweet) {
		this.userTweet = userTweet;
	}

	
}
