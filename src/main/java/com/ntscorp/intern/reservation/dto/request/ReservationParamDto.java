package com.ntscorp.intern.reservation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationParamDto {
    private int displayInfoId;
    private List<ReservationParamPriceDto> prices;
    private int productId;
    private String reservationEmail;
    private String reservationName;
    private String reservationTelephone;
    private String reservationYearMonthDay;
    private String createDate;
    private String modifyDate;
}
