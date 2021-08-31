package com.ntscorp.intern.booking.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService {
    private static final DateTimeFormatter DATE_FORMATTER_TO_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int RANDOM_PLUS_DAY = 5;

    public String getRandomReservationDate() {
        LocalDate currentLocalDate = LocalDate.now();
        LocalDate randomReservationDate = currentLocalDate.plusDays(new Random().nextInt(RANDOM_PLUS_DAY));
        return randomReservationDate.format(DATE_FORMATTER_TO_DAY);
    }
}
