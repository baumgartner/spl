package com.example.demo.dao;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Box {

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private BoxStatus status;
	
	@ManyToOne
	private Location location;
}
