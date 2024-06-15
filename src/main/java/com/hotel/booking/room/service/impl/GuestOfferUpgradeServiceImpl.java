package com.hotel.booking.room.service.impl;

import com.hotel.booking.room.dto.GuestOffers;
import com.hotel.booking.room.service.GuestOfferUpgradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GuestOfferUpgradeServiceImpl implements GuestOfferUpgradeService {

    private static final Logger LOG = LoggerFactory.getLogger(GuestOfferUpgradeServiceImpl.class);

    @Override
    public void upgradeIfPossible(final GuestOffers guestOffers, int premiumRoomCount, int economyRoomCount) {
        int economyOffersToUpgrade = calculateUpgradeableEconomyOffers(guestOffers, premiumRoomCount, economyRoomCount);

        if (economyOffersToUpgrade > 0) {
            LOG.info("Upgrading {} economy offers to premium", economyOffersToUpgrade);

            final List<BigDecimal> economyOffers = guestOffers.getEconomyOffers();

            final List<BigDecimal> offersBeingUpgraded = economyOffers.subList(0, economyOffersToUpgrade);

            List<BigDecimal> premiumOffers = guestOffers.getPremiumOffers();
            if (Objects.isNull(premiumOffers)){
                premiumOffers = new ArrayList<>();
            }

            premiumOffers.addAll(offersBeingUpgraded);

            economyOffers.removeAll(offersBeingUpgraded);
        }
    }

    private int calculateUpgradeableEconomyOffers(final GuestOffers guestOffers, final int premiumRoomCount, final int economyRoomCount) {
        LOG.info("Checking if upgrade possible");

        final List<BigDecimal> economyOffers = guestOffers.getEconomyOffers();
        int economyOfferCount = economyOffers == null ? 0 : economyOffers.size();

        final List<BigDecimal> premiumOffers = guestOffers.getPremiumOffers();
        int premiumOfferCount = premiumOffers == null ? 0 : premiumOffers.size();

        int premiumRoomsAvailableForUpgrade = premiumRoomCount - premiumOfferCount;

        int upgradableEconomyOfferCount = economyOfferCount - economyRoomCount;

        final boolean upgradePossible = premiumRoomsAvailableForUpgrade > 0
                && upgradableEconomyOfferCount > 0;

        int economyOffersToUpgrade = 0;

        if (upgradePossible) {
            economyOffersToUpgrade = Math.min(premiumRoomsAvailableForUpgrade, upgradableEconomyOfferCount);
            LOG.info("Upgrade possible for {} economy offers", economyOffersToUpgrade);
        }else {
            LOG.info("Upgrade is not possible");
        }


        return economyOffersToUpgrade;
    }
}
