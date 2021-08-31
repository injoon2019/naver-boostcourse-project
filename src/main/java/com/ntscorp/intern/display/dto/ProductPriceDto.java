package com.ntscorp.intern.display.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPriceDto {
	private String createDate;
	private double discountRate;
	private String modifyDate;
	private int price;
	private String priceTypeName;
	private int productId;
	private int productPriceId;
}
