package com.ntscorp.intern.promotion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ntscorp.intern.promotion.dao.PromotionDao;
import com.ntscorp.intern.promotion.dto.PromotionDto;

@Service
public class PromotionServiceImpl implements PromotionService{
	private final PromotionDao promotionDao;
	
	public PromotionServiceImpl(PromotionDao promotionDao) {
		this.promotionDao = promotionDao;
	}
	
	@Transactional(readOnly = true)
	public List<PromotionDto> getPromotionDtoList() {
		return promotionDao.getPromotionDtoList();
	}	
}
