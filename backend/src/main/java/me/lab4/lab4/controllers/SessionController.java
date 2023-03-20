package me.lab4.lab4.controllers;

import jakarta.servlet.http.HttpSession;
import me.lab4.lab4.models.mongo.User;
import me.lab4.lab4.repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("session")
public class SessionController {

	@Autowired
	private UsersRepository usersRepository;


	@GetMapping
	public Optional<User> validateSession(HttpSession session) {
		ObjectId userId = (ObjectId) session.getAttribute("id");

		if(userId == null) {
			return Optional.empty();
		}

		User user = usersRepository.findById(userId).orElse(null);

		if(user == null) {
			return Optional.empty();
		}

		session.setAttribute("id", user.getId());
		session.setAttribute("user", user);

		return Optional.of(user);
	}
}