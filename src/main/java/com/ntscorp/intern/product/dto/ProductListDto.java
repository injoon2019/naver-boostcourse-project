package com.ntscorp.intern.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductListDto {
	private List<ProductDto> items;
	private int totalCount;
}
