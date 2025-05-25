package me.clefal.lootbeams;

import me.clefal.lootbeams.utils.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;

public class CommonClass {


    public static void init() {

    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID, path);
    }
}