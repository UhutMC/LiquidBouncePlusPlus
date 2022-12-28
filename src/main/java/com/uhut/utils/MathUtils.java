package com.uhut.utils;

public class MathUtils {
    public static double mathThing(double paramDouble1, double paramDouble2, double paramDouble3) {
        if (paramDouble2 < paramDouble3) {
            double d = paramDouble2;
            paramDouble2 = paramDouble3;
            paramDouble3 = d;
        }
        return Math.max(Math.min(paramDouble2, paramDouble1), paramDouble3);
    }
}
