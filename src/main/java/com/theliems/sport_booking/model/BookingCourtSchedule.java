package com.theliems.sport_booking.model;

import com.theliems.sport_booking.repository.BookingCourtScheduleId;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_court_schedule")
@IdClass(BookingCourtScheduleId.class)
@Getter @Setter
public class BookingCourtSchedule {

    @Id
    private Integer bookingId;

    @Id
    private Integer courtScheduleId;
}

