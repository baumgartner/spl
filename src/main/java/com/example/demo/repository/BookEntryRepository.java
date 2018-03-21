package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.BookEntry;
import com.example.demo.dao.BookEntryStatus;
import com.example.demo.dao.Box;

@Repository
public interface BookEntryRepository extends JpaRepository<BookEntry, Integer>{
	
	BookEntry findByBoxAndStatus(Box box, BookEntryStatus status);
}
