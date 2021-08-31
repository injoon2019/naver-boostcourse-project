package com.ntscorp.intern.display.dao.displayinfo;

public class DisplayInfoDaoSqls {
	public static final String SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID = 
			"SELECT category.id categoryId, category.name categoryName, display_info.create_date, display_info.id displayInfoId, "
			+ "display_info.email, display_info.homepage, display_info.modify_date, display_info.opening_hours, "
			+ "display_info.place_lot, display_info.place_name,  display_info.place_street, product.content productContent, "
			+ "product.description productDescription, product.event productEvent, product.id productId, display_info.tel telephone "
			+ "FROM display_info "
			+ "JOIN product ON display_info.product_id = product.id "
			+ "JOIN category ON product.category_id = category.id "
			+ "WHERE display_info.id = :displayInfoId ";
}
