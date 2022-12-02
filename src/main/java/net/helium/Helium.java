package net.helium;

import net.helium.command.QueueCommand;
import net.helium.command.SettingsCommand;
import net.helium.events.ReceivePacketEvent;
import net.helium.events.SendPacketEvent;
import net.helium.features.AntiKB;
import net.helium.features.WardenFix;
import net.helium.racro.RacroToggle;
import net.helium.ui.Gui;
import net.helium.util.PartyUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod(modid = Helium.MODID, version = Helium.VER)
public class Helium {
    public static final String MODID = "helium";
    public static final String VER = "0.2";
    public static Gui configFile = Gui.INSTANCE;
    public static GuiScreen display = null;
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static KeyBinding[] keyBinds = new KeyBinding[1];
    public static final CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        File directory = new File(mc.mcDataDir.getPath(), "helium");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new WardenFix());
        MinecraftForge.EVENT_BUS.register(new AntiKB());
        MinecraftForge.EVENT_BUS.register(new RacroToggle());
        MinecraftForge.EVENT_BUS.register(new PartyUtils());
        MinecraftForge.EVENT_BUS.register(new net.helium.racro.Chat());
        configFile.initialize();
        ClientCommandHandler.instance.registerCommand(new SettingsCommand());
        ClientCommandHandler.instance.registerCommand(new QueueCommand());
        Helium.keyBinds[0] = new KeyBinding("Toggle Racro", 0, MODID);
        for (KeyBinding keyBind : keyBinds) {
            ClientRegistry.registerKeyBinding(keyBind);
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (display != null) {
            try {
                mc.displayGuiScreen(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
            display = null;
        }
    }
}
