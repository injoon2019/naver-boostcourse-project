package com.ntscorp.intern.display.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ntscorp.intern.display.dto.DisplayDetail;
import com.ntscorp.intern.display.service.DisplayService;

@RestController
public class DisplayController {
	private final DisplayService displayService;
	
	public DisplayController(DisplayService displayService) {
		this.displayService = displayService;
	}
	
	@GetMapping("/api/products/{displayInfoId}")
	public DisplayDetail getDisplayDetailDto(@PathVariable int displayInfoId) {
		return displayService.getDisplayDetail(displayInfoId);
	}
}
