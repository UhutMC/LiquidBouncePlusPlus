package com.uhut.utils;

import me.oringo.oringoclient.OringoClient;

public class FarmUtils {
    public static boolean isStandingStill() {
        return OringoClient.mc.thePlayer.motionX == 0 && OringoClient.mc.thePlayer.motionZ == 0;
    }
}
