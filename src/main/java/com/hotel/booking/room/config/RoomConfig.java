package com.hotel.booking.room.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties("room")
@Getter
@Setter
public class RoomConfig {

    private BigDecimal minPremiumPrice;
}
