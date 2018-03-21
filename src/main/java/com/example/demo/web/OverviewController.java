package com.example.demo.web;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		loadLocation(locationId, model);
		Box box = loadBox(boxId, model);
		
		if (StringUtils.isEmpty(code)) {
			model.addAttribute("alert", "alert");
			return "box";
		}

		BookEntry bookEntry = new BookEntry(null, box ,new Timestamp(System.currentTimeMillis()), null, getLoggedInUser(principal), code, BookEntryStatus.RUNNING);

		bookEntry.setFromDate(new Date());
		bookEntryRepository.save(bookEntry);
		box.setStatus(BoxStatus.DEPOSIT);
		
		boxRepository.save(box);
		
		model.addAttribute("success", "Booked");

		return "box";
	}
	
	private User getLoggedInUser(Principal principal) {
		return userRepository.findById(principal.getName()).get();
	}

	@GetMapping("SPB/locations/{locationId}/boxes/{boxId}/unlock")
	@PostMapping("/SPB/locations/{locationId}/boxes/{boxId}/unlock")
	public String unlock(@PathVariable String locationId, @PathVariable String boxId,
			@RequestParam(name = "code") String code, Model model, Principal principal) {

		Location location = loadLocation(locationId, model);
		Box box = loadBox(boxId, model);
		User user = getLoggedInUser(principal);
		
		BookEntry bookEntry = bookEntryRepository.findByBoxAndStatus(box, BookEntryStatus.RUNNING);
		
		if (user.equals(location.getOwner()) || bookEntry.getPin().equals(code)) {
			box.setStatus(BoxStatus.FREE);
			boxRepository.save(box);
			
			bookEntry.setStatus(BookEntryStatus.FINISHED);
			bookEntry.setToDate(new Date());
			bookEntryRepository.save(bookEntry);

			// ONSUCCESS
			model.addAttribute("success", "Unlocked");
			model.addAttribute("ms", (bookEntry.getToDate().getTime() - bookEntry.getFromDate().getTime()) / 1000);
		} else {
			model.addAttribute("alert", "invalid code");
		}

		return "box";
	}

	@GetMapping("/SPB/locations")
	public String locations(Model model, Principal principal) {
		logger.info("Principal: {}", principal.getName());
		model.addAttribute("publicLocations", locationRepository.findAll());
		model.addAttribute("privateLocations", locationRepository.findAllByOwner(getLoggedInUser(principal)));
		return "locations";
	}

	@GetMapping("/SPB/locations/{locationId}")
	public String location(@PathVariable String locationId, Model model) {
		Location location = loadLocation(locationId, model);
		logger.info("LOCATION- Boxes: " + location.getBoxes().size());
		return "boxes";
	}

	@GetMapping("/SPB/locations/{locationId}/boxes/{boxId}")
	public String box(@PathVariable String locationId, @PathVariable String boxId, Model model, Principal principal) {
		loadLocation(locationId, model);
		loadBox(boxId, model);
		loadCurrentUser(model, principal);
		
		return "box";
	}

	private void loadCurrentUser(Model model, Principal principal) {
		User loggedInUser = getLoggedInUser(principal);
		
		model.addAttribute("currentUser", loggedInUser);
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
