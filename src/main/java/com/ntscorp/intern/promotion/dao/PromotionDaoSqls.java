package com.ntscorp.intern.promotion.dao;

public class PromotionDaoSqls {
	public static final String SELECT_ALL_PROMOTION_LIST = 
			"SELECT promotion.id, promotion.product_id, file_info.save_file_name AS productImageUrl "
			   + "FROM promotion JOIN product ON promotion.product_id = product.id "
			   + "JOIN product_image ON product.id = product_image.product_id "
			   + "JOIN file_info ON product_image.file_id = file_info.id "
			   + "WHERE product_image.type = 'th' ";
}
