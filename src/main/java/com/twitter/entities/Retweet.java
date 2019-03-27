package com.twitter.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "retweet")
public class Retweet{
	@EmbeddedId
	private RetweetKey id;
	
	@Column(name = "contect_reply")
	private String contentReply;
	
	@Column(name = "date_retweet", nullable = false)
	private Date dateRetweet;
}
