package me.lab4.lab4.repositories;

import me.lab4.lab4.models.mongo.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User, ObjectId> {
	Optional<User> findUserByName(String name);
}