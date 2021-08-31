package com.ntscorp.intern.reservation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationParamPriceDto {
    private int count;
    private int productPriceId;
    private int reservationInfoId;
    private int	reservationInfoPriceId;
}
