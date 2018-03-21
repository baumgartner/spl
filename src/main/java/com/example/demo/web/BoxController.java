package com.example.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.BookEntry;
import com.example.demo.dao.BookEntryStatus;
import com.example.demo.dao.Box;
import com.example.demo.dao.BoxStatus;
import com.example.demo.repository.BookEntryRepository;
import com.example.demo.repository.BoxRepository;

@RestController
public class BoxController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoxRepository boxRepository;
	
	@Autowired
	private BookEntryRepository bookEntryRepository;

	@RequestMapping(value = "/external/state/{location}/{box}")
	public Map<String, String> action(@PathVariable(name = "location") String locationId,
			@PathVariable(name = "box") String boxId) {
		Map<String, String> action = new HashMap<>();

		Box box = boxRepository.findByIdAndLocationId(boxId, locationId);

		logger.info("box-state: {}", box);
		
		action.put("state", box.getStatus().name());

		return action;
	}
	
	
	@RequestMapping(value = "/external/button/{location}/{box}")
	public void buttonPressed(@PathVariable(name = "location") String locationId,
			@PathVariable(name = "box") String boxId) {
			Box box = boxRepository.findByIdAndLocationId(boxId, locationId);
			
			logger.info("box-pressed: {}", box);
						
			if(box.getStatus().equals(BoxStatus.DEPOSIT)) {
				box.setStatus(BoxStatus.BOOKED);
			} else {
				throw new IllegalStateException();
			}
			boxRepository.save(box);
	}

	@RequestMapping(value = "/external/reset/{location}/{box}")
	public void reset(@PathVariable(name = "location") String locationId,
			@PathVariable(name = "box") String boxId) {
			Box box = boxRepository.findByIdAndLocationId(boxId, locationId);
			BookEntry findByBoxAndStatus = bookEntryRepository.findByBoxAndStatus(box, BookEntryStatus.RUNNING);
			if (findByBoxAndStatus != null) {
				findByBoxAndStatus.setStatus(BookEntryStatus.FINISHED);
				bookEntryRepository.save(findByBoxAndStatus);
			}
			logger.info("box-pressed: {}", box);
			
			box.setStatus(BoxStatus.FREE);
			
			boxRepository.save(box);
	}
}
