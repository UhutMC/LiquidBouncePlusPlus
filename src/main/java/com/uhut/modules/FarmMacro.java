package com.uhut.modules;

import com.uhut.utils.FarmUtils;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.utils.Notifications;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

public class FarmMacro extends Module {
    private boolean dir;
    private int layers;
    private float yaw;
    private float pitch;

    double y;

    public FarmMacro() {
        super("Farm Macro",0,Category.MACRO);
    }

    @Override
    public void onEnable() {
        /*if (true) {
            Notifications.showNotification("Oringo", "Contact Uhut#1973 for support",5000);
            this.setToggled(false);
        }*/

        y = OringoClient.mc.thePlayer.posY;
        dir = true;
        yaw = OringoClient.mc.thePlayer.rotationYaw;
        pitch = OringoClient.mc.thePlayer.rotationPitch;
        layers = 0;
    }

    @Override
    public void onDisable() {
        disableMovement();
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (!isToggled()) return;

        if (FarmUtils.isStandingStill()) {
            dir = !dir;

            OringoClient.sendMessageWithPrefix("Changed Keys");
        }

        if (this.yaw > OringoClient.mc.thePlayer.rotationYaw+2 || this.yaw < OringoClient.mc.thePlayer.rotationYaw-2 || this.pitch > OringoClient.mc.thePlayer.rotationPitch+2 || this.pitch < OringoClient.mc.thePlayer.rotationPitch-2) {
            OringoClient.mc.thePlayer.rotationYaw = yaw;
            OringoClient.mc.thePlayer.rotationPitch = pitch;
        }



        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindAttack.getKeyCode(), true);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindLeft.getKeyCode(), !this.dir);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindRight.getKeyCode(), this.dir);
    }
    private void changeKeys() {

        disableMovement();
    }

    private void disableMovement() {
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindForward.getKeyCode(), false);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindBack.getKeyCode(), false);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindLeft.getKeyCode(), false);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindRight.getKeyCode(), false);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindJump.getKeyCode(), false);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        KeyBinding.setKeyBindState(OringoClient.mc.gameSettings.keyBindAttack.getKeyCode(), false);
    }
}
