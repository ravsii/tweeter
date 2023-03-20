package me.lab4.lab4.controllers;

import jakarta.servlet.http.HttpSession;
import me.lab4.lab4.exceptions.post.NoSuchPost;
import me.lab4.lab4.exceptions.session.InvalidSession;
import me.lab4.lab4.exceptions.user.NoSuchUser;
import me.lab4.lab4.exceptions.user.NotEnoughPower;
import me.lab4.lab4.models.Error;
import me.lab4.lab4.models.mongo.Post;
import me.lab4.lab4.models.mongo.User;
import me.lab4.lab4.repositories.PostsRepository;
import me.lab4.lab4.repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	PostsRepository postsRepository;

	@GetMapping
	public @ResponseBody List<Post> postsList(@RequestParam(required = false) String user) throws NoSuchUser {
		if(user != null) {
			User author =
					usersRepository.findUserByName(user).orElseThrow(NoSuchUser::new);
			return postsRepository.findAllByAuthorIdOrderByWrittenAtDesc(author.getId());
		}

		return postsRepository.findAllByOrderByWrittenAtDesc();
	}

	@GetMapping("/{id}/like")
	public @ResponseBody Post like(@PathVariable ObjectId id, HttpSession session) throws NoSuchUser, InvalidSession, NoSuchPost {
		Post post = postsRepository.findById(id).orElseThrow(NoSuchPost::new);
		ObjectId authorId = (ObjectId) session.getAttribute("id");

		if(authorId == null) {
			throw new InvalidSession();
		}

		User liker =
				usersRepository.findById(authorId).orElseThrow(NoSuchUser::new);

		post.handleLike(liker);
		postsRepository.save(post);

		return post;
	}

	@PostMapping
	public Post newPost(@RequestBody Post post, HttpSession session) throws InvalidSession, NoSuchUser {
		ObjectId authorId = (ObjectId) session.getAttribute("id");

		if(authorId == null) {
			throw new InvalidSession();
		}

		User author =
				usersRepository.findById(authorId).orElseThrow(NoSuchUser::new);

		post.setWrittenAt(LocalDateTime.now());
		post.setAuthor(author);

		postsRepository.save(post);

		return post;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deletePost(@PathVariable ObjectId id,
											  HttpSession session) throws NotEnoughPower {
		User user = (User) session.getAttribute("user");

		if(user == null || !user.isMod()) {
			throw new NotEnoughPower();
		}

		postsRepository.deleteById(id);

		return ResponseEntity.ok(true);
	}

	@ExceptionHandler
	public Error handleException(Exception e) {
		return new Error(e.getLocalizedMessage());
	}
}
