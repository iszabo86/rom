package com.hotel.booking.room.service.impl;

import com.hotel.booking.room.config.RoomConfig;
import com.hotel.booking.room.dto.GuestOffers;
import com.hotel.booking.room.dto.RoomUsage;
import com.hotel.booking.room.service.GuestOfferService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestOfferServiceImpl implements GuestOfferService {

    private static final Logger LOG = LoggerFactory.getLogger(GuestOfferServiceImpl.class);

    private RoomConfig roomConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public GuestOffers processOffers(final List<BigDecimal> guestOffers) {
        LOG.info("Processing guest offers");
        final GuestOffers offers = categorizeOffers(guestOffers);

        prioritizeGuestOffers(offers);

        return offers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeExcessOffers(final GuestOffers offers, final int premiumRoomCount, final int economyRoomCount) {
        LOG.info("Removing excess offers");
        BiFunction<List<BigDecimal>, Integer, List<BigDecimal>> resizeOffers = (offerListToResize, roomCount) ->
        {
            if (!CollectionUtils.isEmpty(offerListToResize)) {
                return offerListToResize.subList(0, Math.min(offerListToResize.size(), roomCount));
            }
            return null;
        };

        final List<BigDecimal> resizedPremiumOffers = resizeOffers.apply(offers.getPremiumOffers(), premiumRoomCount);
        offers.setPremiumOffers(resizedPremiumOffers);

        final List<BigDecimal> resizedEconomyOffers = resizeOffers.apply(offers.getEconomyOffers(), economyRoomCount);
        offers.setEconomyOffers(resizedEconomyOffers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoomUsage calculateRoomUsage(List<BigDecimal> offers) {
        LOG.debug("Calculating room usage for {}", offers);
        final RoomUsage roomUsage = new RoomUsage();

        BigDecimal revenue = BigDecimal.ZERO;

        int numberOfRooms = 0;

        if (!CollectionUtils.isEmpty(offers)) {
            numberOfRooms = offers.size();
            revenue = offers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        roomUsage.setNumberOfRooms(numberOfRooms);
        roomUsage.setRevenue(revenue);

        LOG.debug("Revenue calculated {} for {} rooms", revenue, numberOfRooms);

        return roomUsage;
    }

    /**
     * Prioritizing offers on the offers GuestOffers instance
     * @param offers instance of GuestOffers that holds both premium and economy offers
     */
    private void prioritizeGuestOffers(final GuestOffers offers) {
        LOG.info("Prioritizing guest offers");
        Consumer<List<BigDecimal>> sortDescending = items ->
        {
            if (!CollectionUtils.isEmpty(items)) {
                items.sort(Collections.reverseOrder());
            }
        };

        //prioritizing premium offers in descending order
        sortDescending.accept(offers.getPremiumOffers());

        //prioritizing economy offers in descending order
        sortDescending.accept(offers.getEconomyOffers());
    }


    /***
     * It categorizes the guest offers into Premium and Economy categories based on the RoomConfig.minPremiumPrice(Minimum Price for
     * Premium Rooms)
     * @param guestOffers all the guest offers received
     */
    private GuestOffers categorizeOffers(final List<BigDecimal> guestOffers) {
        LOG.info("Categorizing guest offers");
        final GuestOffers categorizedOffers = new GuestOffers();

        if (CollectionUtils.isEmpty(guestOffers)) {
            LOG.debug("No guest offers received");
            return categorizedOffers;
        }

        //Price threshold for Premium rooms
        final BigDecimal minPremiumPrice = roomConfig.getMinPremiumPrice();

        //Partitioning offers by the price threshold for Premium rooms
        final Map<Boolean, List<BigDecimal>> categorizedOfferMap = guestOffers.stream()
                .collect(Collectors.partitioningBy(offer -> minPremiumPrice.compareTo(offer) <= 0));


        final List<BigDecimal> premiumOffers = categorizedOfferMap.get(Boolean.TRUE);
        LOG.debug("Premium offers: {}", premiumOffers);
        categorizedOffers.setPremiumOffers(premiumOffers);


        final List<BigDecimal> economyOffers = categorizedOfferMap.get(Boolean.FALSE);
        LOG.debug("Economy offers: {}", economyOffers);
        categorizedOffers.setEconomyOffers(economyOffers);

        return categorizedOffers;
    }
}
