package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.Box;
import com.example.demo.dao.Location;

@Repository
public interface BoxRepository extends JpaRepository<Box, String>{
	Collection<Box> findByLocation(Location location);
}
