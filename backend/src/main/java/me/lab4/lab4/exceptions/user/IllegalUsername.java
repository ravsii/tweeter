package me.lab4.lab4.exceptions.user;


import org.springframework.web.bind.annotation.ResponseStatus;

public class IllegalUsername extends Exception {
	public IllegalUsername(String errorMessage) {
		super("Неверный логин: " + errorMessage);
	}
}

