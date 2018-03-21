package com.example.demo.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; 
	
	@ManyToOne
	private Box box; 
	
//	private Date from; 
//	
//	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
//	private DateTime to;
//	
	@ManyToOne
	private User user;
	
	private String pin; 
	
//	private 

}
