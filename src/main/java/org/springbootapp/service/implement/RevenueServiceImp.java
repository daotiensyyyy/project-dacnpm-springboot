package org.springbootapp.service.implement;

import java.util.Date;
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
		List<Double> tmp = orderRepo.getTotal();
		for (int i = 0; i < tmp.size(); i++) {
			total += tmp.get(i);
		}
		return total;
	}

	@Override
	public Double getTotalByDate(int date) {
		double total = 0;
		List<Double> tmp = orderRepo.getTotalRevenueByDate(date);
		for (int i = 0; i < tmp.size(); i++) {
			total += tmp.get(i);
		}
		return total;
	}

	@Override
	public Revenue save(Revenue r, int date) {
		double total = getTotalByDate(date);
		revenueEntity.setTotal(total);
		return revenueRepo.save(r);
	}

	@Override
	public double updateTotal(int date, double total) throws Exception {
		revenueRepo.updateTotal(date, total);
		total = getTotalByDate(date);
		return total;
	}

	@Override
	public List<Revenue> getAll() {
		return revenueRepo.findAll();
	}

}
