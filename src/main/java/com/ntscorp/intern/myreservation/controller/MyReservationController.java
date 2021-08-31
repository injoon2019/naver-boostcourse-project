package com.ntscorp.intern.myreservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/myreservation")
public class MyReservationController {

    @GetMapping
    public String showMyReservation(@SessionAttribute(name = "reservationEmail", required = false) String reservationEmail, Model model) {
        if (reservationEmail == null) {
            return "redirect:/";
        }
        return "myreservation";
    }
}
