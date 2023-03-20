package me.lab4.lab4.repositories;

import me.lab4.lab4.models.mongo.Count;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CountRepository extends MongoRepository<Count, ObjectId> {
	Optional<Count> findByURL(String URL);
}