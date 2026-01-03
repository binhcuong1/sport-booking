package com.theliems.sport_booking.repository;

import java.io.Serializable;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingCourtScheduleId implements Serializable {

    private Integer bookingId;
    private Integer courtScheduleId;
}