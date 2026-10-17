package com.sunbeam.users.utils;

public class InvalidCredentialsException extends RuntimeException{

	public InvalidCredentialsException() {
		super("Login Error -> Invalid Credentials ");
	}
}
