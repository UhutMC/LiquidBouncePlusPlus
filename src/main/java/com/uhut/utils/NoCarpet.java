package com.uhut.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class NoCarpet {
    public static void backgroundRender(float float1, float float2, float float3, float float4, int paramInt) {
        if (float1  < float3) {
            float f = float1;
            float1 = float3;
            float3 = f;
        }
        if (float2 < float4) {
            float f = float2;
            float2 = float4;
            float4 = f;
        }
        float f1 = (paramInt >> 24 & 0xFF) / 255.0F;
        float f2 = (paramInt >> 16 & 0xFF) / 255.0F;
        float f3 = (paramInt >> 8 & 0xFF) / 255.0F;
        float f4 = (paramInt & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f2, f3, f4, f1);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(float1, float4, 0.0D).endVertex();
        worldRenderer.pos(float3, float4, 0.0D).endVertex();
        worldRenderer.pos(float3, float2, 0.0D).endVertex();
        worldRenderer.pos(float1, float2, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
