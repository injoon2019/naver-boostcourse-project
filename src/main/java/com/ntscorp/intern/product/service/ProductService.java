package com.ntscorp.intern.product.service;

import java.io.IOException;

import com.ntscorp.intern.product.dto.ProductListDto;

public interface ProductService {
	ProductListDto getProductListByCategoryId(int categoryId, int offset) throws IOException;
	ProductListDto getAllProductList(int offset) throws IOException;
}
