package com.uhut.modules;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.Notifications;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PacketCrasher extends me.oringo.oringoclient.qolfeatures.module.Module {
    public static NumberSetting packetsPerTick = new NumberSetting("Packets Per Tick", 10, 1, 20, 1);
    public static NumberSetting disableTime = new NumberSetting("Disable Timer", 5000, 2000, 10000, 50);
    public static NumberSetting firstSlot = new NumberSetting("First Slot", 1, 1, 9, 1);
    public static NumberSetting secondSlot = new NumberSetting("Second Slot", 2, 1, 9, 1);
    public static int packetsSent = 0;
    public static int packetsThisSession = 0;
    public MilliTimer disableTimer = new MilliTimer();
    public PacketCrasher(Category cata) {
        super("Packet Crasher", 0, cata);
        this.addSettings(packetsPerTick, disableTime, firstSlot, secondSlot);
    }

    @Override
    public void onEnable() {
        this.disableTimer.reset();
        packetsSent = 0;
    }
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (!isToggled()) return;
        if (OringoClient.mc.thePlayer == null || OringoClient.mc.theWorld == null) return;
        if (disableTimer.hasTimePassed((long) disableTime.getValue())) {
            setToggled(false);
            return;
        }
        for (double i = 0; i < packetsPerTick.getValue(); i++) {
            if (OringoClient.mc.getNetHandler() == null || OringoClient.mc.getNetHandler().getNetworkManager() == null) return;
            OringoClient.mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange((int) secondSlot.getValue()));
            OringoClient.mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange((int) firstSlot.getValue()));
            packetsSent+=2;
            packetsThisSession+=2;
        }
    }

    @Override
    public void onDisable() {
        this.disableTimer.reset();
        Notifications.showNotification("Oringo Client", "Sent " + packetsSent + " Packets for a total of " + packetsThisSession + " this session", 5000);
    }

    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        this.setToggled(false);
    }
}
