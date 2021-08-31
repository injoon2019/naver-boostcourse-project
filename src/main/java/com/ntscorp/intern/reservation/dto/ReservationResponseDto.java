package com.ntscorp.intern.reservation.dto;

import com.ntscorp.intern.reservation.dto.request.ReservationParamPriceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationResponseDto {
    private Boolean cancelYn;
    private String createDate;
    private Integer displayInfoId;
    private String modifyDate;
    private List<ReservationParamPriceDto> prices;
    private Integer productId;
    private String reservationDate;
    private String reservationEmail;
    private Integer reservationInfoId;
    private String reservationName;
    private String reservationTelephone;
}
