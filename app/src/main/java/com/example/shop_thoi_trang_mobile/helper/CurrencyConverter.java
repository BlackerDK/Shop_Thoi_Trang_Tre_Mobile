package com.example.shop_thoi_trang_mobile.helper;

public class CurrencyConverter {

    // Example exchange rate, you should update this with the latest rate
    private static final double EXCHANGE_RATE_VND_TO_USD = 0.000039; // 1 VND to USD

    /**
     * Converts Vietnamese Dong (VND) to US Dollar (USD).
     *
     * @param vndAmount The amount in VND to be converted.
     * @return The equivalent amount in USD.
     */
    public static double convertVndToUsd(double vndAmount) {
        return vndAmount * EXCHANGE_RATE_VND_TO_USD;
    }
}
