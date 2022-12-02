package net.helium.racro;

import net.helium.Helium;
import net.helium.util.ChatUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class RacroToggle {
    public static boolean isEnabled = false;

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (!Helium.keyBinds[0].isPressed()) return;
        if (isEnabled) {
            ChatUtils.addMessage("Disabled!");
            isEnabled = false;
        } else {
            isEnabled = true;
            ChatUtils.addMessage("Enabled!");
        }
    }
}
