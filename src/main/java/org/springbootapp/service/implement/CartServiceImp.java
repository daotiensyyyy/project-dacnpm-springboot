package org.springbootapp.service.implement;

import java.util.List;

import org.springbootapp.entity.Cart;
import org.springbootapp.service.ICartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImp implements ICartService{

	@Override
	public List<Cart> addCartbyUserIdAndProductId(long productId, long userId, int qty, double price) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateQtyByCartId(long cartId, int qty, double price) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cart> getCartByUserId(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> removeCartByUserId(long cartId, long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> removeAllCartByUserId(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean checkTotalAmountAgainstCart(double totalAmount, long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
