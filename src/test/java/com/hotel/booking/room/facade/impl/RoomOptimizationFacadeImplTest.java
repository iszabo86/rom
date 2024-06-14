package com.hotel.booking.room.facade.impl;

import com.hotel.booking.room.dto.RoomOptimizationRequest;
import com.hotel.booking.room.dto.RoomOptimizationResponse;
import com.hotel.booking.room.dto.RoomUsage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoomOptimizationFacadeImplTest {

    private static final List<BigDecimal> GUEST_OFFERS = List.of(BigDecimal.valueOf(23), BigDecimal.valueOf(45),
            BigDecimal.valueOf(155), BigDecimal.valueOf(374), BigDecimal.valueOf(22), BigDecimal.valueOf(99.99),
            BigDecimal.valueOf(100), BigDecimal.valueOf(101), BigDecimal.valueOf(115), BigDecimal.valueOf(209d));

    @Autowired
    private RoomOptimizationFacadeImpl roomOptimizationFacade;

    @ParameterizedTest
    @CsvSource({
            "3,3,3,3,738,167.99", //fully booked, no upgrade
            "7,5,6,4,1054,189.99", //premium not fully booked, economy not fully booked
            "2,7,2,4,583,189.99", //premium fully booked, economy not fully booked
            "7,1,7,1,1153.99,45", //premium fully booked, economy upgraded
            "0,3,0,3,0,167.99", //no premium room available, economy fully booked
            "2,0,2,0,583,0", //premium fully booked, no economy room available
            "0,0,0,0,0,0", //no premium room available, no economy room available
    })
    public void testOptimizeRooms(int premiumRoomCount, int economyRoomCount,
                                  int expectedPremiumRoomCount, int expectedEconomyRoomCount,
                                  BigDecimal expectedPremiumRevenue, BigDecimal expectedEconomyRevenue) {

        final RoomOptimizationRequest request = createRequest(premiumRoomCount, economyRoomCount);

        final RoomOptimizationResponse response = roomOptimizationFacade.optimizeRooms(request);

        assertNotNull(response);

        final RoomUsage actualPremiumRoomUsage = response.getPremiumRoomUsage();
        assertNotNull(actualPremiumRoomUsage);
        assertEquals(expectedPremiumRoomCount, actualPremiumRoomUsage.getNumberOfRooms());
        assertTrue(expectedPremiumRevenue.compareTo(actualPremiumRoomUsage.getRevenue()) == 0);

        final RoomUsage actualEconomyRoomUsage = response.getEconomyRoomUsage();
        assertNotNull(actualEconomyRoomUsage);
        assertEquals(expectedEconomyRoomCount, actualEconomyRoomUsage.getNumberOfRooms());
        assertTrue(expectedEconomyRevenue.compareTo(actualEconomyRoomUsage.getRevenue()) == 0);

    }


    private RoomOptimizationRequest createRequest(int premiumRoomCount, int economyRoomCount) {
        return new RoomOptimizationRequest(premiumRoomCount, economyRoomCount, GUEST_OFFERS);
    }

    @Test
    public void testOptimizeRoomsWithNoGuestOffers() {
        final RoomOptimizationRequest request = new RoomOptimizationRequest(1, 2, null);

        final RoomOptimizationResponse response = roomOptimizationFacade.optimizeRooms(request);

        final RoomUsage actualPremiumRoomUsage = response.getPremiumRoomUsage();
        assertNotNull(actualPremiumRoomUsage);
        assertEquals(0, actualPremiumRoomUsage.getNumberOfRooms());
        assertTrue(BigDecimal.ZERO.compareTo(actualPremiumRoomUsage.getRevenue()) == 0);

        final RoomUsage actualEconomyRoomUsage = response.getEconomyRoomUsage();
        assertNotNull(actualEconomyRoomUsage);
        assertEquals(0, actualEconomyRoomUsage.getNumberOfRooms());
        assertTrue(BigDecimal.ZERO.compareTo(actualEconomyRoomUsage.getRevenue()) == 0);
    }
}
