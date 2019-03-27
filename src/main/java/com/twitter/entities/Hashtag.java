package com.twitter.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hashtag")
public class Hashtag {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_hashtag")
	private long id;
	
	@Column(name = "hashtag", unique = true)
	String hashtag;
	
	@Column(name = "emoji")
	String emoji;

	
	@ManyToMany(mappedBy = "listHashtag")
	private Set<Tweet> listTweet = new HashSet<Tweet>();
}
