package com.uhut.modules;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.Notifications;
import me.oringo.oringoclient.utils.PacketUtils;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DragsimNetZealot extends Module {
    public static EntityEnderman target = null;
    private Rotation rotation;
    private int actions = 0;
    private int entityId;
    private int count = 0;
    public static NumberSetting delay = new NumberSetting("Tick Delay", 5, 4, 20, 1);
    public static BooleanSetting randomizer = new BooleanSetting("Randomize Aim", false);
    public static NumberSetting randomYaw = new NumberSetting("Random Value", 0.2, 0.1, 1, 0.1) {
        @Override
        public boolean isHidden() {
            return !randomizer.isEnabled();
        }
    };
    public DragsimNetZealot() {
        super("Zealot Macro", 0, Category.MACRO);
        this.addSettings(delay, randomizer, randomYaw);
    }
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (!this.isToggled()) return;
        if (OringoClient.mc.thePlayer == null || OringoClient.mc.theWorld == null) {
            this.setToggled(false);
            return;
        }
        if (count > 1) {
            count--;
            return;
        }
        if (target == null) return;

        PacketUtils.sendPacketNoEvent((Packet<?>) new C08PacketPlayerBlockPlacement(OringoClient.mc.thePlayer.getHeldItem()));
        count = (int) delay.getValue();
    }
    private EntityEnderman getTarget() {
        List<Entity> targets = OringoClient.mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderman).filter(entity -> ((EntityEnderman) entity).deathTime < 1).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(OringoClient.mc.thePlayer))).collect(Collectors.toList());

        if (!targets.isEmpty()) {
            EntityEnderman mob = (EntityEnderman) targets.get(0);
            if (entityId == mob.getEntityId()) {
                return target;
            }

            if (randomizer.isEnabled()) rotation = RotationUtils.getRotations((EntityLivingBase) mob, (float) randomYaw.getValue());
            else rotation = RotationUtils.getRotations((EntityLivingBase) mob);
            entityId = mob.getEntityId();
            return mob;
        }
        rotation = null;
        return null;
    }
    @SubscribeEvent
    public void render(RenderWorldLastEvent e) {
        if (!isToggled()) return;
        target = getTarget();
        if (target == null) return;
        OringoClient.mc.thePlayer.rotationYaw = rotation.getYaw();
        OringoClient.mc.thePlayer.rotationPitch = rotation.getPitch();
    }

    @Override
    public void onDisable() {
        target = null;
    }

    @Override
    public void onEnable() {
        target = null;
        if (OringoClient.mc.thePlayer.getHeldItem().getItem() == null || OringoClient.mc.thePlayer.getHeldItem().getItem() != Items.iron_hoe) {
            this.setToggled(false);
            Notifications.showNotification("Oringo Client","You must be holding a frozen scythe!", 5000);
        }
    }
    @SubscribeEvent
    public void worldload(WorldEvent.Load e) {
        if (this.isToggled()) {
            this.setToggled(false);
        }
    }
}
