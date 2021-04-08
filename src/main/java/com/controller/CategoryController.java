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

import com.category.Category;
import com.category.CategoryRepository;
import com.category.CategoryService;
@Controller
public class CategoryController {
	@Autowired
	private CategoryRepository crepo;
	@Autowired
	private CategoryService csv;
	@GetMapping(value="/admin/category")
	public String listcategory(Model model){
		String pageTitle = "catagory";
		model.addAttribute("pageTitle", pageTitle);
		List<Category> listc = csv.listAll();
		model.addAttribute("list",listc);
		return "category";
	}
	
	@RequestMapping(value="/admin/category/addcategory")
	public String addcatagory(Model model){
		String pageTitle = "add category";
		model.addAttribute("pageTitle", pageTitle);
		Category category = new Category();
		model.addAttribute("category",category);
		return "addcategory";
	}
	@RequestMapping(value="/admin/category/savecategory", method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public String savecategory(@ModelAttribute("category") Category category){
		if(category.getId()<1) {
			Optional<Category> oc = crepo.findCategoryByName(category.getName());
			if(oc.isPresent()) {
				throw new IllegalStateException("Tên danh mục đã tồn tại");
			}
		}
		csv.add(category);		
		return "redirect:/admin/category";
	}
	@RequestMapping(value="/admin/category/edit/{id}")
	public String editcategory(@PathVariable(name="id")int id, Model model) {
		String pageTitle = "edit category";
		model.addAttribute("pageTitle", pageTitle);
		Category c = csv.get(id);
		model.addAttribute("category",c);
		return "editcategory";
	}

	@RequestMapping(value="/admin/deletecategory/{id}")
	public String deletecatagory(@PathVariable(name="id")int id){
		csv.deleteById(id);		
		return "redirect:/admin/category";
	}
	
}
