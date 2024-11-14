package com.zemoso.seeder.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class NumberUtils {

    private NumberUtils(){}

    public static double roundToTwoDecimalPlaces(double value) {
        BigDecimal bigDecimalValue = BigDecimal.valueOf(value);
        bigDecimalValue = bigDecimalValue.setScale(2, RoundingMode.HALF_UP); // Rounds to 2 decimal places
        return bigDecimalValue.doubleValue();
    }
}
