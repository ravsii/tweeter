package me.lab4.lab4.exceptions.session;


public class InvalidSession extends Exception {
	public InvalidSession() {
		super("Сессия не валидна");
	}
}

