package com.ntscorp.intern.reservation.dao;

public class ReservationDaoSqls {
    public static final String SELECT_RESERVATION_INFO_BY_RESERVATION_EMAIL =
            "SELECT rsv_info.cancel_flag as cancelYn, rsv_info.create_date, rsv_info.display_info_id, rsv_info.modify_date, " +
            "rsv_info.product_id, rsv_info.reservation_date, rsv_info.reservation_email, rsv_info.id AS reservationInfoId, " +
            "rsv_info.reservation_name, rsv_info.reservation_tel AS reservationTelephone, SUM(pdt_prc.price * rsv_info_prc.count) AS totalPrice " +
            "FROM reservation_info rsv_info " +
            "JOIN reservation_info_price rsv_info_prc ON rsv_info.id = rsv_info_prc.reservation_info_id " +
            "JOIN product_price pdt_prc ON rsv_info_prc.product_price_id = pdt_prc.id " +
            "WHERE rsv_info.reservation_email = :reservationEmail " +
            "GROUP BY rsv_info.id " +
            "ORDER BY rsv_info.reservation_date DESC ";

    public static final String INSERT_RESERVATION_INFO =
            "INSERT INTO reservation_info (product_id, display_info_id, reservation_name, reservation_tel, reservation_email," +
                    "reservation_date, create_date, modify_date) " +
                    "VALUE (:productId, :displayInfoId, :reservationName, :reservationTelephone, :reservationEmail, :reservationYearMonthDay, :createDate, :modifyDate)";

    public static final String INSERT_RESERVATION_INFO_PRICE =
            "INSERT INTO reservation_info_price (reservation_info_id, product_price_id, count) " +
                    "VALUE (:reservationInfoId, :productPriceId, :count)";

    public static final String UPDATE_RESERVATION_BY_ID =
            "UPDATE reservation_info SET cancel_flag = 1, modify_date = now() WHERE id = :reservationId";

    public static final String  CHECK_RESERVATION_EMAIL_EXIST =
            "SELECT COUNT(*) FROM reservation_info WHERE reservation_email = :reservationEmail";

    public static final String  SELECT_RESERVATION_INFO_BY_ID =
            "SELECT rsv_info.cancel_flag as cancelYn, rsv_info.create_date, rsv_info.display_info_id, rsv_info.modify_date, " +
                    "rsv_info.product_id, rsv_info.reservation_date, rsv_info.reservation_email, rsv_info.id AS reservationInfoId, " +
                    "rsv_info.reservation_name, rsv_info.reservation_tel AS reservationTelephone, SUM(pdt_prc.price * rsv_info_prc.count) AS totalPrice " +
                    "FROM reservation_info rsv_info " +
                    "JOIN reservation_info_price rsv_info_prc ON rsv_info.id = rsv_info_prc.reservation_info_id " +
                    "JOIN product_price pdt_prc ON rsv_info_prc.product_price_id = pdt_prc.id " +
                    "WHERE rsv_info.id = :reservationInfoId " +
                    "GROUP BY rsv_info.id " +
                    "ORDER BY rsv_info.reservation_date DESC ";
}
