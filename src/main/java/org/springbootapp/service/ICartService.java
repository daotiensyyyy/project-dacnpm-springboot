package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Order;
import org.springbootapp.entity.User;

public interface ICartService {
//	List<Cart> addCartbyUserIdAndProductId(Long productId, Long userId, int qty, double price) throws Exception;
//
//	void updateQtyByCartId(Long cartId, int qty, double price) throws Exception;
//
//	List<Cart> getCartByUserId(Long userId);
//	
//	Optional<Cart> getCartByUserIdAndProductId(Long userId, Long product_id);
//
//	List<Cart> removeCartByProductIdAndUserId(Long userId, Long product_id);
//
	List<Cart> removeAllCartByUserId(Long userId);
//
//	Boolean checkTotalAmountAgainstCart(double totalAmount, Long userId);

	List<Order> getAllOrderByUserId(Long userId);

//	List<Order> saveProductsForCheckout(List<Order> tmp) throws Exception;
	
//	List<Cart>listCartItems(Long id);
	
//	Cart addProduct(Long uid, Long productId, Integer qty);
	
	void updateQtyByCartId(Integer qty, Long pid, Long uid) throws Exception;
}
