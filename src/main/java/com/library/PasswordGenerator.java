package com.library;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "buitung99";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.print(encodedPassword);
	}

}
	