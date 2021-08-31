package com.ntscorp.intern.comment.dao;

public class CommentDaoSqls {
	public static final String SELECT_COMMENT_BY_DISPLAY_ID = 
			"SELECT rsv_user_cmt.comment, rsv_user_cmt.id commentId, rsv_user_cmt.create_date, rsv_user_cmt.modify_date, "
			+ "rsv_user_cmt.product_id, rsv_info.reservation_date, rsv_info.reservation_email, rsv_info.id AS reservationInfoId, "
			+ "rsv_info.reservation_name, rsv_info.reservation_tel AS reservationTelephone, rsv_user_cmt.score "
			+ "FROM product "
			+ "JOIN reservation_info rsv_info ON product.id = rsv_info.product_id "
			+ "JOIN reservation_user_comment rsv_user_cmt ON rsv_user_cmt.reservation_info_id = rsv_info.id "
			+ "JOIN display_info ON display_info.product_id = product.id "
			+ "WHERE display_info.id = :displayInfoId "
			+ "ORDER BY rsv_info.reservation_date DESC ";
	
	public static final String SELECT_AVERAGE_SCORE_BY_DISPLAY_ID = 
			"SELECT IFNULL(AVG(rsv_user_cmt.score), 0) AS averageScore "
			+ "FROM product pdt "
			+ "JOIN display_info dp_info ON dp_info.product_id = pdt.id "
			+ "JOIN reservation_user_comment rsv_user_cmt ON pdt.id = rsv_user_cmt.product_id "
			+ "WHERE dp_info.id = :displayInfoId ";

	public static final String INSERT_RESERVATION_USER_COMMENT =
			"INSERT INTO reservation_user_comment (product_id, reservation_info_id, score, comment, create_date, modify_date) " +
					"VALUE (:productId, :reservationInfoId, :score, :comment, now(), now())";

	public static final String INSERT_RESERVATION_COMMENT_IMAGE =
			"INSERT INTO reservation_user_comment_image (reservation_info_id, reservation_user_comment_id, file_id) " +
					"VALUE (:reservationInfoId, :reservationUserCommentId, :fileId)";
}
