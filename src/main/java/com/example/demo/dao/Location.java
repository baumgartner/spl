package com.example.demo.dao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Location {

	@Id
	private String id;
	
	private String name;

	@ManyToOne
	private List<Box> boxes;
}
