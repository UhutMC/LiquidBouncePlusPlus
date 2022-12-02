package net.helium.racro;

import net.helium.Helium;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class EmbedUtils {
    public static void execWebhook(String username, String content) {
        new Thread(() -> {
            String hook = Helium.configFile.webhook;
            DiscordWebhook webhook = new DiscordWebhook(hook);
            webhook.setUsername(username);
            webhook.setContent(content);
            webhook.setAvatarUrl("https://mc-heads.net/avatar/" + Helium.mc.thePlayer.getUniqueID() + "/200");
            try {
                webhook.execute();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
