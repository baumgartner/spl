package com.example.demo.dao;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="location")
public class Box {

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private BoxStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	private Location location;


}
