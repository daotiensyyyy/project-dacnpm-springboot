package org.springbootapp;

import org.springbootapp.repository.IOrderRepository;
import org.springbootapp.service.implement.RevenueServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProjectFoodWebsiteApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProjectFoodWebsiteApplication.class, args);
//		IUserRepository ur = context.getBean(IUserRepository.class);
//		IProductRepository pr = context.getBean(IProductRepository.class);
//		ICartRepository cr = context.getBean(ICartRepository.class);
		IOrderRepository or = context.getBean(IOrderRepository.class);
		RevenueServiceImp rs = context.getBean(RevenueServiceImp.class);

//		System.out.println(ur.findActiveAccount("admin"));
//		System.out.println(cr.getCartByProductIdAnduserId((long)2,(long) 13));
		System.out.println("getPriceGroupByOrderId "+or.getPriceGroupByOrderId());
		System.out.println("getTotalRevenueByMonth "+or.getTotalRevenueByMonth(6));
		System.out.println("getTotal "+rs.getTotal());
		System.out.println("getTotalByMonth "+rs.getTotalByMonth(5));
	}

}
