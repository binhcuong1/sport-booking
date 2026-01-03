package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository
        extends JpaRepository<Booking, Integer> {
}
