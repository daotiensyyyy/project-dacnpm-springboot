package org.springbootapp.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springbootapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
	@Query("Select sum(c.price) FROM Cart c WHERE c.user_id=:user_id")
	double getTotalAmountByUserId(@Param("user_id") Long user_id);

	@Query("Select c  FROM Cart c WHERE c.user_id=:user_id")
	List<Cart> getCartByUserId(@Param("user_id") Long user_id);

	@Query("Select c FROM Cart c ")
	Optional<Cart> getCartByuserIdtest();

	@Query("Select c FROM Cart c WHERE c.product.id= :product_id and c.user_id=:user_id")
	Optional<Cart> getCartByProductIdAndUserId(@Param("user_id") Long user_id, @Param("product_id") Long product_id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Cart c WHERE c.product.id =:product_id and c.user_id=:user_id")
	void deleteCartByProductIdAndUserId(@Param("user_id") Long user_id, @Param("product_id") Long product_id);
	
//	@Modifying
//	@Transactional
//	@Query("DELETE  FROM Cart c WHERE c.user_id=:user_id")
//	void deleteCartByUserId(@Param("user_id") Long user_id);

	@Modifying
	@Transactional
	@Query("DELETE  FROM Cart c WHERE c.user_id=:user_id")
	void deleteAllCartByUserId(@Param("user_id") Long user_id);

	@Modifying
	@Transactional
	@Query("DELETE  FROM Cart c WHERE c.user_id=:user_id")
	void deleteAllCartUserId(@Param("user_id") Long user_id);

	@Modifying
	@Transactional
	@Query("update Cart c set c.qty=:qty,c.price=:price WHERE c.id=:cart_id")
	void updateQtyByCartId(@Param("cart_id") Long cart_id, @Param("price") double price, @Param("qty") Integer qty);
}
