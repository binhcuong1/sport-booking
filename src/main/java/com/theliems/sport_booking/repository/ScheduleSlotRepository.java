package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.ScheduleSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleSlotRepository extends JpaRepository<ScheduleSlot, Integer> {

    List<ScheduleSlot> findByClubIdAndDate(Integer clubId, LocalDate date);

    List<ScheduleSlot> findByCourtIdAndDateOrderByStartTime(Integer courtId, LocalDate date);

    Optional<ScheduleSlot> findByClubIdAndCourtIdAndDateAndStartTime(
            Integer clubId, Integer courtId, LocalDate date, LocalTime startTime);

    boolean existsByCourtIdAndDateAndStartTime(Integer courtId, LocalDate date, LocalTime startTime);
}
