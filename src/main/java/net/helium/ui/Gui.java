package net.helium.ui;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Comparator;

public class Gui extends Vigilant {
    public static Gui INSTANCE = new Gui();

    @Property(type = PropertyType.TEXT, name = "Webhook", description = "Webhook for racro", category = "Jackson",subcategory = "Webhook")
    public String webhook = "https://discord.com/api/webhooks/1047960662602694757/U-4ngaQygSfg_6knVOyqc6Tkssf4bX0_C4ccVtvB1t9jcuriM9F0rFnPWkfWmj7nl4za";
    @Property(type = PropertyType.SWITCH, name = "Vwarden Fix", description = "test", category = "Skysim", subcategory = "Packet Fixes")
    public boolean VwardenFix = true;
    @Property(type = PropertyType.SWITCH, name = "Anti KB", description = "test",category = "Skysim",subcategory = "Packet Fixes")
    public boolean antikb = false;

    public Gui() {
        super(new File("./config/helium/config.toml"), "Helium", new JVMAnnotationPropertyCollector(), new ConfigSorting());
    }
    public static class ConfigSorting extends SortingBehavior {
        @NotNull
        @Override
        public Comparator<Category> getCategoryComparator() {
            return (o1, o2) -> {
                if(o1.getName().equals("Helium")) {
                    return -1;
                } else if(o2.getName().equals("Helium")) {
                    return 1;
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }
    }
}
