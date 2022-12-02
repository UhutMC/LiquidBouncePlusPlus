package net.helium.util;

import net.helium.Helium;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import static net.helium.Helium.mc;

public class ChatUtils {
    public static void addMessage(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + "Rat Racro " + EnumChatFormatting.RESET + EnumChatFormatting.DARK_GRAY + "» " + EnumChatFormatting.AQUA + message));
    }

    public static boolean isOnHypixel() {
        if (mc.getCurrentServerData() == null) {
            return false;
        }
        return mc.getCurrentServerData().serverIP.contains("hypixel.net");
    }

    public static boolean isInGame() {
        return mc.thePlayer == null || mc.theWorld == null;
    }
}
