package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.Box;
import com.example.demo.dao.BoxStatus;
import com.example.demo.dao.Location;
import com.example.demo.repository.BoxRepository;
import com.example.demo.repository.LocationRepository;

@Component
public class TestDataInserter implements InitializingBean {

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private BoxRepository boxRepository;

	@Override
	public void afterPropertiesSet() throws Exception {

		Location hauptplatz = new Location("HAU", "Hauptplatz", null);
		Location rathaus = new Location("RAT", "Rathaus", null);
		locationRepository.save(hauptplatz);
		locationRepository.save(rathaus);

		Box box1 = new Box("HAU001", BoxStatus.FREE, hauptplatz);
		Box box2 = new Box("HAU002", BoxStatus.FREE, hauptplatz);
		Box box3 = new Box("RAT001", BoxStatus.FREE, rathaus);
		Box box4 = new Box("RAT002", BoxStatus.FREE, rathaus);
		
		boxRepository.save(box1);
		boxRepository.save(box2);
		boxRepository.save(box3);
		boxRepository.save(box4);


	}

}
