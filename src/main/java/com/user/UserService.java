package com.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository usv;
	public User getUserByName(String name) {
		return usv.getUserByName(name);
	}
}
