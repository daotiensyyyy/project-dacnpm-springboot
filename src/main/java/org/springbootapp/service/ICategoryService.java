package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.Category;
import org.springbootapp.entity.Product;


public interface ICategoryService {
	Category create(Category category);

	List<Category> getAll();

	List<Product> getAllProductsByCategory(Long categoryId);

	void delete(Long id);
	
	void update(Long id, Category category);
}
