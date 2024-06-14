package com.hotel.booking.room.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RoomUsage {

    private int numberOfRooms = 0;

    private BigDecimal revenue = BigDecimal.ZERO;

}
