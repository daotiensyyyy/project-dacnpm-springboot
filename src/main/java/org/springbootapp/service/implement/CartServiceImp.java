package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Order;
import org.springbootapp.entity.Product;
import org.springbootapp.entity.User;
import org.springbootapp.repository.ICartRepository;
import org.springbootapp.repository.IOrderRepository;
import org.springbootapp.repository.IProductRepository;
import org.springbootapp.service.ICartService;
import org.springbootapp.service.IProductService;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImp implements ICartService {

//	@Autowired
//	ICartRepository cartRepo;

	@Autowired
	ICartRepository cartRepo;

	@Autowired
	IProductRepository productRepo;

	@Autowired
	IProductService productService;

	@Autowired
	IUserService userService;

	@Autowired
	IOrderRepository orderRepo;

//	@Autowired
//	Cart cart;

//	@Override
//	public List<Cart> addCartbyUserIdAndProductId(Long productId, Long userId, int qty, double price) throws Exception {
//		try {
//			if(cartRepo.getCartByProductIdAndUserId(userId, productId).isPresent()){
//				throw new Exception("Product is already exist.");
//			}
//			Cart cart = new Cart();
//			cart.setQty(qty);
//			cart.setUser_id(userId);
//			Product pro = productService.getProductsById(productId);
//			cart.setProduct(pro); 
//			//TODO price has to check with qty
//			cart.setPrice(price);
//			cartRepo.save(cart);		
//			return this.getCartByUserId(userId);	
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new Exception(e.getMessage());
//		}
//	}
//
//	@Override
//	public void updateQtyByCartId(Long cartId, int qty, double price) throws Exception {
//		 cartRepo.updateQtyByCartId(cartId,price,qty);
//		
//	}
//
	@Override
	public List<Cart> getCartByUserId(Long userId) {
		return cartRepo.getCartByUserId(userId);
	}
//	
//	@Override
//	public Optional<Cart> getCartByUserIdAndProductId(Long userId, Long product_id) {
//		return cartRepo.getCartByProductIdAndUserId(userId, product_id);
//	}
//
//	@Override
//	public List<Cart> removeCartByProductIdAndUserId(Long userId, Long productId) {
//		cartRepo.deleteCartByProductIdAndUserId(userId, productId);
//		return this.getCartByUserId(userId);
//	}
//
	@Override
	public List<Cart> removeAllCartByUserId(Long userId) {
		cartRepo.deleteAllCartByUserId(userId);
		return null;
	}
//
//	@Override
//	public Boolean checkTotalAmountAgainstCart(double totalAmount, Long userId) {
//		double total_amount = cartRepo.getTotalAmountByUserId(userId);
//		if(total_amount == totalAmount) {
//			return true;
//		}
//		System.out.print("Error from request "+total_amount +" --db-- "+ totalAmount);
//		return false;
//	}

	@Override
	public List<Order> getAllOrderByUserId(Long userId) {
		return orderRepo.getByUserId(userId);
	}

	@Override
	public List<Order> saveProductsForCheckout(List<Order> tmp) throws Exception {
		try {
			long user_id = tmp.get(0).getUser_id();
			if(tmp.size() >0) {
				orderRepo.saveAll(tmp);
				this.removeAllCartByUserId(user_id);
				return this.getAllOrderByUserId(user_id);
			}	
			else {
				throw  new Exception("Should not be empty");
			}
		}catch(Exception e) {
			throw new Exception("Error while checkout "+e.getMessage());
		}
	}

	public List<Cart> listCartItems(Long id) {
		return cartRepo.getCartByUserId(id);
	}

	public Cart addProduct(Long uid, Long productId, Integer qty) {
		User user = userService.findUserById(uid).get();
		Integer addedQuantity = qty;
		Product product = productRepo.findById(productId).get();
		Cart cartItem = cartRepo.findByUserAndProduct(user, product);
		if (cartItem != null) {
			addedQuantity = cartItem.getQty() + qty;
			cartItem.setQty(addedQuantity);
			cartItem.setTotal(addedQuantity * product.getPrice());
		} else {
			cartItem = new Cart();
			cartItem.setQty(qty);
			cartItem.setUser(user);
			cartItem.setProduct(product);
			cartItem.setTotal(product.getPrice() * qty);
		}
		cartRepo.save(cartItem);
		return cartItem;
	}

	@Override
	public void updateQtyByCartId(Integer qty, Long pid, Long uid) throws Exception {
		cartRepo.updateQty(qty, pid, uid);
		

	}

}
