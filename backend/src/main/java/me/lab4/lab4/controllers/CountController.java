package me.lab4.lab4.controllers;

import me.lab4.lab4.models.mongo.Count;
import me.lab4.lab4.repositories.CountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("count")
public class CountController {

	@Autowired
	private CountRepository countRepository;

	@GetMapping
	private Count count(@RequestParam String URL) {
		Count counter = countRepository.findByURL(URL).orElse(null);

		if(counter == null) {
			counter = new Count(URL, 1);
		} else {
			counter.Increment();
		}

		countRepository.save(counter);
		return counter;
	}
}
