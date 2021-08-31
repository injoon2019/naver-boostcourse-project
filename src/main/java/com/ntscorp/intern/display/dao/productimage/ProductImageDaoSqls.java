package com.ntscorp.intern.display.dao.productimage;

public class ProductImageDaoSqls {
	public static final String SELECT_PRODUCT_IMAGE_LIST_BY_DISPLAY_INFO_ID = 
			"SELECT file_info.content_type, file_info.create_date, file_info.delete_flag, file_info.id fileInfoId, "
			+ "file_info.file_name, file_info.modify_date,  pdt.id productId, pdt_img.id productImageId, "
			+ "file_info.save_file_name, pdt_img.type "
			+ "FROM product pdt "
			+ "JOIN display_info dp_info ON pdt.id = dp_info.product_id "
			+ "JOIN product_image pdt_img ON pdt.id = pdt_img.product_id "
			+ "JOIN file_info ON pdt_img.file_id = file_info.id "
			+ "WHERE dp_info.id = :displayInfoId AND pdt_img.type IN ('ma', 'et') "
			+ "LIMIT 2";
}
