package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.Location;
import com.example.demo.repository.BookEntryRepository;
import com.example.demo.repository.BoxRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class OverviewController {
	
	@Autowired
	private BoxRepository boxRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookEntryRepository bookEntryRepository;
	
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name = "username", required = true) String username, 
			@RequestParam(name = "password", required = true) String password,
			Model model) {
		return "success";
	}
	
	@PostMapping("/SPB/locations/{locationId}/boxes/{box}/book")
	public String book(@PathVariable String locationId,
			@PathVariable String box,
			@RequestParam(name = "code", required = true) String code, Model model) {
		Location location = loadLocation(locationId, model);
		// TODO: create BookEntry
		// 
		return "book";
	}
	
	@PostMapping("/SPB/locations/{locationId}/boxes/{box}/unlock")
	public String unlock(@PathVariable String locationId,
			@PathVariable String box,
			@RequestParam(name = "code", required = true) String code, Model model) {
		
		Location location = loadLocation(locationId, model);
		
		// TODO: check if box has a pendingt BookEntry
		// TODO: check Code
		// TODO: send 
		// TODO: finish booking
		return "unlock";
	}
	
	@GetMapping("/SPB/locations")
	public String locations(Model model) {
		model.addAttribute("locations", locationRepository.findAll());
		
		return "locations";
	}
	
	@GetMapping("/SPB/location/{locationId}/boxes")
	public String boxes(@PathVariable String locationId, Model model) {
		Location location = loadLocation(locationId, model);	
		boxRepository.findByLocation(location);		
		return "boxes";
	}
	
	private Location loadLocation(String locationId, Model model) {
		final Location location = locationRepository.findById(locationId).get();
		
		model.addAttribute("location", location);
		
		return location;
	}
			
}
