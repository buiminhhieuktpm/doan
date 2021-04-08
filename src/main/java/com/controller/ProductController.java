package com.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import com.product.Product;
import com.product.ProductRepository;
import com.product.ProductService;
import com.size.Size;
import com.size.SizeService;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository prepo;
	@Autowired
	private ProductService psv;
	@Autowired
	private SizeService ssv;
	@Autowired
	private CategoryService csv;
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	@RequestMapping(value = "/admin/product")
	public String listproduct(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@RequestMapping(value = "/home/product/view/{pid}")
	public String productdetail(Model model, @PathVariable("pid") int pid) {
		Product p = psv.get(pid);
		String pt = p.getName();
		model.addAttribute("pageTitleUser", pt);

		List<Category> listc = csv.listAll();
		model.addAttribute("listc", listc);
		List<Size> s = ssv.listByPoductAndAmount(pid);
		if (!s.isEmpty()) {
			p.setStatus("Còn hàng");
		} else
			p.setStatus("Hết hàng");

		model.addAttribute("s", s);
		p.setView(p.getView() + 1);
		psv.add(p);
		model.addAttribute("p", p);
		return "productdetail";
	}

	@RequestMapping(value = "/home/product/{cid}")
	public String listproductbycategory(Model model, @PathVariable("cid") int cid) {
		List<Category> listc = csv.listAll();
		model.addAttribute("listc", listc);
		String keyword = null;
		return listByPageUser(model, 1, keyword, cid);
	}

	@GetMapping("/home/product/page/{pageNumber}")
	public String listByPageUser(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword, @Param("cid") int cid) {
		List<Category> listc = csv.listAll();
		model.addAttribute("listc", listc);
		Category category = csv.get(cid);
		String pageTitleUser = category.getName();
		model.addAttribute("pageTitleUser", pageTitleUser);
		Page<Product> page = psv.findAllByCategoryKeyword(currentPage, keyword, cid);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		List<Product> list = page.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("keyword", keyword);
		model.addAttribute("list", list);
		model.addAttribute("cid", cid);
		return "productbycategory";
	}

	@GetMapping("/admin/product/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		String pageTitle = "Product";
		model.addAttribute("pageTitle", pageTitle);
		List<Category> c = csv.listAll();
		Page<Product> page = psv.findAll(currentPage, keyword);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		List<Product> list = page.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("keyword", keyword);
		model.addAttribute("list", list);
		model.addAttribute("c", c);
		return "product";
	}

	@RequestMapping(value = "/admin/product/addproduct")
	public String addproduct(Model model) {
		String pageTitle = "add product";
		model.addAttribute("pageTitle", pageTitle);
		List<Category> c = csv.listAll();
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("c", c);
		return "addproduct";
	}

	@RequestMapping(value = "/admin/product/saveproduct", method = RequestMethod.POST)
	public String saveproduct(@ModelAttribute("product") Product product, @RequestParam MultipartFile image,
			@RequestParam MultipartFile image2, @RequestParam MultipartFile image3, @RequestParam MultipartFile image4)
			throws IOException {

		Path staticPath = Paths.get("static");
		Path imagePath = Paths.get("images");
		if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
			Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
		}
		Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image.getOriginalFilename());
		try (OutputStream os = Files.newOutputStream(file)) {
			os.write(image.getBytes());
		}
		String img = "/images/" + image.getOriginalFilename().toString();
		product.setImg(img);
		if (image2.getOriginalFilename() == "") {
			product.setImg2(img);
		} else {
			Path file2 = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image2.getOriginalFilename());
			try (OutputStream os = Files.newOutputStream(file2)) {
				os.write(image2.getBytes());
			}
			String img2 = "/images/" + image2.getOriginalFilename().toString();
			product.setImg2(img2);
		}
		if (image3.getOriginalFilename() == "") {
			product.setImg3(img);
		} else {
			Path file3 = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image3.getOriginalFilename());
			try (OutputStream os = Files.newOutputStream(file3)) {
				os.write(image3.getBytes());
			}
			 String img3 = "/images/"+image3.getOriginalFilename().toString();
				product.setImg3(img3);
		}
		if (image4.getOriginalFilename() == "") {
			product.setImg4(img);
		} else {
			Path file4 = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image4.getOriginalFilename());
			try (OutputStream os = Files.newOutputStream(file4)) {
				os.write(image4.getBytes());
			}
			 String img4 = "/images/"+image4.getOriginalFilename().toString();
				product.setImg4(img4);
		}
		if (product.getId() < 1) {
			Optional<Product> oc = prepo.findProductByName(product.getName());
			if (oc.isPresent()) {
				throw new IllegalStateException("Tên sản phẩm đã tồn tại");
			}
			product.setOncreate(LocalDate.now());
			product.setOnupdate(LocalDate.now());

		} else
			product.setOnupdate(LocalDate.now());

		psv.add(product);
		return "redirect:/admin/product";
	}

	@RequestMapping(value = "/admin/product/deleteproduct/{id}")
	public String deleteproduct(@PathVariable(name = "id") int id) {
		psv.delete(id);
		return "redirect:/admin/product";
	}

	@RequestMapping(value = "/admin/product/edit/{id}")
	public String editproduct(@PathVariable(name = "id") int id, Model model) {
		String pageTitle = "edit product";
		model.addAttribute("pageTitle", pageTitle);
		List<Category> c = csv.listAll();
		Product sp = psv.get(id);
		model.addAttribute("product", sp);
		model.addAttribute("c", c);
		return "editproduct";
	}
}
