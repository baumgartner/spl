package com.example.demo.web;

import java.security.Principal;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.BookEntry;
import com.example.demo.dao.BookEntryStatus;
import com.example.demo.dao.Box;
import com.example.demo.dao.BoxStatus;
import com.example.demo.dao.Location;
import com.example.demo.dao.User;
import com.example.demo.repository.BookEntryRepository;
import com.example.demo.repository.BoxRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class OverviewController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookEntryRepository bookEntryRepository;
	
	@RequestMapping("/")
    public String redirectWithUsingForwardPrefix() {
        return "redirect:/SPB/locations";
    }

	@PostMapping("/SPB/locations/{locationId}/boxes/{boxId}/book")
	public String book(@PathVariable String locationId, @PathVariable String boxId,
			@RequestParam(name = "code", required = true) String code, Model model, Principal principal) {
		Location location = loadLocation(locationId, model);
		Box box = loadBox(boxId, model);

		BookEntry bookEntry = new BookEntry(null, box ,new Timestamp(System.currentTimeMillis()), null, getLoggedInUser(principal), code, BookEntryStatus.RUNNING);

		bookEntryRepository.save(bookEntry);
		box.setStatus(BoxStatus.DEPOSIT);
		
		boxRepository.save(box);
		
		model.addAttribute("success", "Booked");

		return "box";
	}

	private User getLoggedInUser(Principal principal) {
		return userRepository.findById(principal.getName()).get();
	}

	@PostMapping("/SPB/locations/{locationId}/boxes/{boxId}/unlock")
	public String unlock(@PathVariable String locationId, @PathVariable String boxId,
			@RequestParam(name = "code", required = true) String code, Model model) {

		Location location = loadLocation(locationId, model);
		Box box = loadBox(boxId, model);
		
		BookEntry bookEntry = bookEntryRepository.findByBoxAndStatus(box, BookEntryStatus.RUNNING);
		
		if (bookEntry.getPin().equals(code)) {
			box.setStatus(BoxStatus.FREE);
			boxRepository.save(box);
			
			bookEntry.setStatus(BookEntryStatus.FINISHED);
			bookEntryRepository.save(bookEntry);

			// ONSUCCESS
			model.addAttribute("success", "Unlocked");
		} else {
			model.addAttribute("alert", "invalid code");
		}

		return "box";
	}

	@GetMapping("/SPB/locations")
	public String locations(Model model, Principal principal) {
		logger.info("Principal: {}", principal.getName());
		model.addAttribute("locations", locationRepository.findAll());

		return "locations";
	}

	@GetMapping("/SPB/locations/{locationId}")
	public String location(@PathVariable String locationId, Model model) {
		Location location = loadLocation(locationId, model);
		logger.info("LOCATION- Boxes: " + location.getBoxes().size());
		return "boxes";
	}

	@GetMapping("/SPB/locations/{locationId}/boxes/{boxId}")
	public String box(@PathVariable String locationId, @PathVariable String boxId, Model model) {
		Location location = loadLocation(locationId, model);
		Box box = loadBox(boxId, model);
		
		return "box";
	}

	private Location loadLocation(String locationId, Model model) {
		final Location location = locationRepository.findById(locationId).get();

		model.addAttribute("location", location);

		return location;
	}

	private Box loadBox(String boxId, Model model) {
		final Box box = boxRepository.findById(boxId).get();

		model.addAttribute("box", box);

		return box;
	}
}
