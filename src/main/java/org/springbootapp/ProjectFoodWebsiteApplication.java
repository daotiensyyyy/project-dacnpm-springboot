package org.springbootapp;

import org.springbootapp.repository.IUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProjectFoodWebsiteApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProjectFoodWebsiteApplication.class, args);
		IUserRepository ur = context.getBean(IUserRepository.class);
//		System.out.println(ur.findActiveAccount("admin"));
	}

}
