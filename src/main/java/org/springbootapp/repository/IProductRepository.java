package org.springbootapp.repository;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.Product;
import org.springbootapp.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
	@Query("select p from Product p where p.name =:name and p.active=true")
	Optional<Product> findByName(@Param("name")String name);
	
	@Query("select p from Product p where p.code =:code and p.active=true")
	Optional<Product> findByCode(@Param("code")String code);
	
	@Query("select p from Product p where p.name =:name")
	Optional<Product> adminFindByName(@Param("name")String name);
	
	@Query("select p from Product p where p.code =:code")
	Optional<Product> adminFindByCode(@Param("code")String code);

	@EntityGraph("Product.graph")
	List<Product> findAll();

	@Query("SELECT p FROM Product p WHERE p.id = ?1")
	@EntityGraph("Product.images")
	Optional<Product> findByIdWithImagesGraph(Long id);
	
	@Query("select p from Product p where p.active=true")
	List<Product> findActiveProduct();
	
}
