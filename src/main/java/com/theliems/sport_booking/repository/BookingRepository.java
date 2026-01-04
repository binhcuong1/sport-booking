package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository
        extends JpaRepository<Booking, Integer> {
                    @Query("""
  SELECT (COUNT(b) > 0)
  FROM Booking b
  WHERE b.profile.profile_id = :profileId
    AND b.club.id = :clubId
    AND b.booking_status = 'hoàn thành'
  """)
    boolean hasCompletedBooking(Integer profileId, Integer clubId);
}
