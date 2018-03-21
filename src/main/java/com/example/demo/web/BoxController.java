package com.example.demo.web;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.BookEntry;
import com.example.demo.dao.BookEntryStatus;
import com.example.demo.dao.Box;
import com.example.demo.dao.BoxStatus;
import com.example.demo.dao.Location;
import com.example.demo.repository.BookEntryRepository;
import com.example.demo.repository.BoxRepository;
import com.example.demo.repository.LocationRepository;

@RestController
public class BoxController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoxRepository boxRepository;
	
	@Autowired
	private BookEntryRepository bookEntryRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@RequestMapping(value = "/external/state/{location}/{box}")
	public Map<String, String> action(@PathVariable(name = "location") String locationId,
			@PathVariable(name = "box") String boxId) {
		Map<String, String> action = new HashMap<>();

		Box box = boxRepository.findByIdAndLocationId(boxId, locationId);

		logger.info("box-state: {}", box);
		
		action.put("state", box.getStatus().name());

		return action;
	}
	
	
	@RequestMapping("/external/{boxId}")
	public String bookings(@PathVariable String boxId) {
		Box box = boxRepository.findById(boxId).get();
		
		BookEntry bookEntry = bookEntryRepository.findByBoxAndStatus(box, BookEntryStatus.RUNNING);
		logger.info("PIN: {}", bookEntry.getPin());
		return bookEntry.getPin();
	}
	
	@RequestMapping(value = "/external/button/{location}/{box}")
	public void buttonPressed(@PathVariable(name = "location") String locationId,
			@PathVariable(name = "box") String boxId) {
			Box box = boxRepository.findByIdAndLocationId(boxId, locationId);
					
			if (BoxStatus.DEPOSIT.equals(box.getStatus())) {
				logger.info("box-pressed: {}", box);
				box.setStatus(BoxStatus.BOOKED);
				boxRepository.save(box);
			} else if(BoxStatus.FREE.equals(box.getStatus())) {
				Location location = locationRepository.findById(locationId).get();
				BookEntry bookEntry = new BookEntry(null, box ,new Timestamp(System.currentTimeMillis()), null, location.getOwner(), "", BookEntryStatus.RUNNING);

				bookEntry.setFromDate(new Date());
				bookEntryRepository.save(bookEntry);
				
				box.setStatus(BoxStatus.BOOKED);
				boxRepository.save(box);
			} else {
				logger.info("box-pressed-ignore: {}", box);
			}
			
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
			logger.info("reset: {}", box);
			
			box.setStatus(BoxStatus.FREE);
			
			boxRepository.save(box);
	}
}
