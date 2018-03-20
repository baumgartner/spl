package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.Auto;
import com.example.demo.repository.AutoRepository;

@Component
public class Starter implements InitializingBean{

	@Autowired AutoRepository repository; 
	
	@Override
	public void afterPropertiesSet() throws Exception {
		repository.save(new Auto());
	}

}
