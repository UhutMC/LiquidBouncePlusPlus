package com.uhut.modules;

import com.uhut.utils.MathUtils;
import com.uhut.utils.NoCarpet;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.KeyPressEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class BetterNametags extends Module {
    public static BooleanSetting players = new BooleanSetting("Players", false);
    public static BooleanSetting information = new BooleanSetting("Various Information", false);
    public BetterNametags(Category cata) {
        super("Better Nametags", 0, cata);
        this.addSettings(players,information);
    }
    @SubscribeEvent
    public void render(RenderLivingEvent.Specials.Pre<EntityLivingBase> e) {
        if (!this.isToggled() || !valid(e.entity)) return;

        e.setCanceled(true);
        double x = e.x, y = e.y, z = e.z;

        GlStateManager.alphaFunc(516, 0.1F);
        String str = e.entity.getName();
        double d1 = e.x;
        double d2 = e.y;
        double d3 = e.z;
        float f1 = Math.max(1.4F, e.entity.getDistanceToEntity((Entity)mc.thePlayer) / 10.0F);
        float f2 = 0.016666668F * f1;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)d1 + 0.0F, (float)d2 + e.entity.height + 0.5F, (float)d3);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((mc.getRenderManager()).playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f2, -f2, f2);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float f3 = (float) (Fonts.robotoMediumBold.getStringWidth(str) / 2.0D)-1;
        GlStateManager.disableTexture2D();
        NoCarpet.backgroundRender(-f3 - 3.0F, (Fonts.robotoMediumBold.getHeight() + 3), f3 + 3.0F, -3.0F, (new Color(20, 20, 20, 80)).getRGB());
        NoCarpet.backgroundRender(-f3 - 3.0F, (Fonts.robotoMediumBold.getHeight() + 3), (float)((f3 + 3.0F) * (MathUtils.mathThing((e.entity.getHealth() / e.entity.getMaxHealth()), 1.0D, 0.0D) - 0.5D) * 2.0D), (Fonts.robotoMediumBold.getHeight() + 2), OringoClient.clickGui.getColor().getRGB());
        GlStateManager.enableTexture2D();
        Fonts.robotoMediumBold.drawString(str, -Fonts.robotoMediumBold.getStringWidth(str) / 2.0D, 0.0D, Color.WHITE.getRGB());
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        Fonts.robotoMediumBold.drawString(str, -Fonts.robotoMediumBold.getStringWidth(str) / 2.0D, 0.0D, Color.WHITE.getRGB());
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
    private boolean valid(EntityLivingBase e) {
        if (players.isEnabled() && e instanceof EntityPlayer) return true;
        return !(e instanceof EntityArmorStand);
    }
}
