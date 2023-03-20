package me.lab4.lab4.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("time")
public class TimeController {

	@GetMapping
	public long currentTime() {
		return Instant.now().getEpochSecond();
	}
}
