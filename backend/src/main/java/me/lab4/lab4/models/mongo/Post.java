package me.lab4.lab4.models.mongo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("posts")
public class Post {

	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;

	private String content;

	@DBRef
	private User author;

	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime writtenAt;

	@DocumentReference(lazy = true)
	private List<User> likes;

	@DBRef
	private Post replyTo;

	public Post getReplyTo() {
		return replyTo;
	}

	public void handleLike(User liker) {
		boolean isLikedByLiker =
				this.getLikes().stream().anyMatch(user -> user.getId().equals(liker.getId()));

		if(isLikedByLiker) {
			this.likes.removeIf(user -> user.getId().equals(liker.getId()));
		} else {
			this.likes.add(liker);
		}
	}

	public List<User> getLikes() {
		return likes;
	}

	public ObjectId getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public LocalDateTime getWrittenAt() {
		return writtenAt;
	}

	public void setWrittenAt(LocalDateTime writtenAt) {
		this.writtenAt = writtenAt;
	}
}
