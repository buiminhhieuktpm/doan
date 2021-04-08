package com.city;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.category.Category;
@Repository
public interface CityRepository extends JpaRepository<City, Integer>{
	@Query("SELECT c FROM City c WHERE c.name =?1")
	Optional<City> findCityByName(String name);
}
