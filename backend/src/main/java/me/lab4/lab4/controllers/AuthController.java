package me.lab4.lab4.controllers;

import jakarta.servlet.http.HttpSession;
import me.lab4.lab4.exceptions.user.IllegalUsername;
import me.lab4.lab4.exceptions.user.NoSuchUser;
import me.lab4.lab4.exceptions.user.UsernameTaken;
import me.lab4.lab4.exceptions.user.WrongPassword;
import me.lab4.lab4.models.Error;
import me.lab4.lab4.models.mongo.User;
import me.lab4.lab4.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class AuthController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@PostMapping("/login")
	public User loginUser(@RequestBody User requestUser,
						  HttpSession session) throws WrongPassword, NoSuchUser {
		User user =
				usersRepository.findUserByName(requestUser.getName()).orElse(null);

		if(user == null) {
			throw new NoSuchUser();
		}

		if(!passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
			throw new WrongPassword();
		}

		session.setAttribute("id", user.getId());
		session.setAttribute("user", user);

		return user;
	}

	@PostMapping("/register")
	public User registerUser(@RequestBody User user, HttpSession session) throws Exception {
		String name = user.getName();

		if(name.equalsIgnoreCase("login") || name.equalsIgnoreCase("register") || name.equalsIgnoreCase("logout")) {
			throw new IllegalUsername(name);
		}

		if(usernameTaken(name)) {
			throw new UsernameTaken();
		}

		if(user.getName().length() < 3) {
			throw new Exception("Минимум 3 буквы в логине");
		}
		if(user.getPassword().length() < 3) {
			throw new Exception("Минимум 3 буквы в пароле");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		usersRepository.save(user);

		session.setAttribute("id", user.getId());
		session.setAttribute("user", user);

		return user;
	}


	@GetMapping("/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	/**
	 * Возвращает true, если пользователь с таким именем уже есть.
	 */
	private boolean usernameTaken(String username) {
		return usersRepository.findUserByName(username).isPresent();
	}

	@ExceptionHandler
	public Error handleException(Exception e) {
		return new Error(e.getLocalizedMessage());
	}
}
