package com.hotel.booking.room.service;

import com.hotel.booking.room.dto.GuestOffers;

public interface GuestOfferUpgradeService {

    void upgradeIfPossible(GuestOffers guestOffers, int premiumRoomCount, int economyRoomCount);
}
