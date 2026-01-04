package com.theliems.sport_booking.controller;

import com.theliems.sport_booking.service.BookingService;
import com.theliems.sport_booking.service.CreateBookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestBody CreateBookingRequest request
    ) {
        Integer bookingId = bookingService.createBooking(request);
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "bookingId", bookingId
                )
        );
    }
}

