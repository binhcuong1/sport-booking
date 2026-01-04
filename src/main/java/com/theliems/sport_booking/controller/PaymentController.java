package com.theliems.sport_booking.controller;

import com.theliems.sport_booking.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final VNPayService vnpayService;

    @PostMapping("/vnpay/create")
    public ResponseEntity<?> createPayment(
            @RequestBody Map<String, Long> body,
            HttpServletRequest request
    ) {
        Long bookingId = body.get("bookingId");
        Long amount = body.get("amount");

        return ResponseEntity.ok(
                vnpayService.createPayment(bookingId, amount, request)
        );
    }



    @GetMapping("/vnpay/return")
    public void vnpayReturn(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String redirectUrl = vnpayService.handleReturn(request);
        response.sendRedirect(redirectUrl);
    }
}

