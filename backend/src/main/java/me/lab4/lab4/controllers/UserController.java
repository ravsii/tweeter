package me.lab4.lab4.controllers;

import jakarta.servlet.http.HttpSession;
import me.lab4.lab4.exceptions.image.WrongType;
import me.lab4.lab4.exceptions.session.InvalidSession;
import me.lab4.lab4.exceptions.user.NoSuchUser;
import me.lab4.lab4.exceptions.user.NotEnoughPower;
import me.lab4.lab4.models.Error;
import me.lab4.lab4.models.Role;
import me.lab4.lab4.models.mongo.User;
import me.lab4.lab4.repositories.UsersRepository;
import me.lab4.lab4.services.FileStorageService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	FileStorageService fileStorageService;
	@Autowired
	private UsersRepository usersRepository;

	@GetMapping
	public List<User> getAllUsers() {
		return usersRepository.findAll();
	}

	@GetMapping("/{name}")
	public User getUser(@PathVariable String name) throws NoSuchUser {
		return usersRepository.findUserByName(name).orElseThrow(NoSuchUser::new);
	}


	@PostMapping("/uploadProfilePicture")
	public User handleFileUpload(@RequestParam("pfp") MultipartFile file,
								 HttpSession session) throws InvalidSession, WrongType, NoSuchUser {
		ObjectId userId = (ObjectId) session.getAttribute("id");

		if(userId == null) {
			throw new InvalidSession();
		}

		String type = file.getContentType();

		if(type == null || !type.startsWith("image/")) {
			throw new WrongType();
		}

		User user = usersRepository.findById(userId).orElseThrow(NoSuchUser::new);
		fileStorageService.save(file, userId.toString());
		user.pfpUploaded();
		usersRepository.save(user);

		return user;
	}

	@PostMapping("/{id}/role")
	public User handleForceUpdate(@PathVariable ObjectId id,
								  @RequestBody User userWithRole,
								  HttpSession session) throws NoSuchUser, NotEnoughPower {
		User invoker = (User) session.getAttribute("user");
		if(invoker == null || !invoker.isAdmin()) {
			throw new NotEnoughPower();
		}


		User user = usersRepository.findById(id).orElseThrow(NoSuchUser::new);
		if(userWithRole.getRole() == Role.SUPERADMIN || user.getRole() == Role.SUPERADMIN ||
				userWithRole.getRole() == user.getRole()) {
			throw new NotEnoughPower();
		}

		user.setRole(userWithRole.getRole());
		usersRepository.save(user);

		return user;
	}

	@ExceptionHandler
	public Error handleException(Exception e) {
		return new Error(e.getLocalizedMessage());
	}
}
