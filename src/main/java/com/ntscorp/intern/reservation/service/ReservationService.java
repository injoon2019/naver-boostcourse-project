package com.ntscorp.intern.reservation.service;

import com.ntscorp.intern.reservation.dto.ReservationInfoDetailDto;
import com.ntscorp.intern.reservation.dto.ReservationInfoDto;
import com.ntscorp.intern.reservation.dto.ReservationResponseDto;
import com.ntscorp.intern.reservation.dto.request.ReservationParamDto;

public interface ReservationService {
    ReservationInfoDetailDto getReservationDetailDtoByEmail(String reservationEmail);
    ReservationInfoDto getReservationInfoDtoById(int reservationInfoId);
    ReservationResponseDto addReservationInfo(ReservationParamDto reservationParamDto);
    ReservationResponseDto cancelReservation(int reservationId);
    boolean existReservationEmail(String reservationEmail);
    boolean validReservationParamDto(ReservationParamDto reservationParamDto);
    boolean validReservationId(int reservationId);
}
