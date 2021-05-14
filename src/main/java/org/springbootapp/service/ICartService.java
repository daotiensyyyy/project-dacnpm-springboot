package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.Cart;

public interface ICartService {
	List<Cart> addCartbyUserIdAndProductId(long productId,long userId,int qty,double price) throws Exception;
	void updateQtyByCartId(long cartId,int qty,double price) throws Exception;
	List<Cart> getCartByUserId(long userId);
	List<Cart> removeCartByUserId(long cartId,long userId);
	List<Cart> removeAllCartByUserId(long userId);
	Boolean checkTotalAmountAgainstCart(double totalAmount,long userId);
//	List<CheckoutCart> getAllCheckoutByUserId(long userId);
//	List<CheckoutCart> saveProductsForCheckout(List<CheckoutCart> tmp)  throws Exception;
}
