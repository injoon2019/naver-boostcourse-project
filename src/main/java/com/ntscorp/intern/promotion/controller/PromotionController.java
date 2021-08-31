package com.ntscorp.intern.promotion.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntscorp.intern.promotion.dto.PromotionDto;
import com.ntscorp.intern.promotion.service.PromotionService;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
	private final PromotionService promotionService;
	
	public PromotionController(PromotionService promotionService) {
		this.promotionService = promotionService;
	}
	
	@GetMapping
	public List<PromotionDto> getPromotionDtoList() {
		return promotionService.getPromotionDtoList();
	}
}
