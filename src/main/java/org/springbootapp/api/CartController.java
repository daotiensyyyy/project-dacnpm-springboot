package org.springbootapp.api;

import java.util.HashMap;
import java.util.List;

import org.springbootapp.config.CartConfig;
import org.springbootapp.entity.Cart;
import org.springbootapp.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000",  maxAge = 3600)
@RestController
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	@RequestMapping(value = "/cart/add", method = RequestMethod.POST)
	public ResponseEntity<?> addCartwithProduct(@RequestBody HashMap<String,String> addCartRequest) {
		try {
			String keys[] = {"productId","userId","qty","price"};
			if(CartConfig.validationWithHashMap(keys, addCartRequest)) {
			}
			long productId = Long.parseLong(addCartRequest.get("productId")); 
			long userId =  Long.parseLong(addCartRequest.get("userId")); 
			int qty =  Integer.parseInt(addCartRequest.get("qty")); 
			double price = Double.parseDouble(addCartRequest.get("price"));
			List<Cart> obj = cartService.addCartbyUserIdAndProductId(productId,userId,qty,price);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
   }
	
	@RequestMapping(value = "/cart/{id}", method = RequestMethod.GET)
  	public ResponseEntity<?> getCartsByUserId(@PathVariable("id")Long id) {
		try {
			List<Cart> obj = cartService.getCartByUserId(id);
			return ResponseEntity.ok(obj);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
   }
	
	@RequestMapping(value = "/cart/remove/{user_id}/{product_id}", method = RequestMethod.DELETE)
  	public ResponseEntity<?> removeCartwithUserIdAndProductId(@PathVariable("user_id")Long user_id, @PathVariable("product_id")Long product_id) {
		try {
//			String keys[] = {"userId","cartId"};
//			if(CartConfig.validationWithHashMap(keys, removeCartRequest)) {	
//			}
			List<Cart> obj = cartService.removeCartByProductIdAndUserId(user_id, product_id);
			return ResponseEntity.ok(obj);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
   }
}
