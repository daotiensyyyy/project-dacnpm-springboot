package org.springbootapp.service;

import org.springbootapp.entity.Order;

public interface IOrderService {
	
	void addOrder(Order order);
	
	Order getOrder(Long orderID);
	
}
