package com.hotel.booking.room.facade.impl;

import com.hotel.booking.room.dto.RoomOptimizationRequest;
import com.hotel.booking.room.dto.RoomOptimizationResponse;
import com.hotel.booking.room.facade.RoomOptimizationFacade;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoomOptimizationFacadeImpl implements RoomOptimizationFacade {

    private static final Logger LOG = LoggerFactory.getLogger(RoomOptimizationFacadeImpl.class);


    /**
     * {@inheritDoc}
     */
    @Override
    public RoomOptimizationResponse optimizeRooms(RoomOptimizationRequest request) {

        return null;
    }
}
