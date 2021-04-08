package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.city.City;
import com.city.CityRepository;
import com.city.CityService;
@Controller
public class CityController {
	@Autowired
	private CityRepository crepo;
	@Autowired
	private CityService ctsv;
	@GetMapping(value="/admin/city")
	public String listcity(Model model){
		String pageTitle = "city";
		model.addAttribute("pageTitle", pageTitle);
		List<City> listc = ctsv.listAll();
		model.addAttribute("list",listc);
		return "city";
	}
	@RequestMapping(value="/admin/city/addcity")
	public String addcity(Model model){
		String pageTitle = "add city";
		model.addAttribute("pageTitle", pageTitle);
		City city = new City();
		model.addAttribute("city",city);
		return "addcity";
	}
	@RequestMapping(value="/savecity", method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public String savecity(@ModelAttribute("city") City city){
		if(city.getId()<1) {
			Optional<City> oc = crepo.findCityByName(city.getName());
			if(oc.isPresent()) {
				throw new IllegalStateException("Tên danh mục đã tồn tại");
			}
		}
		ctsv.add(city);		
		return "redirect:/admin/city";
	}
	@RequestMapping(value="/admin/city/edit/{id}")
	public String editcity(@PathVariable(name="id")int id, Model model) {
		String pageTitle = "edit city";
		model.addAttribute("pageTitle", pageTitle);
		City c = ctsv.get(id);
		model.addAttribute("city",c);
		return "editcity";
	}

	@RequestMapping(value="/admin/deletecity/{id}")
	public String deletecity(@PathVariable(name="id")int id){
		ctsv.deleteById(id);		
		return "redirect:/admin/category";
	}
}
