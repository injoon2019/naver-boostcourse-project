package com.ntscorp.intern.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationInfoDetailDto {
    private List<ReservationInfoDto> reservations;
    private Integer size;
}
