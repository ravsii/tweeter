package me.lab4.lab4.controllers;

import jakarta.servlet.http.HttpSession;
import me.lab4.lab4.exceptions.post.NoSuchPost;
import me.lab4.lab4.exceptions.user.NoSuchUser;
import me.lab4.lab4.exceptions.user.NotEnoughPower;
import me.lab4.lab4.models.Error;
import me.lab4.lab4.models.mongo.News;
import me.lab4.lab4.models.mongo.User;
import me.lab4.lab4.repositories.NewsRepository;
import me.lab4.lab4.repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("news")
public class NewsController {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	NewsRepository newsRepository;

	@GetMapping
	public @ResponseBody List<News> newsList() {
		return newsRepository.findAllByOrderByWrittenAtDesc();
	}

	@GetMapping("/{id}/like")
	public @ResponseBody News like(@PathVariable ObjectId id,
								   HttpSession session) throws NoSuchPost, NoSuchUser {
		News news = newsRepository.findById(id).orElseThrow(NoSuchPost::new);
		User user = (User) session.getAttribute("user");

		if(user == null) {
			throw new NoSuchUser();
		}

		news.handleLike(user);
		newsRepository.save(news);
		return news;
	}

	@PostMapping
	public News newPost(@RequestBody News news, HttpSession session) throws NotEnoughPower {
		User user = (User) session.getAttribute("user");

		if(user == null || !user.isMod()) {
			throw new NotEnoughPower();
		}

		news.setWrittenAt(LocalDateTime.now());
		news.setAuthor(user);
		newsRepository.save(news);
		return news;
	}

	@ExceptionHandler
	public Error handleException(Exception e) {
		return new Error(e.getLocalizedMessage());
	}
}
