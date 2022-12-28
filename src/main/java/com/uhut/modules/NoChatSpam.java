package com.uhut.modules;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoChatSpam extends Module {
    public static BooleanSetting exquisiteRing = new BooleanSetting("chat spam", false);
    public NoChatSpam() {
        super("No Chat Spam",0,Category.OTHER);
        this.addSettings(exquisiteRing);
    }
    @SubscribeEvent
    public void chat(ClientChatReceivedEvent e) {
        if (exquisiteRing.isEnabled() && (e.message.getUnformattedText().contains("requires higher quest completion!") || e.message.getUnformattedText().contains("Its stats and effects don't apply!"))) {
            e.setCanceled(true);
        }
    }
}
