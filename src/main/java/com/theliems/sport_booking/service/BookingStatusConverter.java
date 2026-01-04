package com.theliems.sport_booking.service;

import com.theliems.sport_booking.model.BookingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookingStatusConverter
        implements AttributeConverter<BookingStatus, String> {

    @Override
    public String convertToDatabaseColumn(BookingStatus status) {
        if (status == null) return null;
        return switch (status) {
            case DANG_XU_LY -> "đang xử lý";
            case HOAN_THANH -> "hoàn thành";
            case HUY -> "hủy";
        };
    }

    @Override
    public BookingStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case "đang xử lý" -> BookingStatus.DANG_XU_LY;
            case "hoàn thành" -> BookingStatus.HOAN_THANH;
            case "hủy" -> BookingStatus.HUY;
            default -> throw new IllegalArgumentException("Unknown booking_status: " + dbData);
        };
    }
}