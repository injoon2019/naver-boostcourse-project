package com.ntscorp.intern.display.dao.displayinfoimage;

public class DisplayInfoImageDaoSqls {
	public static final String SELECT_DISPLAY_INFO_IMAGE_BY_DISPLAY_INFO_ID = 
			"SELECT file_info.content_type, file_info.create_date, file_info.delete_flag, dp_info.id displayInfoId, "
			+ "dp_info_img.id displayInfoImageId, file_info.id fileId, file_info.file_name, file_info.modify_date, "
			+ "file_info.save_file_name "
			+ "FROM product pdt "
			+ "JOIN display_info dp_info ON pdt.id = dp_info.product_id "
			+ "JOIN display_info_image dp_info_img ON dp_info.id = dp_info_img.display_info_id "
			+ "JOIN file_info ON dp_info_img.file_id = file_info.id "
			+ "WHERE dp_info.id = :displayInfoId ";
}
