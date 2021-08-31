package com.ntscorp.intern.bookinglogin.controller;

import com.ntscorp.intern.reservation.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/bookinglogin")
public class BookingLoginController {
    private static final int SESSION_MAX_INACTIVE_INTERVAL_SEC = 3600;
    private final ReservationService reservationService;

    public BookingLoginController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String showBookinglogin() {
        return "bookinglogin";
    }

    @PostMapping
    public String handleLogin(@RequestParam("resrv_email") String reservationEmail, HttpSession session) {
        boolean existEmail = reservationService.existReservationEmail(reservationEmail);
        if (existEmail) {
            session.setAttribute("reservationEmail", reservationEmail);
            session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL_SEC);
            return "redirect:myreservation";
        }
        return "bookinglogin";
    }
}
