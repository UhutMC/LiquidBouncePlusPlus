package net.helium.features;

import net.helium.Helium;
import net.helium.events.ReceivePacketEvent;
import net.helium.util.SkysimUtils;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WardenFix {
    @SubscribeEvent
    public void packetReceive(ReceivePacketEvent e) {
        if (e.packet instanceof S04PacketEntityEquipment && Helium.configFile.VwardenFix) {
            S04PacketEntityEquipment packet = (S04PacketEntityEquipment) e.packet;
            if (packet.getEquipmentSlot() == 3 && packet.getItemStack() != null && SkysimUtils.getID(packet.getItemStack()).equals("HIDDEN_VOIDLINGS_WARDEN_HELMET")) {
                e.setCanceled(true);
            }
        }
    }
}
