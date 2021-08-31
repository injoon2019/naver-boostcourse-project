package com.ntscorp.intern.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ntscorp.intern.category.dao.CategoryDao;
import com.ntscorp.intern.category.dto.CategoryDto;

@Service
public class CategoryServiceImpl implements CategoryService{
	private final CategoryDao categoryDao;
	
	public CategoryServiceImpl(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Transactional(readOnly = true)
	public List<CategoryDto> getCategoryList() {
		return categoryDao.getCategoryList();
	}
}
