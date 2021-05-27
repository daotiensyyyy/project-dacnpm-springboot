package org.springbootapp.api;

import java.util.List;

import org.springbootapp.dto.MessageResponse;
import org.springbootapp.entity.Revenue;
import org.springbootapp.service.IRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class RevenueController {
	@Autowired
	IRevenueService revenueService;

	@RequestMapping(value = "/revenue", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
	public ResponseEntity<?> updateMonthRevenue(@RequestParam("date") int date) {
		try {
			double total = revenueService.getTotalByDate(date);
			total = revenueService.updateTotal(date, total);
			return ResponseEntity.ok(total);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
		}
	}

	@RequestMapping(value = "/revenue", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
	public ResponseEntity<?> getRevenue() {
		List<Revenue> obj = revenueService.getAll();
		return ResponseEntity.ok(obj);

	}
}
