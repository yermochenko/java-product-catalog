package org.itstep.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Scope("prototype")
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(name = "category", required = false) Long categoryId) throws LogicException {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		if(categoryId != null) {
			Category category = categoryService.findById(categoryId);
			if(category == null) {
				throw new NotFoundException();
			}
			Map<Category, List<Product>> products = new HashMap<>();
			products.put(category, productService.findByCategory(category.getId()));
			model.addAttribute("products", products);
		} else {
			Map<Category, List<Product>> products = productService.findLatest();
			model.addAttribute("products", products);
		}
		return "/general/products";
	}

	@GetMapping("/edit")
	public String edit(Model model, @RequestParam(name = "id", required = false) Long id) throws LogicException {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		if(id != null) {
			Product product = productService.findById(id);
			if(product == null) {
				throw new NotFoundException();
			}
			model.addAttribute("product", product);
		}
		return "/product/edit";
	}

	@PostMapping("/save")
	public String save(
		@RequestParam(name = "id", required = false) Long id,
		@RequestParam(name = "name") String name,
		@RequestParam(name = "category") Long categoryId,
		@RequestParam(name = "price") Long price,
		@RequestParam(name = "amount") Integer amount
	) throws LogicException {
		if(!name.isBlank() && price > 0 && amount > 0) {
			Product product = new Product();
			if(id != null) {
				product.setId(id);
			}
			product.setName(name);
			product.setCategory(new Category());
			product.getCategory().setId(categoryId);
			product.setPrice(price);
			product.setAmount(amount);
			productService.save(product);
			return "redirect:/product/list.html";
		} else {
			throw new BadRequestException();
		}
	}

	@PostMapping("/delete")
	public String delete(HttpServletRequest req) throws LogicException {
		String idsString[] = req.getParameterValues("id");
		if(idsString != null) {
			try {
				List<Long> ids = new ArrayList<>(idsString.length);
				for(String id : idsString) {
					ids.add(Long.parseLong(id));
				}
				productService.delete(ids);
			} catch(NumberFormatException e) {
				throw new BadRequestException();
			}
		}
		return "redirect:/product/list.html";
	}

	@GetMapping("/search-names")
	public void searchNames(HttpServletRequest req, HttpServletResponse resp) throws LogicException, IOException {
		String search = req.getQueryString();
		if(search != null) {
			search = URLDecoder.decode(search, "UTF-8");
			try {
				List<Product> products = productService.findNamesBySearchString(search);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.writeValue(resp.getOutputStream(), products);
			} catch(LogicException e) {
				resp.sendError(500);
			}
		} else {
			resp.sendError(400);
		}
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam(name = "query", required = false) String query) throws LogicException {
		if(query != null && query.length() >= 3) {
			List<Category> categories = categoryService.findAll();
			model.addAttribute("categories", categories);
			Map<Category, List<Product>> products = productService.findBySearchString(query);
			model.addAttribute("products", products);
			return "/general/products";
		}
		return "redirect:/product/list.html";
	}
}
