package com.theliems.sport_booking.service;

import com.theliems.sport_booking.model.*;
import com.theliems.sport_booking.repository.BookingCourtScheduleRepository;
import com.theliems.sport_booking.repository.BookingRepository;
import com.theliems.sport_booking.repository.ScheduleSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final BookingCourtScheduleRepository bookingSlotRepo;
    private final ScheduleSlotRepository slotRepo;

    @Transactional
    public Integer createBooking(CreateBookingRequest req) {

        // 1️⃣ Check slot hợp lệ
        List<ScheduleSlot> slots = slotRepo.findAllById(
                req.getSelectedSlots()
                        .stream()
                        .map(SelectedSlot::getCourtScheduleId)
                        .toList()
        );

        for (ScheduleSlot slot : slots) {
            if (slot.getStatus() != CourtScheduleStatus.available) {
                throw new RuntimeException("Slot đã được đặt hoặc bị khóa");
            }
        }

        // 2️⃣ Tạo booking
        Booking booking = new Booking();
        booking.setClubId(req.getClubId());
        booking.setProfileId(req.getProfileId());
        booking.setPhoneNumber(req.getPhoneNumber());
        booking.setNote(req.getNote());
        booking.setPaymentMethod(PaymentMethod.vnpay);
        booking.setTotalTime(req.getTotalTime());
        booking.setTotalPrice(req.getTotalPrice());
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepo.save(booking);

        // 3️⃣ Insert booking_court_schedule + update slot → booked
        for (ScheduleSlot slot : slots) {
            BookingCourtSchedule bcs = new BookingCourtSchedule();
            bcs.setBookingId(booking.getBookingId());
            bcs.setCourtScheduleId(slot.getCourtScheduleId());
            bookingSlotRepo.save(bcs);

            slot.setStatus(CourtScheduleStatus.valueOf("booked"));
            slotRepo.save(slot);
        }

        return booking.getBookingId();
    }
}
