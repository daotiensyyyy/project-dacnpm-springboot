package org.springbootapp.api;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springbootapp.entity.Image;
import org.springbootapp.entity.Product;
import org.springbootapp.service.IFileService;
import org.springbootapp.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class ProductController {

	private static String UPLOAD_DIR = "/uploads";

	@Autowired
	IProductService productService;

	@Autowired
	IFileService service;
	
	/* ADMIN */

	@RequestMapping(value = "/admin/products", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Product> createProduct(@ModelAttribute Product product,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath(UPLOAD_DIR) + File.separator + fileName;
			service.save(file.getInputStream(), path);
			String link = request.getRequestURL().toString().replace(request.getRequestURI(), "") + UPLOAD_DIR
					+ File.separator + file.getOriginalFilename();
			Image image = new Image(link, product.getName());
			product.setImages(new HashSet<>());
			product.addImage(image);
			productService.save(product);

			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/admin/products/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
		try {
			productService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/admin/products/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> updateById(@PathVariable("id") Long id, @RequestBody Product newProduct) {
		try {
			productService.update(id, newProduct);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/admin/products", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAll();
		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admin/products/code/{code}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> adminGetProductByCode(@PathVariable("code") String code) {
		Optional<Product> product = productService.adminGetByCode(code);
		if (product.isPresent()) {
			return new ResponseEntity(product.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admin/products/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> adminGetProductByName(@PathVariable("name") String name) {
		Optional<Product> product = productService.adminGetByName(name);
		if (product.isPresent()) {
			return new ResponseEntity(product.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/* USER */
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getAllActiveProducts() {
		List<Product> products = productService.getActiveProduct();
		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/products/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> getProductById(@PathVariable("id") Long id) {
		Optional<Product> product = productService.getById(id);
		if (product.isPresent()) {
			return new ResponseEntity(product.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/products/code/{code}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> getProductByCode(@PathVariable("code") String code) {
		Optional<Product> product = productService.getByCode(code);
		if (product.isPresent()) {
			return new ResponseEntity(product.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/products/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> getProductByName(@PathVariable("name") String name) {
		Optional<Product> product = productService.getByName(name);
		if (product.isPresent()) {
			return new ResponseEntity(product.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(value = "/products/{name}", method = RequestMethod.GET)
//	public ResponseEntity<Optional<Product>> getProductByName(@PathVariable("name") String name) {
//		Optional<Product> product = productService.getByName(name);
//		if (product.isPresent()) {
//			return new ResponseEntity(product.get(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}


	@RequestMapping(value = "/products/{id}/images", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> getProductImages(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(productService.getImages(id), HttpStatus.OK);
		} catch (Exception e) {
//			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
