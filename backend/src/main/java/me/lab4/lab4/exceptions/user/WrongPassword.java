package me.lab4.lab4.exceptions.user;


public class WrongPassword extends Exception {
	public WrongPassword() {
		super("Неверный пароль");
	}
}

