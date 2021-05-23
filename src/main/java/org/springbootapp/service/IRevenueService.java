package org.springbootapp.service;

import org.springbootapp.entity.Revenue;

public interface IRevenueService {
	
	Double getTotal();
	
	Double getTotalByMonth(int month);
	
	Revenue save(Revenue r, int month);
	
	double updateTotal(int month, double total) throws Exception;
}
