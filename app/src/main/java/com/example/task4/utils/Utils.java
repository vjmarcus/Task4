package com.example.task4.utils;

import java.text.DecimalFormat;

public class Utils {
    public static double formatDouble(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        String result = decimalFormat.format(num);
        return Double.parseDouble(result);
    }
}
