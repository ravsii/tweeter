package me.lab4.lab4.repositories;

import me.lab4.lab4.models.mongo.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostsRepository extends MongoRepository<Post, ObjectId> {
	List<Post> findAllByOrderByWrittenAtDesc();

	List<Post> findAllByAuthorIdOrderByWrittenAtDesc(ObjectId id);
}