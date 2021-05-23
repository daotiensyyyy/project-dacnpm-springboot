package org.springbootapp.repository;

import javax.transaction.Transactional;

import org.springbootapp.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRevenueRepository extends JpaRepository<Revenue, Long> {
	
	@Modifying
	@Transactional
	@Query("update Revenue r set r.total=:total WHERE r.month=:month")
	void updateTotal(@Param("month")int month, @Param("total") double total);

}
