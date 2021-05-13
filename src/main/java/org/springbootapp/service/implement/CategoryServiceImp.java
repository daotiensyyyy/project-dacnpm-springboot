package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springbootapp.entity.Category;
import org.springbootapp.entity.Product;
import org.springbootapp.repository.ICategoryRepository;
import org.springbootapp.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp implements ICategoryService {
	@Autowired
	private ICategoryRepository repository;
	
	@Autowired
	private Category categoryEntity;

	@Override
	public Category create(Category category) {
		return repository.saveAndFlush(category);
	}

	@Override
	public List<Category> getAll() {
		return repository.findAll();
	}

	@Override
	public List<Product> getAllProductsByCategory(Long categoryId) {
		Optional<Category> findByIdWithProductsGraph = repository.findByIdWithProductsGraph(categoryId);
		return List.copyOf(findByIdWithProductsGraph.get().getProducts());
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public void update(Long id, Category category) {
		if (repository.findById(id).isPresent()) {
			categoryEntity = repository.findById(id).get();
			categoryEntity.setName(category.getName());
			categoryEntity.setDescription(category.getDescription());
			repository.save(categoryEntity);

		}
	}

}
