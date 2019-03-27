package com.twitter.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class) 
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String 	username;

	@Column(nullable = false)
	private String 	name;
	@Column(nullable = false)
	private String 	email;
	@Column(nullable = false)
	private long 	cellphone;
	@Column(nullable = false)
	private String 	password;
	@Column
	private Date 	birthday;
	@Column(name = "profile_picture_url")
	private String 	profilePictureUrl;
	@Column
	private String 	Location;


	
	//********************************************************************
	
	@OneToMany (fetch=FetchType.LAZY, mappedBy ="userTweet")
//	@JsonManagedReference
	@JsonBackReference
//	@JsonIgnore
	private Set<Tweet> tweets = new HashSet<Tweet>();
	
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.user")
	private Set<Retweet> retweets;

	
	
	@ManyToMany(targetEntity=Tweet.class, fetch=FetchType.LAZY)
	@JoinTable(
	        name = "like_tweet", 
	        joinColumns = { @JoinColumn(name = "id_user") }, 
	        inverseJoinColumns = { @JoinColumn(name = "id_tweet") }
	    )
	private Set<Tweet> likes = new HashSet<Tweet>();
	
	
	
	/*
	 * The people that we follow
	 */
	@ManyToMany(targetEntity=User.class, fetch=FetchType.LAZY)
	@JoinTable(
	        name = "follows", 
	        joinColumns = { @JoinColumn(name = "id_user_follow") }, 
	        inverseJoinColumns = { @JoinColumn(name = "id_user_follower") }
	    )
	@JsonBackReference
	private Set<User> followed = new HashSet<User>();
	
	
	/*
	 * The people that follow us
	 */
	@ManyToMany(mappedBy = "followed")
	@JsonIgnore
	private Set<User> followers = new HashSet<User>();
	
	
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userSender")
	private Set<Message> messageSend;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userReceptor")
	private Set<Message> messageReceptor;
	
	
	
	
	//-------------------------------------------------------------------------

	public User() {
	}
	
	public User(String username) {
		this.username = username;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCellphone() {
		return cellphone;
	}

	public void setCellphone(long cellphone) {
		this.cellphone = cellphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Set<Tweet> getTweets() {
		return tweets;
	}
	
	@Override
	public String toString() {
		return username;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public boolean followUser(User follow) {
		return this.followed.add(follow);
	}
	
	public boolean followedUser(User followed) {
		return this.followers.add(followed);
	}

	public boolean sendMessage(Message message) {
		return this.messageSend.add(message);
	}

	public boolean receptMessage(Message message) {
		return this.messageReceptor.add(message);
	}

	public Set<Message> getMessageSend() {
		return messageSend;
	}

	public Set<Message> getMessageReceptor() {
		return messageReceptor;
	}

	
}
