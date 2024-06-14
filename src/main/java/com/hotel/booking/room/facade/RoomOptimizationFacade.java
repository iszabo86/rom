package com.hotel.booking.room.facade;

import com.hotel.booking.room.dto.RoomOptimizationRequest;
import com.hotel.booking.room.dto.RoomOptimizationResponse;

public interface RoomOptimizationFacade {

    /**
     * Based on the details of the request object, optimizes the room occupancy with a maximized revenue
     * @param request instance of RoomOptimizationRequest that holds the premium and economy room numbers, and guest offers
     * @return instance RoomOptimizationResponse that includes the number of rooms which could be occupied with a calculated
     * maximum revenue
     */
    RoomOptimizationResponse optimizeRooms(RoomOptimizationRequest request);
}
