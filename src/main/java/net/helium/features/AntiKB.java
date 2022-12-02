package net.helium.features;

import net.helium.Helium;
import net.helium.events.ReceivePacketEvent;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiKB {
    @SubscribeEvent
    public void receivePacket(ReceivePacketEvent e) {
        if (e.packet instanceof S12PacketEntityVelocity && Helium.configFile.antikb) e.setCanceled(true);
    }
}
