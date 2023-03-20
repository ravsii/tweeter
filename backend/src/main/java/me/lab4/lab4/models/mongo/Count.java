package me.lab4.lab4.models.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("page_count")
public class Count {
	@Id
	private String URL;
	private Integer counter;

	public Count(String URL, Integer counter) {
		this.URL = URL;
		this.counter = counter;
	}

	public String getURL() {
		return URL;
	}

	public Integer getCounter() {
		return counter;
	}

	public void Increment() {
		this.counter++;
	}
}
