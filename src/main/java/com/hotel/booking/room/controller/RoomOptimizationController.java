package com.hotel.booking.room.controller;

import com.hotel.booking.room.dto.RoomOptimizationRequest;
import com.hotel.booking.room.dto.RoomOptimizationResponse;
import com.hotel.booking.room.facade.RoomOptimizationFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private RoomOptimizationFacade roomOptimizationFacade;

    @Operation(summary = "Optimizes Room Occupancy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room Occupancy Optimized", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = RoomOptimizationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request, please check the inputs", content =
                    { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error",  content =
                    { @Content(mediaType = "application/json") }) })
    @PostMapping("/optimize")
    public ResponseEntity<RoomOptimizationResponse> optimizeRooms(@Valid @RequestBody RoomOptimizationRequest roomOptimizationRequest) {

        final RoomOptimizationResponse response = roomOptimizationFacade.optimizeRooms(roomOptimizationRequest);

        return ResponseEntity.ok(response);
    }
}
