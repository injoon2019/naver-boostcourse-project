package com.ntscorp.intern.product.service;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.junit.Test;
import org.mockito.Mock;

import com.ntscorp.intern.config.ApplicationConfig;
import com.ntscorp.intern.config.DBConfig;
import com.ntscorp.intern.config.WebMvcContextConfiguration;
import com.ntscorp.intern.product.dto.ProductListDto;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebMvcContextConfiguration.class, DBConfig.class, ApplicationConfig.class})
public class ProductServiceImplTest {
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void testGetProductListByCategoryId() throws IOException {
		ProductListDto productListDto = productService.getProductListByCategoryId(1, 0);
		assertTrue(productListDto.getTotalCount() == 10);
	}

	@Test
	public void testGetAllProductList() throws IOException {
		ProductListDto productListDto = productService.getAllProductList(0);
		assertTrue(productListDto.getTotalCount() > 0);
	}
}
