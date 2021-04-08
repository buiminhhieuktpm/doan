package com.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.category.Category;
import com.category.CategoryService;
import com.product.Product;
import com.product.ProductService;

@Controller
public class IndexController {
	@Autowired
	private CategoryService csv;
	@Autowired
	private ProductService psv;
	@GetMapping(value = "/")
	public String index(Model model) {
		List<Category> listc = csv.listAll();
		model.addAttribute("listc",listc);
		String pageTitle = "Trang chủ";
		model.addAttribute("pageTitleUser", pageTitle);
		List<Product> lnew = psv.listProductNew();
		model.addAttribute("lnew", lnew);
		List<Product> lview = psv.listProductByView();
		model.addAttribute("lview", lview);
		return "index";
	}
	@GetMapping(value = "/home/contact")
	public String contact(Model model) {
		List<Category> listc = csv.listAll();
		model.addAttribute("listc",listc);
		return "contact";
	}
}
