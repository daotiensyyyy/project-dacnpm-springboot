package org.springbootapp.repository;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Product;
import org.springbootapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByUser(User user);

	Cart findByUserAndProduct(User user, Product product);

//	@Query("Select sum(c.price) FROM Cart c WHERE c.user_id=:user_id")
//	double getTotalAmountByUserId(@Param("user_id") Long user_id);

	@Query("Select c  FROM Cart c WHERE c.user.id=:user_id")
	List<Cart> getCartByUserId(@Param("user_id") Long user_id);

//	@Query("Select c FROM Cart c ")
//	Optional<Cart> getCartByuserIdtest();
//
//	@Query("Select c FROM Cart c WHERE c.product.id= :product_id and c.user_id=:user_id")
//	Optional<Cart> getCartByProductIdAndUserId(@Param("user_id") Long user_id, @Param("product_id") Long product_id);
//
//	@Query("DELETE FROM Cart c WHERE c.product.id =:product_id and c.user_id=:user_id")
//	@Modifying
//	@Transactional
//	void deleteCartByProductIdAndUserId(@Param("user_id") Long user_id, @Param("product_id") Long product_id);
//
////	@Modifying
////	@Transactional
////	@Query("DELETE  FROM Cart c WHERE c.user_id=:user_id")
////	void deleteCartByUserId(@Param("user_id") Long user_id);
//
	@Query("DELETE  FROM Cart c WHERE c.user.id=:user_id")
	@Modifying
	@Transactional
	void deleteAllCartByUserId(@Param("user_id") Long user_id);

//	@Query("DELETE  FROM Cart c WHERE c.user.id=:user_id")
//	@Modifying
//	@Transactional
//	void deleteAllCartUserId(@Param("user_id") Long user_id);

	@Modifying
	@Transactional
	@Query("update Cart c set c.qty=?1 WHERE c.product.id=?2 and c.user.id=?3")
	void updateQty(Integer qty,Long pid, Long uid);
}
