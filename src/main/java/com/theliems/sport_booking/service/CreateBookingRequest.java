package com.theliems.sport_booking.service;

import com.theliems.sport_booking.model.SelectedSlot;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreateBookingRequest {

    private Integer clubId;
    private Integer profileId;
    private String phoneNumber;
    private String note;

    private Integer totalTime;
    private Double totalPrice;

    private List<SelectedSlot> selectedSlots;
}

