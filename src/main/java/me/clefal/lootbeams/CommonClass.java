package me.clefal.lootbeams;

import net.minecraft.resources.ResourceLocation;

public class CommonClass {


    public static void init() {

    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(LootBeamsConstants.MODID, path);
    }
}