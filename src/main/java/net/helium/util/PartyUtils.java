package net.helium.util;

import net.helium.Helium;
import net.helium.racro.RacroToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class PartyUtils {
    static int windowId;
    public static boolean isListing;
    private static final KeyBinding sneak;
    static boolean inGui;
    public int count = 0;

    public static void setSneaking(boolean sneaks) {
        if (sneaks) {
            KeyBinding.setKeyBindState(sneak.getKeyCode(), true);
        } else {
            KeyBinding.setKeyBindState(sneak.getKeyCode(), false);
        }
    }

    private static void interactWithEntity(Entity entity) {
        PlayerControllerMP playerControllerMP = Helium.mc.playerController;
        playerControllerMP.interactWithEntitySendPacket(Helium.mc.thePlayer, entity);
    }

    public static void warpHome() {
        new Thread(() -> {
            ChatUtils.addMessage("Warping to: home");
            try {
                Thread.sleep(3000L);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Helium.mc.thePlayer.sendChatMessage("/warp home");
        }).start();
    }

    public static void rejoinSkyblock() {
        new Thread(() -> {
            try {
                ChatUtils.addMessage("You were removed from Skyblock. You will be brought back in 20 seconds.");
                Thread.sleep(20000L);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Helium.mc.thePlayer.sendChatMessage("/play skyblock");
            ChatUtils.addMessage("You have been brought back to Skyblock.");
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Helium.mc.thePlayer.sendChatMessage("/warp home");
            ChatUtils.addMessage("You have been brought back to your island.");
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            PartyUtils.relist();
        }).start();
    }

    public static void relist() {
        if (!RacroToggle.isEnabled) {
            return;
        }
        ChatUtils.addMessage("Attempting to relist party...");
        new Thread(() -> {
            try {
                isListing = true;
                Thread.sleep(1000L);
                Helium.mc.thePlayer.sendChatMessage("/warp dhub");
                ChatUtils.addMessage("Warped to dungeon hub. Waiting 6.5 seconds before doing anything else to ensure a smooth transition.");
                Thread.sleep(6500L);
                Helium.mc.thePlayer.inventory.currentItem = 3;
                ItemStack item = Helium.mc.thePlayer.inventory.getStackInSlot(3);
                Thread.sleep(500L);
                PartyUtils.setSneaking(true);
                Thread.sleep(1000L);
                if (Helium.mc.thePlayer.posY != 123.0) {
                    Helium.mc.playerController.sendUseItem(Helium.mc.thePlayer, Helium.mc.theWorld, item);
                }
                Thread.sleep(2500L);
                if (Helium.mc.thePlayer.posY != 123.0) {
                    Thread.sleep(1000L);
                    Helium.mc.playerController.sendUseItem(Helium.mc.thePlayer, Helium.mc.theWorld, item);
                }
                PartyUtils.setSneaking(false);
                Helium.mc.thePlayer.rotationPitch = 80.0f;
                Thread.sleep(500L);
                Helium.mc.thePlayer.inventory.currentItem = 0;
                Thread.sleep(500L);
                for (Entity e : Helium.mc.theWorld.loadedEntityList) {
                    if (!e.getName().contains("Mort")) continue;
                    PartyUtils.interactWithEntity(e);
                }
                if (!inGui) {
                    Thread.sleep(1000L);
                    for (Entity e : Helium.mc.theWorld.loadedEntityList) {
                        if (!e.getName().contains("Mort")) continue;
                        PartyUtils.interactWithEntity(e);
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @SubscribeEvent
    public void guiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        if (!isListing) {
            return;
        }
        if (event.gui instanceof GuiChest) {
            new Thread(() -> {
                inGui = true;
                Container container = ((GuiChest)((Object)event.gui)).inventorySlots;
                if (container instanceof ContainerChest) {
                    String slotName;
                    int i;
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String chestName = ((ContainerChest)((Object)container)).getLowerChestInventory().getDisplayName().getUnformattedText();
                    List invSlots = container.inventorySlots;
                    if (chestName.contains("Catacombs Gate")) {
                        for (i = 0; i < invSlots.size(); ++i) {
                            if (!((Slot)invSlots.get(i)).getHasStack() || !(slotName = StringUtils.stripControlCodes(((Slot)invSlots.get(i)).getStack().getDisplayName())).equals("Find a Party")) continue;
                            ChatUtils.addMessage("Chest name: " + chestName);
                            this.clickSlot((Slot)invSlots.get(i));
                        }
                    }
                    if (chestName.contains("Party Finder")) {
                        for (i = 0; i < invSlots.size(); ++i) {
                            if (!((Slot)invSlots.get(i)).getHasStack() || !(slotName = StringUtils.stripControlCodes(((Slot)invSlots.get(i)).getStack().getDisplayName())).equals("Start a new queue")) continue;
                            ChatUtils.addMessage("Chest name: " + chestName);
                            this.clickSlot((Slot)invSlots.get(i));
                        }
                    }
                    if (chestName.contains("Group Builder")) {
                        for (i = 0; i < invSlots.size(); ++i) {
                            if (!((Slot)invSlots.get(i)).getHasStack() || !(slotName = StringUtils.stripControlCodes(((Slot)invSlots.get(i)).getStack().getDisplayName())).equals("Confirm Group")) continue;
                            this.clickSlot((Slot)invSlots.get(i));
                        }
                        if (chestName.contains("Party Finder")) {
                            for (i = 0; i < invSlots.size(); ++i) {
                                if (!((Slot)invSlots.get(i)).getHasStack() || !(slotName = StringUtils.stripControlCodes(((Slot)invSlots.get(i)).getStack().getDisplayName())).equals("De-list Group")) continue;
                                ChatUtils.addMessage("Chest name: " + chestName);
                                this.clickSlot((Slot)invSlots.get(i));
                            }
                        }
                    }
                }
            }).start();
        }
    }

    private void clickSlot(Slot slot) {
        windowId = Helium.mc.thePlayer.openContainer.windowId;
        Helium.mc.playerController.windowClick(windowId, slot.slotNumber, 1, 0, Helium.mc.thePlayer);
        System.out.println("Clicked slot: " + slot.slotNumber);
        ChatUtils.addMessage("Clicked slot: " + slot.slotNumber);
    }

    static {
        isListing = false;
        sneak = Minecraft.getMinecraft().gameSettings.keyBindSneak;
        inGui = false;
    }
}
