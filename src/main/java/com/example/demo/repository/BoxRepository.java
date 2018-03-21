package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, String>{

}
