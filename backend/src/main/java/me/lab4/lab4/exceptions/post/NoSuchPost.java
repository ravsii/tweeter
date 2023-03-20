package me.lab4.lab4.exceptions.post;

public class NoSuchPost extends Exception {
	public NoSuchPost() {
		super("Такого поста не существует");
	}
}

