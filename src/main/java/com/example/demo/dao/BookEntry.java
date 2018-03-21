package com.example.demo.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromDate; 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date toDate;
	
	@ManyToOne
	private User user;
	
	private String pin; 
	
	@Enumerated(EnumType.STRING)
	private BookEntryStatus status;

}
