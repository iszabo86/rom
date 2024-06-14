package com.hotel.booking.room.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GuestOffers {

    List<BigDecimal> premiumOffers;

    List<BigDecimal> economyOffers;
}
