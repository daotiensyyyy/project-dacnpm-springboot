package org.springbootapp.api;

import org.springbootapp.dto.MessageResponse;
import org.springbootapp.entity.Revenue;
import org.springbootapp.service.IRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> createProduct( @RequestParam("month") int month) {
		try {
			double total = revenueService.getTotalByMonth(month);
//			Revenue r = new Revenue();
//			r.setTotal(total);
			total = revenueService.updateTotal(month, total);
			return ResponseEntity.ok(total);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), ""));
		}
	}
}
