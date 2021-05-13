package org.springbootapp.repository;

import java.util.Optional;

import org.springbootapp.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
	@Query("SELECT c FROM Category c WHERE c.id = ?1")
	@EntityGraph("Category.products")
	public Optional<Category> findByIdWithProductsGraph(Long id);
}
