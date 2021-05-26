package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.Revenue;

public interface IRevenueService {
	
	Double getTotal();
	
	Double getTotalByDate(int date);
	
	Revenue save(Revenue r, int date);
	
	double updateTotal(int date, double total) throws Exception;
	
	List<Revenue> getAll();
	
}
