package me.lab4.lab4.repositories;

import me.lab4.lab4.models.mongo.News;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NewsRepository extends MongoRepository<News, ObjectId> {
	List<News> findAllByOrderByWrittenAtDesc();
}