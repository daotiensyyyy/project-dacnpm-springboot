package org.springbootapp.service.implement;

import java.util.List;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Product;
import org.springbootapp.repository.ICartRepository;
import org.springbootapp.service.ICartService;
import org.springbootapp.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImp implements ICartService{
	
	@Autowired
	ICartRepository cartRepo;
	
	@Autowired
	IProductService productService;
	
//	@Autowired
//	Cart cart;

	@Override
	public List<Cart> addCartbyUserIdAndProductId(long productId, long userId, int qty, double price) throws Exception {
		try {
			if(cartRepo.getCartByProductIdAnduserId(userId, productId).isPresent()){
				throw new Exception("Product is already exist.");
			}
			Cart cart = new Cart();
			cart.setQty(qty);
			cart.setUser_id(userId);
			Product pro = productService.getProductsById(productId);
			cart.setProduct(pro); 
			//TODO price has to check with qty
			cart.setPrice(price);
			cartRepo.save(cart);		
			return this.getCartByUserId(userId);	
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void updateQtyByCartId(long cartId, int qty, double price) throws Exception {
		cartRepo.updateQtyByCartId(cartId,price,qty);
		
	}

	@Override
	public List<Cart> getCartByUserId(long userId) {
		return cartRepo.getCartByuserId(userId);
	}

	@Override
	public List<Cart> removeCartByProductIdAndUserId(long userId, Long productId) {
		cartRepo.deleteCartByProductIdAndUserId(userId, productId);
		return this.getCartByUserId(userId);
	}

	@Override
	public List<Cart> removeAllCartByUserId(long userId) {
		cartRepo.deleteAllCartByUserId(userId);
		return null;
	}

	@Override
	public Boolean checkTotalAmountAgainstCart(double totalAmount, long userId) {
		double total_amount = cartRepo.getTotalAmountByUserId(userId);
		if(total_amount == totalAmount) {
			return true;
		}
		System.out.print("Error from request "+total_amount +" --db-- "+ totalAmount);
		return false;
	}

}
