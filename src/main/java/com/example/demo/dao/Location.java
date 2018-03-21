package com.example.demo.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Location {

	@Id
	private String id;
	
	private String name;

}
