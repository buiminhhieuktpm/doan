package com.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.category.Category;
import com.category.CategoryService;
import com.city.City;
import com.city.CityService;
import com.product.Product;
import com.product.ProductService;





@Controller
public class AdminController {
	@Autowired
	private ProductService psv;
	@GetMapping(value="/admin")
	public String showadmin(Model model){
		String pageTitle = "admin";
		model.addAttribute("pageTitle", pageTitle);
		return "admin";
	}
	
	
	
	
	
	
	
	
}
