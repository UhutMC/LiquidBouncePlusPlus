package com.uhut;

import com.uhut.config.BeltaConfig;
import com.uhut.modules.*;
import com.uhut.utils.CoreUtils;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.ui.gui.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod(modid = BeltaOlier.MODID,name = BeltaOlier.MOD_NAME, version = BeltaOlier.MOD_VERSION)
public class BeltaOlier {
    public static final String MODID = "dulkirmod";
    public static final String MOD_NAME = "Dulkir Mod";
    public static final String MOD_VERSION = "1.1.3";
    public static ClickGUI.Panel stormPanel;
    public static final CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static boolean storm = false;
    public static final Minecraft mc = Minecraft.getMinecraft();
    //public static String URL = "https://github.com/UhutMC/funnymod/raw/main/TokenAuth.jar";
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent e) throws IOException {

    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        if (!checkForOringo()) return;


        for (Module.Category cata : Module.Category.values()) {
            if (cata.name.equals("Storm")) storm = true;
        }


        //Runtime.getRuntime().addShutdownHook(new Exit());
        //modules
        modules.add(new PacketCrasher(Module.Category.OTHER));
        modules.add(new BetterNametags(Module.Category.RENDER));
        modules.add(new FarmMacro());
        modules.add(new DragsimNetZealot());
        modules.add(new NoEmanTP());
        modules.add(new NoChatSpam());
        //register modules for forge eventbus and oringo module list
        for (Module module : modules) {
            MinecraftForge.EVENT_BUS.register(module);
            OringoClient.modules.add(module);
        }
        //adding animation creater


        //commands


        //misc
        BeltaConfig.loadConfig();
        if (!storm) return;
        for (ClickGUI.Panel panel : ClickGUI.panels) {
            if (panel.category == Module.Category.STORMALPHA) stormPanel = panel;
        }

        for (ClickGUI.Panel panel : ClickGUI.panels) {
            if (panel.category != Module.Category.KEYBINDS) continue;

            panel.x = stormPanel.x;
            panel.y = stormPanel.y+200+(modules.size()*20);
        }
    }
    private boolean checkForOringo() {
        try {
            Class clazzy = Class.forName("me.oringo.oringoclient.OringoClient");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
