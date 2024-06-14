package com.hotel.booking.room.service;

import com.hotel.booking.room.dto.GuestOffers;
import com.hotel.booking.room.dto.RoomUsage;

import java.math.BigDecimal;
import java.util.List;

public interface GuestOfferService {

    /**
     * It processes the guest offers by categorizing and prioritizing them.
     * @param guestOffers All the offers received from the hotel
     * @return instance of the GuestOffers class which holds the Premium Room offers in descending order and holds the
     * Economy room offers in descending order.
     */
    GuestOffers processOffers(List<BigDecimal> guestOffers);

    /**
     * Removes excess offers from both Premium and Economy based on the room counts
     * @param offers holds the offer lists to be resized
     * @param premiumRoomCount Premium room count
     * @param economyRoomCount Economy room count
     */
    void removeExcessOffers(GuestOffers offers, int premiumRoomCount, int economyRoomCount);

    /**
     * Calculates the room usage based on the offers
     * @param offers offer list to be summarized
     * @return the room usage which includes the number of rooms and the sum value of the offers
     */
    RoomUsage calculateRoomUsage(List<BigDecimal> offers);
}
