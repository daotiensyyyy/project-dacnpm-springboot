package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.Image;
import org.springbootapp.entity.Product;
import org.springbootapp.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
	List<Product> getAll();

	Optional<Product> getById(Long id);

	Optional<Product> getByName(String name);

	Optional<Product> getByCode(String code);

	Optional<Product> adminGetByName(String name);

	Optional<Product> adminGetByCode(String code);

	Product save(Product product);

	void delete(Long id);

	void update(Long id, Product product);

	List<Image> getImages(Long productId);

	void saveImage(MultipartFile imageFile, String folder);

	List<Product> getActiveProduct();
}
