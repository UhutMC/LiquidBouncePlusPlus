package net.helium.racro;

import net.helium.Helium;
import net.helium.util.ChatUtils;
import net.helium.util.PartyUtils;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chat {
    public static String[] BlacklistedMessages = new String[]{"scammer", "scam", "rat", "steal", "ip grabber", "ip stealer", "don't join", "take ur", "take your", "takes your", "takes ur", "don't verify", "fake", "dont verify", "dont join", "session id", "ssid", "token", "hack", "fishy", "sus", "grabber", "phish", "virus"};
    public static String[] WelcomeMessages = new String[]{"hi the discord link is", "my server is", "my discord server is", "my dc server is", "my server link is"};

    private String getname(String name) {
        String subString = "";
        try {
            int iend = name.indexOf(":");
            if (iend != -1) {
                subString = name.substring(0, iend);
            }
        }
        catch (Exception e) {
            ChatUtils.addMessage("" + e);
        }
        return subString;
    }

    private String stripRank(String name) {
        if (!name.contains("]")) {
            int split = name.indexOf(">") + 2;
            return name.substring(split);
        }
        return name.substring(name.indexOf("]") + 2);
    }

    @SubscribeEvent
    public void onChat0(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = StringUtils.stripControlCodes(event.message.getUnformattedText()).toLowerCase();
        String part = this.stripRank(msg.substring(msg.indexOf("by") + 3));
        String Name = this.getname(part);
        if (Name.contains("]")) {
            Name = Name.substring(Name.indexOf("]") + 2);
        }
        if (msg.contains("[irc]")) {
            return;
        }
        if (Objects.equals(Name, "")) {
            return;
        }
        if (Name.equals(Helium.mc.getSession().getUsername().toLowerCase())) {
            return;
        }
        for (String item : BlacklistedMessages) {
            String finalName = Name;
            new Thread(() -> {
                try {
                    if (msg.contains(item)) {
                        Helium.mc.thePlayer.sendChatMessage("/p disband");
                        System.out.println("Kicked " + finalName + " for " + item);
                        Thread.sleep(250L);
                        Helium.mc.thePlayer.sendChatMessage("/ignore add " + finalName);
                        System.out.println("Ignored " + finalName);
                        Thread.sleep(250L);
                        Helium.mc.thePlayer.sendChatMessage("/sbkickall");
                        ChatUtils.addMessage("Kicked " + finalName + " for saying \"" + item + "\"");
                        PartyUtils.relist();
                    }
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void onChat2(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText();
        if (msg.contains("Your dungeon group is full")) {
            ChatUtils.addMessage("Dungeon group is full. Waiting 5 minutes to relist!");
            new Thread(() -> {
                try {
                    Thread.sleep(300000L);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Helium.mc.thePlayer.sendChatMessage("/p disband");
                Helium.mc.thePlayer.sendChatMessage("/sbkickall");
                PartyUtils.relist();
            }).start();
        }
    }

    @SubscribeEvent
    public void onChat3(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText();
        if (msg.contains("The party was disbanded")) {
            PartyUtils.relist();
        }
    }

    @SubscribeEvent
    public void onChat4(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText();
        if (msg.contains("Couldn't warp you! Try again later.")) {
            PartyUtils.warpHome();
        }
    }

    @SubscribeEvent
    public void onChat5(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText();
        if (msg.contains("Your group has been removed from the party finder because the leader left Skyblock!")) {
            PartyUtils.rejoinSkyblock();
        }
    }

    @SubscribeEvent
    public void onChat7(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText();
        if (msg.contains("unclaimed leveling rewards") || msg.contains("unclaimed achievment rewards")) {
            PartyUtils.rejoinSkyblock();
        }
    }

    @SubscribeEvent
    public void onChat6(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText();
        if (msg.contains("has disconnected, they have 5 minutes to rejoin before they are removed from the party.")) {
            new Thread(() -> {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Helium.mc.thePlayer.sendChatMessage("/p kickoffline");
            }).start();
        }
    }

    @SubscribeEvent
    public void onChat10(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText().toLowerCase();
        if (msg.contains("joined the dungeon group!")) {
            return;
        }
        if (msg.contains("joined")) {
            new Thread(() -> {
                String[] messages;
                messages = new String[]{"when you verify go to #carries channel", "go to #carrries when you're verified", "when you verify check #carries"};
                String message = messages[new Random().nextInt(WelcomeMessages.length)];
                int randomInt = ThreadLocalRandom.current().nextInt(5000, 7501);
                ChatUtils.addMessage("Sending message in " + randomInt / 1000 + " seconds.");
                try {
                    Thread.sleep(randomInt);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Helium.mc.thePlayer.sendChatMessage(message);
            }).start();
        }
    }

    @SubscribeEvent
    public void onChat11(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getUnformattedText().toLowerCase();
        if (msg.contains("your party has been queued in the dungeon finder!") || msg.contains("you have successfully started a new dungeon queue!")) {
            PartyUtils.warpHome();
            PartyUtils.isListing = false;
        }
    }

    @SubscribeEvent
    public void onChat1(ClientChatReceivedEvent event) {
        if (!RacroToggle.isEnabled) {
            return;
        }
        String msg = event.message.getFormattedText();
        if (msg.contains("joined the dungeon group!")) {
            new Thread(() -> {
                Helium.mc.thePlayer.sendChatMessage("/p warp");
                System.out.println("warping");
                ChatUtils.addMessage("Warping dungeon group");
                EmbedUtils.execWebhook(Helium.mc.thePlayer.getName(), msg);
                if (true) {
                    String welcomeMessage = WelcomeMessages[new Random().nextInt(WelcomeMessages.length)];
                    int randomInt = ThreadLocalRandom.current().nextInt(5000, 15001);
                    ChatUtils.addMessage("Sending welcome message in " + randomInt / 1000 + " seconds.");
                    try {
                        Thread.sleep(randomInt);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Helium.mc.thePlayer.sendChatMessage("" + welcomeMessage + " https://discord.gg/J3cQfm2F");
                }
            }).start();
        }
    }
}
