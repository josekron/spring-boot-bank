package com.jaherrera.springbootbank.util;

import java.math.BigDecimal;
import static java.math.RoundingMode.UP;

public class BigDecimalUtil {

    public static BigDecimal getChangeFromRoundUp(BigDecimal amount){
        return amount.setScale(0, UP).subtract(amount);
    }
}
