package org.springbootapp.config;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Category;
import org.springbootapp.entity.Image;
import org.springbootapp.entity.Order;
import org.springbootapp.entity.Product;
import org.springbootapp.entity.Revenue;
import org.springbootapp.entity.Role;
import org.springbootapp.entity.User;
import org.springbootapp.jwt.AuthEntryPointJwt;
import org.springbootapp.jwt.AuthenticationFilter;
import org.springbootapp.service.implement.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public AuthenticationFilter jwtAuthenticationFilter() {
		return new AuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Get AuthenticationManager bean
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// Password encoder, ????? Spring Security s??? d???ng m?? h??a m???t kh???u ng?????i d??ng
		return new BCryptPasswordEncoder();
	}

	@Bean
	public User user() {
		return new User();
	}

	@Bean
	public Role role() {
		return new Role();
	}
	

	@Bean
	public Product product() {
		return new Product();
	}
	

	@Bean
	public Category category() {
		return new Category();
	}
	

	@Bean
	public Image image() {
		return new Image();
	}
	
	@Bean
	public Cart cart() {
		return new Cart();
	}
	
	@Bean
	public Order order() {
		return new Order();
	}
	
	@Bean
	public Revenue revenue() {
		return new Revenue();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService) // Cung c??p userservice cho spring security
				.passwordEncoder(passwordEncoder()); // cung c???p password encoder
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
//		.antMatchers("/login").permitAll().antMatchers("/register").permitAll().antMatchers("/products").permitAll()
//		.antMatchers("/files/upload").permitAll()
//		.antMatchers(HttpMethod.POST,"/products").permitAll()
//		.antMatchers(HttpMethod.GET,"/products").permitAll()
//		.antMatchers(HttpMethod.POST,"/categories").permitAll().antMatchers(HttpMethod.GET,"/categories").permitAll()
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated();
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
}
