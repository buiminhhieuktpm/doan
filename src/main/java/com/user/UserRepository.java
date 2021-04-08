package com.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	@Query("SELECT u FROM User u WHERE u.username=?1")
	public User getUserByName(String name);
}
