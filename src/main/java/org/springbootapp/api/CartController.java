package org.springbootapp.api;

import java.util.List;

import org.springbootapp.dto.MessageResponse;
import org.springbootapp.entity.Cart;
import org.springbootapp.service.ICartService;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

//	@RequestMapping(value = "/cart/add/{uid}/{pid}/{qty}", method = RequestMethod.POST)
//	public ResponseEntity<?> getCart(@PathVariable("uid") Long uid, @PathVariable("pid") Long pid,
//			@PathVariable("qty") Integer qty) {
//
//		Cart cart = cartService.addProduct(uid, pid, qty);
//
//		return ResponseEntity.ok(cart);
//	}

	@RequestMapping(value = "/user/{cid}/cart-items", method = RequestMethod.POST)
	public ResponseEntity<?> addItemToCart(@PathVariable("cid") Long customerID, @RequestBody Cart item) {
		userService.addItemToCart(customerID, item);
		return new ResponseEntity<>(new MessageResponse("The product has been added to your cart !"), HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{cid}/cart-items", method = RequestMethod.GET)
	public ResponseEntity<?> getCart(@PathVariable("cid") Long customerID) {
		List<Cart> obj = userService.getCart(customerID);
		return ResponseEntity.ok(obj);
	}

	@RequestMapping(value = "/user/{cid}/cart-items/{iid}", method = RequestMethod.PUT)
	public void updateCart(@PathVariable("cid") Long customerID, @PathVariable("iid") Long itemID,
			@RequestParam String action) {
		userService.updateCartItem(customerID, itemID, action);
	}
	
	@RequestMapping(value = "/user/{cid}/cart-items/{iid}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteItemInCart(@PathVariable("cid") Long customerID, @PathVariable("iid") Long itemID) {
		userService.deleteItemIncart(customerID, itemID);
		return new ResponseEntity<>(new MessageResponse("The product has been deleted from your cart !"), HttpStatus.OK);
	}

}
