package com.ntscorp.intern.reservation.controller;

import com.ntscorp.intern.commons.exception.customexception.InvalidInputException;
import com.ntscorp.intern.commons.validator.ReservationValidator;
import com.ntscorp.intern.reservation.dto.ReservationInfoDetailDto;
import com.ntscorp.intern.reservation.dto.ReservationResponseDto;
import com.ntscorp.intern.reservation.dto.request.ReservationParamDto;
import com.ntscorp.intern.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    private final ReservationService reservationService;
    private final ReservationValidator reservationValidator;

    public ReservationRestController(ReservationService reservationService, ReservationValidator reservationValidator) {
        this.reservationService = reservationService;
        this.reservationValidator = reservationValidator;
    }

    @GetMapping
    public ReservationInfoDetailDto getReservationDetailDto(@RequestParam String reservationEmail) throws InvalidInputException {
        if (!reservationValidator.validEmail(reservationEmail)) {
            throw new InvalidInputException();
        }
        return reservationService.getReservationDetailDtoByEmail(reservationEmail);
    }

    @PostMapping
    public ReservationResponseDto addReservationParamDto(@RequestBody ReservationParamDto reservationParamDto) throws InvalidInputException {
        if (!reservationService.validReservationParamDto(reservationParamDto)) {
            throw new InvalidInputException();
        }
        return reservationService.addReservationInfo(reservationParamDto);
    }

    @PutMapping("/{reservationId}")
    public ReservationResponseDto cancelReservation(@PathVariable int reservationId) {
        if (!reservationService.validReservationId(reservationId)) {
            throw new InvalidInputException();
        }
        return reservationService.cancelReservation(reservationId);
    }
}
