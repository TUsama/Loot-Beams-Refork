package com.clefal.lootbeams;

import com.clefal.lootbeams.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class CommonClass {


    public static void init() {

    }

    public static ResourceLocation id(String path){
        return ResourceLocation.fromNamespaceAndPath(LootBeamsConstants.MODID, path);
    }
}