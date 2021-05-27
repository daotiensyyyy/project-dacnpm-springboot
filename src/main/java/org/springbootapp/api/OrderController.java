package org.springbootapp.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springbootapp.config.CartConfig;
import org.springbootapp.dto.MessageResponse;
import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Order;
import org.springbootapp.service.ICartService;
import org.springbootapp.service.IOrderService;
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
	IOrderService orderService;


	@Autowired
	IProductService proService;

	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<?> addOrder(@RequestBody Order order) {
		orderService.addOrder(order);
		return new ResponseEntity<>(new MessageResponse("Your order has been placed successfully !"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/order/{oid}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrder(@PathVariable("oid") Long orderID) {
		Order order = orderService.getOrder(orderID);
		return ResponseEntity.ok(order);
	}
}
