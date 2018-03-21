package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.dao.Box;
import com.example.demo.dao.BoxStatus;
import com.example.demo.dao.Location;

@SpringBootApplication
public class SplApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplApplication.class, args);
	}
	

}
