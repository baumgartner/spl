package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.Box;
import com.example.demo.dao.BoxStatus;
import com.example.demo.dao.Location;
import com.example.demo.dao.User;
import com.example.demo.repository.BoxRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;

@Component
public class TestDataInserter implements InitializingBean {

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		Collection<User> users = new ArrayList<>();
		users.add(new User("jakob", "jakob"));
		users.add(new User("martin", "martin"));
		users.add(new User("florian", "florian"));
		User franz = new User("franz", "franz");
		users.add(franz);
		userRepository.saveAll(users);

		Location hauptplatz = new Location("HAU", "Hauptplatz", null, null);
		Location rathaus = new Location("BHF", "Bahnhof", null, null);
		Location lagerfranz = new Location("LFR", "Lager Franz", null, franz );
		locationRepository.save(hauptplatz);
		locationRepository.save(rathaus);
		locationRepository.save(lagerfranz);

		Box box1 = new Box("HAU001", BoxStatus.FREE, hauptplatz);
		Box box2 = new Box("HAU002", BoxStatus.FREE, hauptplatz);
		Box box3 = new Box("BHF001", BoxStatus.FREE, rathaus);
		Box box4 = new Box("BHF002", BoxStatus.FREE, rathaus);
		Box box5 = new Box("BHF003", BoxStatus.FREE, rathaus);
		Box box6 = new Box("LFR001", BoxStatus.FREE, lagerfranz);
		
		boxRepository.save(box1);
		boxRepository.save(box2);
		boxRepository.save(box3);
		boxRepository.save(box4);
		boxRepository.save(box5);
		boxRepository.save(box6);
	}

}
