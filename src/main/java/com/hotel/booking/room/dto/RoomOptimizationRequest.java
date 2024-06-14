package com.hotel.booking.room.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoomOptimizationRequest {

    @Min(value = 0, message = "The number of Premium rooms cannot be negative")
    private int premiumRoomCount;

    @Min(value = 0, message = "The number of Economy rooms cannot be negative")
    private int economyRoomCount;

    private List<BigDecimal> guestOffers;
}
