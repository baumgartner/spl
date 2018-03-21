package com.example.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.Box;
import com.example.demo.dao.BoxStatus;
import com.example.demo.repository.BoxRepository;

@RestController
public class BoxController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoxRepository boxRepository;

	@RequestMapping(value = "/external/state/{location}/{box}")
	public Map<String, String> action(@PathVariable(name = "location") String locationId,
			@PathVariable(name = "box") String boxId) {
		Map<String, String> action = new HashMap<>();

		Box box = boxRepository.findByIdAndLocationId(boxId, locationId);

		logger.info("box: {}", box);
		
		action.put("state", box.getStatus().name());

		return action;
	}
	
	
	@RequestMapping(value = "/external/button/{location}/{box}")
	public void buttonPressed(@PathVariable(name = "location") String locationId,
			@PathVariable(name = "box") String boxId) {
			Box box = boxRepository.findByIdAndLocationId(boxId, locationId);
			
			if(box.getStatus().equals(BoxStatus.DEPOSIT)) {
				box.setStatus(BoxStatus.BOOKED);
			} else {
				throw new IllegalStateException();
			}
			boxRepository.save(box);
	}

}
