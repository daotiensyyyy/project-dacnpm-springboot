package org.springbootapp;

import org.springbootapp.repository.ICartRepository;
import org.springbootapp.repository.IRevenueRepository;
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
		IRevenueRepository r = context.getBean(IRevenueRepository.class);
//		System.out.println(ur.findActiveAccount("admin"));
//		System.out.println(cr.getCartByProductIdAnduserId((long)2,(long) 13));
		System.out.println(r.getTotalRevenueByMonth(5));
	}

}
