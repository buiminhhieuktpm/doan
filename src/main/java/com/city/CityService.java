package com.city;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.category.Category;

@Service
@Transactional
public class CityService {
	@Autowired
	private CityRepository crepo;
	public List<City> listAll(){
		return (List<City>)crepo.findAll();
	}
	public void add(City c) {
		Optional<City> oc = crepo.findCityByName(c.getName());
		if(oc.isPresent()) {
			throw new IllegalStateException("Tên thành phố đã tồn tại");
		}
		crepo.save(c);
	}
	public void delete(City c) {
		crepo.delete(c);
	}
	public void deleteById(int id) {
		crepo.deleteById(id);
	}
	public City get(int id) {
		return crepo.findById(id).get();
	}
}
