package com.example.demo.dao;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Location {

	@Id
	private String id;
	
	private String name;

	@OneToMany
	private Collection<Box> boxes;
}
