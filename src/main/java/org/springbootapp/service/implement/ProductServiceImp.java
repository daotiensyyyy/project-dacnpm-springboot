package org.springbootapp.service.implement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springbootapp.entity.Category;
import org.springbootapp.entity.Image;
import org.springbootapp.entity.Product;
import org.springbootapp.entity.User;
import org.springbootapp.repository.IProductRepository;
import org.springbootapp.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImp implements IProductService {

	@Autowired
	private Product productEntity;

	@Autowired
	private Category category;

	@Autowired
	private IProductRepository productRepository;

	@Override
	@Transactional
	public Product save(Product product) {
		return productRepository.saveAndFlush(product);
	}

	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<Product> getById(Long id) {
		Optional<Product> product = Optional.of(productRepository.findById(id).get());
		return product;
	}

	@Override
	public Optional<Product> getByName(String name) {
		String url_name = name.toLowerCase(); 
		Optional<Product> product = productRepository.findByName(url_name);
		return product;
	}
	
	@Override
	public Optional<Product> getByCode(String code) {
		Optional<Product> product = productRepository.findByCode(code);
		return product;
	}
	
	@Override
	public Optional<Product> adminGetByName(String name) {
		String url_name = name.toLowerCase(); 
		Optional<Product> product = productRepository.adminFindByName(url_name);
		return product;
	}
	
	@Override
	public Optional<Product> adminGetByCode(String code) {
		Optional<Product> product = productRepository.adminFindByCode(code);
		return product;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Optional<Product> product_deleted = productRepository.findById(id);
		if (product_deleted.isPresent()) {
			Product productInActive = product_deleted.get();
			productInActive.setActive(false);
			productRepository.save(productInActive);
		}
	}

	@Override
	@Transactional
	public void update(Long id, Product product) {

		if (productRepository.findById(id).isPresent()) {
			productEntity = productRepository.findById(id).get();
			productEntity.setPrice(product.getPrice());
			productEntity.setDescription(product.getDescription());
			productEntity.setEvaluate(product.getEvaluate());
			productEntity.setRemain(product.getRemain());
			productRepository.save(productEntity);

		}
	}

	@Override
	public List<Image> getImages(Long productId) {
		Optional<Product> findById = productRepository.findByIdWithImagesGraph(productId);
		return List.copyOf(findById.get().getImages());
	}

	@Override
	@Transactional
	public void saveImage(MultipartFile imageFile, String folder){
		try {
			byte[] bytes = imageFile.getBytes();
			 Path path = Paths.get(folder + imageFile.getOriginalFilename());
			java.nio.file.Files.write(path, bytes);
			System.out.println("OK");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error");
		}
		
	}

	@Override
	public List<Product> getActiveProduct() {
		return productRepository.findActiveProduct();
	}

	@Override
	public Product getProductsById(Long id) throws Exception {
		return productRepository.findById(id).orElseThrow(() ->new Exception("Product is not found"));
	}

}
