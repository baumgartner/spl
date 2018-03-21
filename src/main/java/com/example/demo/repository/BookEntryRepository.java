package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.BookEntry;

@Repository
public interface BookEntryRepository extends JpaRepository<BookEntry, Integer>{
	
//	@Query("SELECT p FROM BookEntry p WHERE p.box = :box")
//	BookEntry findByBox(Box box);
}
