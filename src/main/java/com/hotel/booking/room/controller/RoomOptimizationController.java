package com.hotel.booking.room.controller;

import com.hotel.booking.room.dto.RoomOptimizationRequest;
import com.hotel.booking.room.dto.RoomOptimizationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room/occupancy")
@AllArgsConstructor
public class RoomOptimizationController {

    @PostMapping("/optimize")
    public ResponseEntity<RoomOptimizationResponse> optimizeRooms(@Valid @RequestBody RoomOptimizationRequest roomOptimizationRequest) {

        return ResponseEntity.ok().build();
    }
}
