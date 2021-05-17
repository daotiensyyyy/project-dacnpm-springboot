package org.springbootapp.repository;

import org.springbootapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRevenueRepository extends JpaRepository<Order, Long> {

//	@Query("select o from Order o")
//	@Query("select sum(o.price) from Order o ")
	@Query("Select sum(o.price) FROM Order o WHERE month(o.order_date)=:month")
	double getTotalRevenueByMonth(@Param("month") int month);
}
