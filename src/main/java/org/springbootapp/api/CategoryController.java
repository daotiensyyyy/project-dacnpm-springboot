package org.springbootapp.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springbootapp.entity.Category;
import org.springbootapp.entity.Product;
import org.springbootapp.service.ICategoryService;

@CrossOrigin(origins = "http://localhost:3000",  maxAge = 3600)
@RestController
public class CategoryController {

	@Autowired
	private ICategoryService service;

	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
	public ResponseEntity<Category> create(@RequestBody Category category) {
		try {
			service.create(category);
			return new ResponseEntity<>(category, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> category = service.getAll();
		if (category.isEmpty()) {
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Category>>(category, HttpStatus.OK);
	}


	@RequestMapping(value = "/categories/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getProductsByCategory(@PathVariable("id") Long id) {
		try {
			List<Product> products = service.getAllProductsByCategory(id);
			return new ResponseEntity(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> updateById(@PathVariable("id") Long id, @RequestBody Category newCategory) {
		try {
			service.update(id, newCategory);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
