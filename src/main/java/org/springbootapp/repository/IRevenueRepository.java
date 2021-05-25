package org.springbootapp.repository;

import java.util.Date;

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
	@Query("update Revenue r set r.total=:total WHERE r.date=:date")
	void updateTotal(@Param("date")int date, @Param("total") double total);

}
