package com.zqskate.schedule.main;

import java.math.BigDecimal;

/**
 * @author soho.chan
 * @date 25/9/2020 15:32
 * @description TODO
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1.00);
        BigDecimal b = new BigDecimal(1.0);
        System.out.println(a.equals(b));
        System.out.println(a.compareTo(b));
    }
}
