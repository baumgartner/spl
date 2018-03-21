package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.Location;
import com.example.demo.dao.User;

@Repository
public interface LocationRepository extends JpaRepository<Location, String>{
	
	Collection<Location> findAllByUser(User user);

}
