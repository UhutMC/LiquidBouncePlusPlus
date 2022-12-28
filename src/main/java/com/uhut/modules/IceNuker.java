package com.uhut.modules;

import com.uhut.BeltaOlier;
import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class IceNuker extends Module {
    public IceNuker() {
        super("Ice Nuker",0, Category.OTHER);
    }
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (BeltaOlier.mc.thePlayer != null || BeltaOlier.mc.theWorld != null) return;
        if (e.phase != TickEvent.Phase.START || !isToggled()) return;

        BlockPos pos = localBlock();
        if (pos != null) {

        }
    }

    private BlockPos localBlock() {
        for (double x = BeltaOlier.mc.thePlayer.posX-4;x<BeltaOlier.mc.thePlayer.posX+4;x++) {
            for (double z = BeltaOlier.mc.thePlayer.posZ-4;z<BeltaOlier.mc.thePlayer.posZ+4;z++) {
                for (double y = BeltaOlier.mc.thePlayer.posY;y<BeltaOlier.mc.thePlayer.posY;y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (BeltaOlier.mc.theWorld.getBlockState(pos).getBlock().equals(Block.getIdFromBlock(Blocks.ice))) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }
}
