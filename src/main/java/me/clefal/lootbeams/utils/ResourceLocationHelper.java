package me.clefal.lootbeams.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;

@UtilityClass
public class ResourceLocationHelper {
    public ResourceLocation fromNameAndPath(String name, String path){
        //? if !=1.20.1 {
        return ResourceLocation.fromNamespaceAndPath(name, path);
        //?} else {
        /*return new ResourceLocation(name, path);
        *///?}

    }

    public ResourceLocation fromWholeName(String location){
        //? if !=1.20.1 {
        return ResourceLocation.bySeparator(location, ':');
        //?} else {
        /*return ResourceLocation.of(location, ':');
        *///?}

    }
}
