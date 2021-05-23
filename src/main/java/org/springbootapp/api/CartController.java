package org.springbootapp.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Product;
import org.springbootapp.service.ICartService;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class CartController {

	@Autowired
	ICartService cartService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserService userService;

//	@RequestMapping(value = "/cart/add", method = RequestMethod.POST)
//	public ResponseEntity<?> addCartwithProduct(@RequestBody HashMap<String, String> addCartRequest) {
//		try {
//			String keys[] = { "productId", "userId", "qty", "price" };
//			if (CartConfig.validationWithHashMap(keys, addCartRequest)) {
//			}
//
//			long productId = Long.parseLong(addCartRequest.get("productId"));
//			long userId = Long.parseLong(addCartRequest.get("userId"));
//
//			if (cartService.getCartByUserIdAndProductId(userId, productId).isEmpty()) { // check if product is not in
//																						// cart
//				int qty = Integer.parseInt(addCartRequest.get("qty"));
//				double price = Double.parseDouble(addCartRequest.get("price"));
//				List<Cart> obj = cartService.addCartbyUserIdAndProductId(productId, userId, qty, price);
//				return ResponseEntity.ok(obj);
//			} else { // product already in cart
//				Optional<Cart> tmp = cartService.getCartByUserIdAndProductId(userId, productId);
//				int qty = tmp.get().getQty() + Integer.parseInt(addCartRequest.get("qty"));
//				double price = tmp.get().getPrice() + Double.parseDouble(addCartRequest.get("price"));
//				cartService.updateQtyByCartId(tmp.get().getId(), qty, price);
//				Optional<Cart> obj = cartService.getCartByUserIdAndProductId(userId, productId);
//				return ResponseEntity.ok(obj);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
//		}
//
//	}
//
//	@RequestMapping(value = "/cart/update", method = RequestMethod.PUT)
//	public ResponseEntity<?> updateQtyForCart(@RequestBody HashMap<String, String> addCartRequest) {
//		try {
//			String keys[] = { "cartId", "userId", "qty", "price" };
//			if (CartConfig.validationWithHashMap(keys, addCartRequest)) {
//			}
//			long cartId = Long.parseLong(addCartRequest.get("cartId"));
//			long userId = Long.parseLong(addCartRequest.get("userId"));
//			int qty = Integer.parseInt(addCartRequest.get("qty"));
//			double price = Double.parseDouble(addCartRequest.get("price"));
//			cartService.updateQtyByCartId(cartId, qty, price);
//			List<Cart> obj = cartService.getCartByUserId(userId);
//			return ResponseEntity.ok(obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
//		}
//
//	}
//
//	@RequestMapping(value = "/cart/{id}", method = RequestMethod.GET)
//	public ResponseEntity<?> getCartsByUserId(@PathVariable("id") Long id) {
//		try {
//			List<Cart> obj = cartService.getCartByUserId(id);
//			return ResponseEntity.ok(obj);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
//		}
//	}
//
//	@RequestMapping(value = "/cart/remove/{user_id}/{product_id}", method = RequestMethod.DELETE)
//	public ResponseEntity<?> removeCartwithUserIdAndProductId(@PathVariable("user_id") Long user_id,
//			@PathVariable("product_id") Long product_id) {
//		try {
////			String keys[] = {"userId","cartId"};
////			if(CartConfig.validationWithHashMap(keys, removeCartRequest)) {	
////			}
//			List<Cart> obj = cartService.removeCartByProductIdAndUserId(user_id, product_id);
//			return ResponseEntity.ok(obj);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
//		}
//	}

	@RequestMapping(value = "/cart/{uid}", method = RequestMethod.GET)
	public ResponseEntity<?> getCart(@PathVariable("uid") Long uid) {
		HashMap<Object, Object> map = new HashMap<>();
		HashSet<Object> set2 = new HashSet<>();
		HashMap<String, Object> map3 = new HashMap<>();
		List<Cart> cart = cartService.listCartItems(uid);
		int total = 0;
		for (int i = 0; i < cart.size(); i++) {
			System.out.println(cart.get(i).getTotal());
			int arr = cart.get(i).getTotal();
			total += arr;
			Long cart_id = ((Cart) cart.get(i)).getId();
			Long user_id = cart.get(i).getUser().getId();
			int qty = cart.get(i).getQty();
			List<Product> products = Arrays.asList(cart.get(i).getProduct());
			map3.put("product", products);
			map3.put("qty", qty);
			set2.add(map3);
			map.put("cart_id", cart_id);
			map.put("user_id", user_id);
			map.put("products", set2);
			map.put("total", total);
		}

		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/cart/add/{uid}/{pid}/{qty}", method = RequestMethod.POST)
	public ResponseEntity<?> getCart(@PathVariable("uid") Long uid, @PathVariable("pid") Long pid,
			@PathVariable("qty") Integer qty) {

		Cart cart = cartService.addProduct(uid, pid, qty);

		return ResponseEntity.ok(cart);
	}

//	@RequestMapping(value = "/cart/update/{uid}/{pid}/{qty}", method = RequestMethod.PUT)
//	public ResponseEntity<?> updateQty(@PathVariable("uid") Long uid, @PathVariable("pid") Long pid,
//			@PathVariable("qty") Integer qty) {
//
//		try {
//			cartService.updateQtyByCartId(qty, pid, uid);
//			HashMap<Object, Object> map = new HashMap<>();
//			HashSet<Object> map2 = new HashSet<>();
//			HashMap<String, Object> map3 = new HashMap<>();
//			List<Cart> cart = cartService.listCartItems(uid);
//			for (int i = 0; i < cart.size(); i++) {
//				System.out.println(cart.get(i).getTotal());
//				int arr = cart.get(i).getTotal();
//				total += arr;
//				Long cart_id = ((Cart) cart.get(i)).getId();
//				Long user_id = cart.get(i).getUser().getId();
//				List<Product> products = Arrays.asList(cart.get(i).getProduct());
//				map3.put("product", products);
//				map3.put("qty", qty);
//				map2.add(map3);
//				map.put("cart_id", cart_id);
//				map.put("user_id", user_id);
//				map.put("products", map2);
//				map.put("total", total);
//			}
//			
//			return ResponseEntity.ok(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
