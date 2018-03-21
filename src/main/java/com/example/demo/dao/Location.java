package com.example.demo.dao;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
	
	

	@Id
	private String id;
	
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="location")
	private Collection<Box> boxes;
}
