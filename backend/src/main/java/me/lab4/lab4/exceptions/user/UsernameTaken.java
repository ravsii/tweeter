package me.lab4.lab4.exceptions.user;

public class UsernameTaken extends Exception {
	public UsernameTaken() {
		super("Логин уже существует");
	}
}

