package com.clefal.lootbeams.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;

@UtilityClass
public class ResourceLocationHelper {
    public ResourceLocation fromNameAndPath(String name, String path){
        return new ResourceLocation(name, path);
    }

    public ResourceLocation fromWholeName(String location){
        return ResourceLocation.of(location, ':');
    }
}
