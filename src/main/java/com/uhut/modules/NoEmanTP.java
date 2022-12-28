package com.uhut.modules;

import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoEmanTP extends Module {
    public NoEmanTP() {
        super("NoEmanTP",0,Category.COMBAT);
    }
    @SubscribeEvent
    public void endermantp(EnderTeleportEvent e) {
        if (isToggled()) e.setCanceled(true);
    }
}
