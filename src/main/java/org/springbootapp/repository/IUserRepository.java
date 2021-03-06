package org.springbootapp.repository;

import java.util.Optional;

import org.springbootapp.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	User findUserByUsername(String username);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByResetToken(String resetToken);
	
	@Query("select u from User u where u.username=:username and u.active=true")
	Optional<User> findActiveAccount(@Param("username")String username);
	
	@EntityGraph("User.items")
	@Query("SELECT c FROM User c WHERE c.id = ?1")
	Optional<User> findByIdWithItemsGraph(Long userID);
	
	@EntityGraph("User.orders")
	@Query("SELECT c FROM User c WHERE c.id = ?1")
	public Optional<User> findByIdWithOrdersGraph(Long userID);
}
