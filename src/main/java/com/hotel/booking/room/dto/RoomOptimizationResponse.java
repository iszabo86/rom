package com.hotel.booking.room.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomOptimizationResponse {


    private RoomUsage premiumRoomUsage = new RoomUsage();

    private RoomUsage economyRoomUsage = new RoomUsage();


}
