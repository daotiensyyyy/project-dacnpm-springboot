package org.springbootapp.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springbootapp.config.CartConfig;
import org.springbootapp.dto.MessageResponse;
import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Order;
import org.springbootapp.service.ICartService;
import org.springbootapp.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class OrderController {
	@Autowired
	ICartService cartService;

	@Autowired
	IProductService proService;

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<?> addOrder(@RequestBody HashMap<String, String> request) {
		try {
			String keys[] = { "userId", "total_price", "pay_type", "deliveryAddress" };
			if (CartConfig.validationWithHashMap(keys, request)) {
			}
			long user_Id = Long.parseLong(request.get("userId"));
			double total_amt = Double.parseDouble(request.get("total_price"));
			if (cartService.checkTotalAmountAgainstCart(total_amt, user_Id)) {
				List<Cart> cartItems = cartService.getCartByUserId(user_Id);
				List<Order> tmp = new ArrayList<Order>();
				for (Cart c : cartItems) {
					String orderId = "" + c.getOrderId();
					Order cart = new Order();
					cart.setPayment_type(request.get("pay_type"));
					cart.setPrice(total_amt);
					cart.setUser_id(user_Id);
					cart.setOrder_id(orderId);
					cart.setProduct(c.getProduct());
					cart.setQty(c.getQty());
					cart.setDelivery_address(request.get("deliveryAddress"));
					tmp.add(cart);
				}
				cartService.saveProductsForCheckout(tmp);
				return ResponseEntity.ok().body(new MessageResponse("Success! Your order has been placed! ", ""));
			} else {
				throw new Exception("Total amount is mismatch");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
		}
	}

	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrdersByUserId(@PathVariable Long id) {
		try {
			List<Order> obj = cartService.getAllOrderByUserId(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
		}

	}
}