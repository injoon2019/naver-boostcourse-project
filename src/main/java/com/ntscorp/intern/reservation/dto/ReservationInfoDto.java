package com.ntscorp.intern.reservation.dto;

import com.ntscorp.intern.display.dto.DisplayInfoDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationInfoDto {
    private Boolean cancelYn;
    private String createDate;
    private DisplayInfoDto displayInfo;
    private Integer displayInfoId;
    private String modifyDate;
    private Integer productId;
    private String reservationDate;
    private String reservationEmail;
    private Integer reservationInfoId;
    private String reservationName;
    private String reservationTelephone;
    private Integer totalPrice;
}
