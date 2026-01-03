package com.theliems.sport_booking.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    private Integer clubId;
    private Integer profileId;
    private Integer totalTime;
    private Double totalPrice;
    private String note;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "booking_status")
    private BookingStatus bookingStatus = BookingStatus.DANG_XU_LY;

    private LocalDateTime createdAt;
}
