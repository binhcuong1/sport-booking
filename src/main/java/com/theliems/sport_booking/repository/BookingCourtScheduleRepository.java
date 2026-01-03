package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.BookingCourtSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingCourtScheduleRepository
        extends JpaRepository<BookingCourtSchedule, BookingCourtScheduleId> {
}
