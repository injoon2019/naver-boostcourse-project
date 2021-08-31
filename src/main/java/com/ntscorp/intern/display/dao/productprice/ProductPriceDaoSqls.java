package com.ntscorp.intern.display.dao.productprice;

public class ProductPriceDaoSqls {
	public static final String SELECT_PRODUCT_PRICE_BY_DISPLAY_INFO_ID = 
			"SELECT pdt_price.create_date, pdt_price.discount_rate, pdt_price.modify_date, pdt_price.price, "
			+ "price_type.type_full_name AS price_type_name, pdt.id productId, pdt_price.id productPriceId "
			+ "FROM product pdt "
			+ "JOIN display_info dp_info ON pdt.id = dp_info.product_id "
			+ "JOIN product_price pdt_price ON pdt.id = pdt_price.product_id "
			+ "JOIN price_type ON pdt_price.price_type_name = price_type.type_abbreviation "
			+ "WHERE dp_info.id = :displayInfoId ";
}
