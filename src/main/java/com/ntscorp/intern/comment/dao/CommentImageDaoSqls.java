package com.ntscorp.intern.comment.dao;

public class CommentImageDaoSqls {
	public static final String SELECT_COMMENT_IMAGE_BY_COMMENT_ID = 
			"SELECT file_info.content_type, file_info.create_date, file_info.delete_flag, file_info.id fileId, file_info.file_name, "
			+ "rsv_user_cmt_img.id imageId, file_info.modify_date, rsv_info.id reservationInfoId, "
			+ "rsv_user_cmt_img.reservation_user_comment_id, file_info.save_file_name "
			+ "FROM reservation_user_comment_image rsv_user_cmt_img "
			+ "JOIN file_info ON file_info.id = rsv_user_cmt_img.file_id "
			+ "JOIN reservation_user_comment rsv_user_cmt ON rsv_user_cmt_img.reservation_user_comment_id = rsv_user_cmt.id "
			+ "JOIN reservation_info rsv_info ON rsv_info.id = rsv_user_cmt_img.reservation_info_id "
			+ "WHERE rsv_user_cmt.id = :commentId";

	public static final String INSERT_FILE_INFO =
			"INSERT INTO file_info (file_name, save_file_name, content_type, delete_flag, create_date, modify_date) " +
					"VALUE (:fileName, :saveFileName, :contentType, false, now(), now())";
}
