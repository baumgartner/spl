package com.example.demo.dao;

import java.util.Collection;
import java.util.stream.Collectors;

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
	
	public int getFreeBoxes() {
		return getBoxes(BoxStatus.FREE).size();
	}
	
	private Collection<Box> getBoxes(BoxStatus status) {
		return boxes.stream().filter( box -> status.equals(box.getStatus())).collect(Collectors.toList());
	}
}
