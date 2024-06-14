package com.hotel.booking.room.facade.impl;

import com.hotel.booking.room.dto.GuestOffers;
import com.hotel.booking.room.dto.RoomOptimizationRequest;
import com.hotel.booking.room.dto.RoomOptimizationResponse;
import com.hotel.booking.room.dto.RoomUsage;
import com.hotel.booking.room.facade.RoomOptimizationFacade;
import com.hotel.booking.room.service.GuestOfferService;
import com.hotel.booking.room.service.GuestOfferUpgradeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@AllArgsConstructor
public class RoomOptimizationFacadeImpl implements RoomOptimizationFacade {

    private static final Logger LOG = LoggerFactory.getLogger(RoomOptimizationFacadeImpl.class);

    private GuestOfferService guestOfferService;

    private GuestOfferUpgradeService guestOfferUpgradeService;

    /**
     * {@inheritDoc}
     */
    @Override
    public RoomOptimizationResponse optimizeRooms(RoomOptimizationRequest request){
        LOG.info("Optimizing rooms");

        final RoomOptimizationResponse response = new RoomOptimizationResponse();

        if (CollectionUtils.isEmpty(request.getGuestOffers())){
            LOG.info("No offers received");
            return response;
        }
        final int premiumRoomCount = request.getPremiumRoomCount();
        final int economyRoomCount = request.getEconomyRoomCount();

        //categorizing and prioritizing the offers
        final GuestOffers guestOffers = guestOfferService.processOffers(request.getGuestOffers());

        //Upgrading offers if possible
        guestOfferUpgradeService.upgradeIfPossible(guestOffers, premiumRoomCount, economyRoomCount);

        guestOfferService.removeExcessOffers(guestOffers, premiumRoomCount, economyRoomCount);

        final RoomUsage premiumRoomUsage = guestOfferService.calculateRoomUsage(guestOffers.getPremiumOffers());

        response.setPremiumRoomUsage(premiumRoomUsage);

        final RoomUsage economyRoomUsage = guestOfferService.calculateRoomUsage(guestOffers.getEconomyOffers());

        response.setEconomyRoomUsage(economyRoomUsage);

        return response;
    }
}
