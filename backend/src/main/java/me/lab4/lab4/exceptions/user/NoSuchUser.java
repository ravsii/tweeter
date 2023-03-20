package me.lab4.lab4.exceptions.user;


public class NoSuchUser extends Exception {
	public NoSuchUser() {
		super("Пользователя с таким логином не существует");
	}
}

