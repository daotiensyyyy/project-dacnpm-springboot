package org.springbootapp.repository;

import java.util.List;

import org.springbootapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>{

	@Query("Select o  FROM Order o WHERE o.user_id=:user_id")
	List<Order> getByUserId(@Param("user_id")Long user_id);
	
	@Query("Select o.price FROM Order o group by o.order_id")
	List<Double> getPriceGroupByOrderId();
	
	@Query("Select o.price FROM Order o WHERE month(o.order_date)=:month group by o.order_id")
	List<Double> getTotalRevenueByMonth(@Param("month") int month);
}
