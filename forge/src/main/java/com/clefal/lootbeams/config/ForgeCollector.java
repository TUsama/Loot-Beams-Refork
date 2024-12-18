package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServiceCollector;
import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ForgeCollector implements IServiceCollector {


    @Override
    public List<String> gatherModIDList() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }
}
