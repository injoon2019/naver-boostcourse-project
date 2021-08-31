package com.ntscorp.intern.product.controller;


import com.ntscorp.intern.display.service.DisplayService;
import com.ntscorp.intern.product.dto.ProductListDto;
import com.ntscorp.intern.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ProductController {
	public static final Integer ALL_CATEGORY_ID = 0;
	private final ProductService productService;

	public ProductController(ProductService productService, DisplayService displayService) {
		this.productService = productService;
	}
	
	@GetMapping("/api/products")
	public ProductListDto getProductList(@RequestParam(defaultValue = "0") int categoryId,
			@RequestParam(name = "start", defaultValue = "0") int offset) throws IOException {
		if (categoryId == ALL_CATEGORY_ID) {
			return productService.getAllProductList(offset);
		} else {
			return productService.getProductListByCategoryId(categoryId, offset);
		}
	}
}
