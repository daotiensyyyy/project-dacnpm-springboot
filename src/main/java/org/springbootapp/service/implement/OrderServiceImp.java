package org.springbootapp.service.implement;

import java.util.HashSet;
import java.util.Set;

import org.springbootapp.entity.Order;
import org.springbootapp.entity.OrderItem;
import org.springbootapp.repository.ICartRepository;
import org.springbootapp.repository.IOrderRepository;
import org.springbootapp.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImp implements IOrderService{
	
	@Autowired
	IOrderRepository orderRepo;
	
	@Autowired
	ICartRepository cartRepo;

	@Override
	@Transactional
	public void addOrder(Order order) {
		Set<OrderItem> temp = new HashSet<>(order.getItems());
		order.setItems(new HashSet<>());
		Order save = orderRepo.save(order);
		temp.forEach(item -> orderRepo.addItem(item.getQuantity(), save.getId(), item.getProduct().getId()));	
		cartRepo.deleteAllCartByUserId(save.getUser().getId());
	}
	
	@Override
	public Order getOrder(Long orderID) {
		return orderRepo.findByIdWithItemsGraph(orderID).get();
	}

}
