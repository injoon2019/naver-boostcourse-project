package com.ntscorp.intern.product.dao;

public class ProductDaoSqls {
	public static final String SELECT_ALL_PRODUCT_BY_CATEGORY_ID = 
			"SELECT display_info.id, display_info.place_name, product.content AS ProductContent, "
			+ "product.description AS productDescription, "
			+ "display_info.product_id, file_info.save_file_name AS productImageUrl "
			+ "FROM product JOIN display_info ON product.id = display_info.product_id "
			+ "JOIN product_image ON product.id = product_image.product_id "
			+ "JOIN file_info ON product_image.file_id = file_info.id "
			+ "WHERE product.category_id = :categoryId AND product_image.type = 'th' "
			+ "LIMIT 4 OFFSET :offset";

	public static final String SELECT_ALL_PRODUCT = 
			"SELECT display_info.id, display_info.place_name, product.content AS ProductContent, "
			+ "product.description AS productDescription, "
			+ "display_info.product_id, file_info.save_file_name AS productImageUrl "
			+ "FROM product JOIN display_info ON product.id = display_info.product_id "
			+ "JOIN product_image ON product.id = product_image.product_id "
			+ "JOIN file_info ON product_image.file_id = file_info.id "
			+ "WHERE product_image.type = 'th' "
			+ "LIMIT 4 OFFSET :offset";
	
	
	public static final String SELECT_PRODUCT_TOTAL_COUNT_BY_CATEGORY_ID = 
			"SELECT COUNT(*) "
			+ "FROM product JOIN display_info ON product.id = display_info.product_id "
			+ "JOIN product_image ON product.id = product_image.product_id "
			+ "JOIN file_info ON product_image.file_id = file_info.id "
			+ "WHERE product_image.type = 'th' AND product.category_id = :categoryId ";
	
	public static final String SELECT_PRODUCT_TOTAL_COUNT = 
			"SELECT COUNT(*) "
			+ "FROM product JOIN display_info ON product.id = display_info.product_id "
			+ "JOIN product_image ON product.id = product_image.product_id "
			+ "JOIN file_info ON product_image.file_id = file_info.id "
			+ "WHERE product_image.type = 'th' ";
}
