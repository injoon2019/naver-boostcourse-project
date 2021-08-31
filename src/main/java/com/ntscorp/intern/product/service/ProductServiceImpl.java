package com.ntscorp.intern.product.service;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ntscorp.intern.product.dao.ProductDao;
import com.ntscorp.intern.product.dto.ProductDto;
import com.ntscorp.intern.product.dto.ProductListDto;

@Service
public class ProductServiceImpl implements ProductService{
	private final ProductDao productDao;
	
	public ProductServiceImpl(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Transactional(readOnly = true)
	public ProductListDto getProductListByCategoryId(int categoryId, int offset) throws IOException {
		ProductListDto productListDto;
		int totalCount = productDao.getTotalCountByCategoryId(categoryId);
		
		if (totalCount == 0) {
			productListDto = new ProductListDto(Collections.emptyList(), totalCount);
		} else {
			List<ProductDto> productDtoList = productDao.selectByCategoryId(categoryId, offset);
			productListDto = new ProductListDto(productDtoList, totalCount);			
		}

		return productListDto;
	}
	
	@Transactional(readOnly = true)
	public ProductListDto getAllProductList(int offset) throws IOException {
		ProductListDto productListDto;
		int totalCount = productDao.getTotalCount();
		
		if (totalCount == 0) {
			productListDto = new ProductListDto(Collections.emptyList(), totalCount);
		} else {
			List<ProductDto> productDtoList = productDao.selectAllCategoryList(offset);
			productListDto = new ProductListDto(productDtoList, totalCount);
		}
		
		return productListDto;
	}
}
