package net.helium.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SkysimUtils {
    public static String getID(ItemStack item) {
        if (item.getTagCompound() == null) return "";
        NBTTagCompound tag = item.getTagCompound();
        if (tag.hasKey("type")) return tag.getString("type");
        return "";
    }
}
