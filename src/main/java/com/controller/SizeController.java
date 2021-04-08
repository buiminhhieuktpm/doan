package com.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.category.Category;
import com.product.Product;
import com.product.ProductService;
import com.size.Size;
import com.size.SizeService;

@Controller
public class SizeController {
	@Autowired
	private ProductService psv;
	@Autowired
	private SizeService ssv;
	@GetMapping(value = "/admin/product/size/{pid}")
	public String listsizebyproductid(Model model,@PathVariable("pid") int pid) {
		Product p = psv.get(pid);
		String pageTitle=p.getName()+"số lượng";
		model.addAttribute("pageTitle", pageTitle);
		List<Size> s = ssv.listByProductId(pid);
		model.addAttribute("s", s);
		model.addAttribute("p", p);
		return "size";
	}
	@RequestMapping(value="/admin/product/addsize/{pid}")
	public String addsize(Model model,@PathVariable("pid")int pid){
		String pageTitle = "Thêm size";
		model.addAttribute("pageTitle", pageTitle);
		Size size = new Size();
		model.addAttribute("size",size);
		Product p = psv.get(pid);
		model.addAttribute("product", p);
		return "addsize";
	}
	@RequestMapping(value = "/admin/product/size/savesize/{pid}",method = RequestMethod.POST)
	public String savesize(@ModelAttribute("size")Size size,@PathVariable("pid")int pid) {
		if(size.getId()<1) {
			Optional<Size> oc = ssv.listByName(pid, size.getSize());
			if(oc.isPresent()) {
				throw new IllegalStateException("Size đã tồn tại");
			}
			size.setP(psv.get(pid));
			ssv.add(size);
		}
		
		ssv.add(size);
		return "redirect:/admin/product/size/"+pid;
	}
	@RequestMapping(value="/admin/product/size/editsize/{id}")
	public String editproduct(@PathVariable(name="id")int id, Model model) {
		String pageTitle = "Edit size";
		model.addAttribute("pageTitle", pageTitle);
		Size s = ssv.get(id);
		int pid = s.getP().getId();
		model.addAttribute("p", s.getP());
		model.addAttribute("pid", pid);
		model.addAttribute("size", s);
		return "editsize";
	}
	@RequestMapping(value="/admin/product/deletesize/{id}")
	public String deleteproduct(@PathVariable(name="id")int id){
		int pid = ssv.get(id).getP().getId();
		ssv.delete(id);		
		return "redirect:/admin/product/size/"+pid;
	}
	
}
