package com.ntscorp.intern.booking.controller;

import com.ntscorp.intern.booking.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String showBooking(@RequestParam int id, Model model) {
        String reservationDate = bookingService.getRandomReservationDate();
        model.addAttribute("reservationDateTime", reservationDate);
        return "reservation";
    }
}
