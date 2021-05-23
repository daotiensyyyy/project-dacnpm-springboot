package org.springbootapp.service.implement;

import java.util.List;

import org.springbootapp.entity.Revenue;
import org.springbootapp.repository.IOrderRepository;
import org.springbootapp.repository.IRevenueRepository;
import org.springbootapp.service.IRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenueServiceImp implements IRevenueService {

	@Autowired
	IOrderRepository orderRepo;
	
	@Autowired
	IRevenueRepository revenueRepo;
	
	@Autowired
	Revenue revenueEntity;

	@Override
	public Double getTotal() {
		double total = 0;
		List<Double> tmp = orderRepo.getPriceGroupByOrderId();
		for (int i = 0; i < tmp.size(); i++) {
			total += tmp.get(i);
		}
		return total;
	}

	@Override
	public Double getTotalByMonth(int month) {
		double total = 0;
		List<Double> tmp = orderRepo.getTotalRevenueByMonth(month);
		for (int i = 0; i < tmp.size(); i++) {
			total += tmp.get(i);
		}
		return total;
	}

	@Override
	public Revenue save(Revenue r, int month) {
		double total = getTotalByMonth(month);
		revenueEntity.setTotal(total);
		return revenueRepo.save(r);
	}

	@Override
	public double updateTotal(int month, double total) throws Exception {
		revenueRepo.updateTotal(month, total);
		total = getTotalByMonth(month);
		return total;
	}

}
